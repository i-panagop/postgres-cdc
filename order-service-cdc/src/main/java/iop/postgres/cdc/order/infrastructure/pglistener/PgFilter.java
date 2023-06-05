package iop.postgres.cdc.order.infrastructure.pglistener;

import com.fasterxml.jackson.databind.JsonNode;
import iop.postgres.cdc.order.business.event.Event;
import iop.postgres.cdc.order.business.event.EventType;
import iop.postgres.cdc.order.business.event.OrderEvent;
import iop.postgres.cdc.order.business.json.NodeReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
public class PgFilter implements GenericHandler<List<PgRowMapper.PgSlotChange>> {

    @Override
    public Object handle(List<PgRowMapper.PgSlotChange> payload, MessageHeaders headers) {
        if (CollectionUtils.isEmpty(payload)) {
            return new ArrayList<>();
        }
        List<Event> events = new ArrayList<>();
        for (PgRowMapper.PgSlotChange pgSlotChange : payload) {
            if (Objects.isNull(pgSlotChange.data()) || !hasChanges(pgSlotChange.data())) {
                continue;
            }
            events.addAll(populateOrderEvent(pgSlotChange.data().get("change")));
        }
        return events;
    }

    private List<OrderEvent> populateOrderEvent(JsonNode changes) {
        List<OrderEvent> events = new ArrayList<>();
        Iterator<JsonNode> itr = changes.elements();
        while (itr.hasNext()) {
            JsonNode change = itr.next();

            OrderEvent event = new OrderEvent();
            event.setType(EventType.of(NodeReader.getValueNullSafe(List.of("kind"), change, JsonNode::asText)));

            Iterator<JsonNode> columnNamesItr = change.get("columnnames").elements();
            List<String> columnNames = new ArrayList<>();
            while (columnNamesItr.hasNext()) {
                columnNames.add(columnNamesItr.next().asText());
            }

            Iterator<JsonNode> columnValuesItr = change.get("columnvalues").elements();
            List<String> columnValues = new ArrayList<>();
            while (columnValuesItr.hasNext()) {
                columnValues.add(columnValuesItr.next().asText());
            }

            for (int i = 0; i < columnNames.size(); i++) {
                String columnName = columnNames.get(i);
                String value = columnValues.get(i);
                String fieldName = convertToCamelCase(columnName);
                try {
                    Field field = event.getClass().getDeclaredField(fieldName);
                    field.setAccessible(true); // To make private fields accessible

                    Class<?> fieldType = field.getType();
                    Object convertedValue = convertValue(value, fieldType);
                    try {
                        field.set(event, convertedValue);
                    } catch (IllegalAccessException e) {
                        String errorMessage = String.format("Error while setting field %s with value %s for event %s",
                            fieldName, convertedValue, event.getClass().getName());
                        log.info(errorMessage, e);
                        event.setError(errorMessage);
                    }
                } catch (NoSuchFieldException e) {
                    String errorMessage = String.format("No field found with name %s for event %s", fieldName,
                        event.getClass().getName());
                    log.info(errorMessage, e);
                    event.setError(errorMessage);
                }
            }
            events.add(event);
        }
        return events;
    }

    private boolean hasChanges(JsonNode data) {
        JsonNode changes = data.get("change");
        return Objects.nonNull(changes) && changes.elements().hasNext();
    }

    private String convertToCamelCase(String columnName) {
        StringBuilder sb = new StringBuilder(columnName.length());
        boolean capitalizeNext = false;

        for (char c : columnName.toCharArray()) {
            if (c == '_') {
                capitalizeNext = true;
            } else if (capitalizeNext) {
                sb.append(Character.toUpperCase(c));
                capitalizeNext = false;
            } else {
                sb.append(c);
            }
        }

        return sb.toString();
    }

    private static Object convertValue(String value, Class<?> fieldType) {
        if (fieldType == double.class || fieldType == Double.class) {
            return Double.parseDouble(value);
        }
        if (fieldType == UUID.class) {
            return UUID.fromString(value);
        }
        return value;
    }
}
