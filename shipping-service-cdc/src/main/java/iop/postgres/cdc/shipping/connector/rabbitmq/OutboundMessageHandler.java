package iop.postgres.cdc.shipping.connector.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import iop.postgres.cdc.shipping.business.event.Event;
import iop.postgres.cdc.shipping.business.event.EventTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.integration.core.GenericHandler;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboundMessageHandler implements GenericHandler<Event> {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Object handle(Event event, MessageHeaders headers) {
        try {
            EventTopic eventTopic = EventTopic.forClass(event.getClass());
            if (Objects.isNull(eventTopic)) {
                return null;
            }
            rabbitTemplate.convertAndSend(
                eventTopic.getTopic(),
                eventTopic.getRoutingKey(),
                objectMapper.writeValueAsString(event)
            );
        } catch (JsonProcessingException e) {
            log.error("Error while sending message to rabbitmq", e);
        }
        return null;
    }
}
