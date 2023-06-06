package iop.postgres.cdc.payment.connector.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import iop.postgres.cdc.payment.business.command.PaymentUpdateCommand;
import iop.postgres.cdc.payment.business.event.order.OrderCreationEvent;
import iop.postgres.cdc.payment.business.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.MapUtils;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class InboundMessageHandler {

    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "PaymentQueue")
    public void receiveMessage(Message message, Channel channel)
        throws IOException {
        try {
            Map<String, Object> headers = message.getMessageProperties().getHeaders();
            if (MapUtils.isEmpty(headers) || !headers.containsKey("eventType")) {
                throw new RuntimeException("Event type not found");
            }
            log.info("EventType is {}", headers.get("eventType"));
            if (OrderCreationEvent.class.getSimpleName().equalsIgnoreCase((String) headers.get("eventType"))) {
                OrderCreationEvent orderCreationEvent =
                    objectMapper.readValue(message.getBody(), OrderCreationEvent.class);
                paymentService.createPayment(orderCreationEvent);
            } else if (PaymentUpdateCommand.class.getSimpleName().equalsIgnoreCase((String) headers.get("eventType"))) {
                PaymentUpdateCommand paymentUpdateCommand =
                    objectMapper.readValue(message.getBody(), PaymentUpdateCommand.class);
                paymentService.updateOrCreatePayment(paymentUpdateCommand);
            } else {
                log.info("Event type not found");
            }
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (Exception e) {
            log.info("Error while processing message", e);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}