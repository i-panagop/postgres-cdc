package iop.postgres.cdc.payment.connector.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micrometer.common.util.StringUtils;
import iop.postgres.cdc.payment.business.command.Command;
import iop.postgres.cdc.payment.business.command.CreatePaymentCommand;
import iop.postgres.cdc.payment.business.payment.PaymentService;
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

    private static final String COMMAND_CLASSPATH = "iop.postgres.cdc.payment.business.command.";

    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "PaymentQueue")
    public void receiveMessage(Message message) {
        try {
            Map<String, Object> headers = message.getMessageProperties().getHeaders();
            String domain = (String) headers.get("domain");
            String className = (String) headers.get("className");
            if (MapUtils.isEmpty(headers) || StringUtils.isBlank(domain) || StringUtils.isBlank(className)) {
                throw new RuntimeException("Event headers not found");
            }
            if (!"payment".equalsIgnoreCase(domain)) {
                throw new RuntimeException("Domain:" + domain + " , is not the correct domain for payment service");
            }
            log.info("Event with class {} came.", className);
            Class<?> clazz = Class.forName(COMMAND_CLASSPATH + className);
            Command command = (Command) objectMapper.readValue(message.getBody(), clazz);
            if (command instanceof CreatePaymentCommand createPaymentCommand) {
                paymentService.handleCreatePaymentCommand(createPaymentCommand);
            } else {
                log.info("Event type not found");
            }
        } catch (Exception e) {
            log.info("Error while processing message", e);
            throw new RuntimeException(e);
        }
    }
}