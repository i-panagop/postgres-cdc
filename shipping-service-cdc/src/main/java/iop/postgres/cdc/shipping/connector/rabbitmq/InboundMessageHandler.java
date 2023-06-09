package iop.postgres.cdc.shipping.connector.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import iop.postgres.cdc.shipping.business.command.Command;
import iop.postgres.cdc.shipping.business.command.CreateShippingCommand;
import iop.postgres.cdc.shipping.business.exception.UserNotFoundException;
import iop.postgres.cdc.shipping.business.shipping.ShippingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class InboundMessageHandler {

    private static final String COMMAND_CLASSPATH = "iop.postgres.cdc.shipping.business.command.";

    private final ObjectMapper objectMapper;
    private final ShippingService shippingService;

    @RabbitListener(queues = "ShippingQueue")
    public void receiveMessage(Message message) {
        try {
            Map<String, Object> headers = message.getMessageProperties().getHeaders();
            String domain = (String) headers.get("domain");
            String className = (String) headers.get("className");
            if (MapUtils.isEmpty(headers) || StringUtils.isBlank(domain) || StringUtils.isBlank(className)) {
                throw new RuntimeException("Event headers not found");
            }
            if (!"shipping".equalsIgnoreCase(domain)) {
                throw new RuntimeException("Domain:" + domain + " , is not the correct domain for shipping service");
            }
            log.info("Event with class {} came.", className);
            Class<?> clazz = Class.forName(COMMAND_CLASSPATH + className);
            Command command = (Command) objectMapper.readValue(message.getBody(), clazz);
            if (command instanceof CreateShippingCommand createShippingCommand) {
                shippingService.handleCreateShippingCommand(createShippingCommand);
            } else {
                log.info("Event type not found");
            }
        } catch (Exception | UserNotFoundException e) {
            log.info("Error while processing message", e);
            throw new RuntimeException(e);
        }
    }
}