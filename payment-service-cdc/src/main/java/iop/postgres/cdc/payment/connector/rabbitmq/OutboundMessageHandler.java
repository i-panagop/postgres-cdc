package iop.postgres.cdc.payment.connector.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import iop.postgres.cdc.payment.business.event.Event;
import iop.postgres.cdc.payment.business.event.EventTopic;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
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
            MessageProperties properties = new MessageProperties();
            properties.setHeader("eventType", event.getClass().getSimpleName());
            Message message = new Message(objectMapper.writeValueAsBytes(event), properties);
            rabbitTemplate.convertAndSend(
                eventTopic.getTopic(),
                eventTopic.getRoutingKey(),
                message
            );
        } catch (JsonProcessingException e) {
            log.error("Error while sending message to rabbitmq", e);
        }
        return null;
    }
}
