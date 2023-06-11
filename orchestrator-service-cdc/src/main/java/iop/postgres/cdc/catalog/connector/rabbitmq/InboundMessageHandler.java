package iop.postgres.cdc.catalog.connector.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import iop.postgres.cdc.catalog.business.event.EventHandler;
import iop.postgres.cdc.catalog.business.event.PgMessageBody;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class InboundMessageHandler {

    private final ObjectMapper objectMapper;
    private final EventHandler eventHandler;

    @RabbitListener(queues = "OrchestratorQueue")
    public void receiveMessage(Message message) {
        try {
            eventHandler.handle(objectMapper.readValue(message.getBody(), PgMessageBody.class));
        } catch (Exception e) {
            log.info("Error while processing message", e);
            throw new RuntimeException(e);
        }
    }
}