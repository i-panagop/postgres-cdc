package iop.postgres.cdc.orchestrator.business.event;

import com.fasterxml.jackson.databind.JsonNode;
import io.micrometer.common.util.StringUtils;
import iop.postgres.cdc.orchestrator.business.commerceitem.CommerceItemEvent;
import iop.postgres.cdc.orchestrator.business.json.NodeReader;
import iop.postgres.cdc.orchestrator.business.order.OrderEvent;
import iop.postgres.cdc.orchestrator.business.payment.PaymentEvent;
import iop.postgres.cdc.orchestrator.business.properties.DomainTableProperties;
import iop.postgres.cdc.orchestrator.business.shipping.ShippingEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
@EnableConfigurationProperties(DomainTableProperties.class)
public class EventFactory {

    private final DomainTableProperties domainTableProperties;

    private static Object convertValue(String value, Class<?> fieldType) {
        if(Objects.isNull(value)){
            return null;
        }
        if (fieldType == double.class || fieldType == Double.class) {
            return Double.parseDouble(value);
        }
        if (fieldType == UUID.class) {
            return UUID.fromString(value);
        }
        if( fieldType == int.class || fieldType == Integer.class){
            return Integer.parseInt(value);
        }
        return value;
    }

    public List<Event> generateEvents(JsonNode changes) {
        List<Event> events = new ArrayList<>();
        Iterator<JsonNode> itr = changes.elements();
        while (itr.hasNext()) {
            JsonNode change = itr.next();

            String table = NodeReader.getValueNullSafe(List.of("table"), change, JsonNode::asText);
            EventType eventType = EventType.of(NodeReader.getValueNullSafe(List.of("kind"), change, JsonNode::asText));

            if (eventType == EventType.UNHANDLED || StringUtils.isBlank(table)) {
                continue;
            }

            Event event = createEvent(table, eventType);

            if (Objects.isNull(event)) {
                continue;
            }

            populateEvent(event, change);

            events.add(event);
        }
        return events;
    }

    public Event createEvent(String tableName, EventType eventType) {
        if (domainTableProperties.getOrder().equalsIgnoreCase(tableName)) {
            switch (eventType) {
                case INSERT:
                    return new OrderEvent(eventType);
                default:
                    return handleNoMatchingEvent(tableName, eventType);
            }
        } else if (domainTableProperties.getCommerceItem().equalsIgnoreCase(tableName)) {
            switch (eventType) {
                case INSERT:
                    return new CommerceItemEvent(eventType);
                default:
                    return handleNoMatchingEvent(tableName, eventType);
            }
        } else if (domainTableProperties.getPayment().equalsIgnoreCase(tableName)) {
            switch (eventType) {
                case INSERT:
                    return new PaymentEvent(eventType);
                default:
                    return handleNoMatchingEvent(tableName, eventType);
            }
        } else if (domainTableProperties.getShipping().equalsIgnoreCase(tableName)) {
            switch (eventType) {
                case INSERT:
                    return new ShippingEvent(eventType);
                default:
                    return handleNoMatchingEvent(tableName, eventType);
            }
        }
        return handleNoMatchingEvent(tableName, eventType);
    }

    private Event handleNoMatchingEvent(String tableName, EventType eventType) {
        log.info("No matching event found for table: " + tableName + " and event type: " + eventType);
        return null;
    }

    private void populateEvent(Event event, JsonNode change) {
        List<String> columnNames = fetchColumnNames(change);
        List<String> columnValues = fetchColumnValues(change);

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
                    log.info(errorMessage);
                    event.setError(errorMessage);
                }
            } catch (NoSuchFieldException e) {
                log.info(String.format("No field found with name %s for event %s", fieldName,
                    event.getClass().getName()));
            }
        }
    }

    private List<String> fetchColumnNames(JsonNode change) {
        Iterator<JsonNode> columnNamesItr = change.get("columnnames").elements();
        List<String> columnNames = new ArrayList<>();
        while (columnNamesItr.hasNext()) {
            columnNames.add(NodeReader.getNullSafeStringValueFromNode(columnNamesItr.next()));
        }
        return columnNames;
    }

    private List<String> fetchColumnValues(JsonNode change) {
        Iterator<JsonNode> columnValuesItr = change.get("columnvalues").elements();
        List<String> columnValues = new ArrayList<>();
        while (columnValuesItr.hasNext()) {
            columnValues.add(NodeReader.getNullSafeStringValueFromNode(columnValuesItr.next()));
        }
        return columnValues;
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

}
