package iop.postgres.cdc.order.connector.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import iop.postgres.cdc.order.infrastructure.pglistener.PgRowMapper;
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
public class OutboundMessageHandler implements GenericHandler<PgRowMapper.PgSlotChange> {

    private static final String TOPIC = "orchestrator-topic";
    private static final String ROUTING_KEY = "orchestrator-rk";

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Object handle(PgRowMapper.PgSlotChange pgSlotChange, MessageHeaders headers) {
        try {
            if (Objects.isNull(pgSlotChange)) {
                return null;
            }
            rabbitTemplate.convertAndSend(
                TOPIC,
                ROUTING_KEY,
                objectMapper.writeValueAsString(pgSlotChange)
            );
        } catch (JsonProcessingException e) {
            log.error("Error while sending message to rabbitmq", e);
        }
        return null;
    }
}
