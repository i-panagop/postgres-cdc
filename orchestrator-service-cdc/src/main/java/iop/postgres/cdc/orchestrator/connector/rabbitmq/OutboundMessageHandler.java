package iop.postgres.cdc.orchestrator.connector.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import iop.postgres.cdc.orchestrator.business.command.Command;
import iop.postgres.cdc.orchestrator.business.command.CommandTopic;
import jakarta.annotation.Nonnull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class OutboundMessageHandler {

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;

    public void send(List<Command> commands) {
        if (CollectionUtils.isEmpty(commands)) {
            return;
        }
        for (Command command : commands) {
            if (Objects.isNull(command)) {
                continue;
            }
            send(command);
        }
    }

    public void send(@Nonnull Command command) {
        try {
            CommandTopic commandTopic = CommandTopic.forClass(command.getClass());
            if (Objects.isNull(commandTopic)) {
                return;
            }
            MessageProperties properties = new MessageProperties();
            properties.setHeader("domain", command.getDomain());
            properties.setHeader("className", command.getClassName());
            Message message = new Message(objectMapper.writeValueAsBytes(command), properties);
            rabbitTemplate.convertAndSend(
                commandTopic.getTopic(),
                commandTopic.getRoutingKey(),
                message
            );
        } catch (JsonProcessingException e) {
            log.error("Error while sending command to rabbitmq", e);
        }
    }
}
