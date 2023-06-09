package iop.postgres.cdc.order.connector.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import iop.postgres.cdc.order.business.command.Command;
import iop.postgres.cdc.order.business.command.CreateOrderCommand;
import iop.postgres.cdc.order.business.command.UpdateOrderCommand;
import iop.postgres.cdc.order.business.order.OrderService;
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

    private static final String COMMAND_CLASSPATH = "iop.postgres.cdc.order.business.command.";

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "OrderQueue")
    public void receiveMessage(Message message) {
        try {
            Map<String, Object> headers = message.getMessageProperties().getHeaders();
            String domain = (String) headers.get("domain");
            String className = (String) headers.get("className");
            if (MapUtils.isEmpty(headers) || StringUtils.isBlank(domain) || StringUtils.isBlank(className)) {
                throw new RuntimeException("Event headers not found");
            }
            if (!"order".equalsIgnoreCase(domain)) {
                throw new RuntimeException("Domain:" + domain + " , is not the correct domain for order service");
            }
            log.info("Event with class {} came.", className);
            Class<?> clazz = Class.forName(COMMAND_CLASSPATH + className);
            Command command = (Command) objectMapper.readValue(message.getBody(), clazz);
            if (command instanceof CreateOrderCommand createOrderCommand) {
                orderService.handleCreateOrderCommand(createOrderCommand);
            } else if (command instanceof UpdateOrderCommand updateOrderCommand) {
                orderService.handleUpdateOrderCommand(updateOrderCommand);
            } else {
                log.info("Event type not found");
            }
        } catch (Exception e) {
            log.info("Error while processing message", e);
            throw new RuntimeException(e);
        }
    }
}