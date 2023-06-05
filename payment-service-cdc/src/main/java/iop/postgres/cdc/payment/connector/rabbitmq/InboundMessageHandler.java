package iop.postgres.cdc.payment.connector.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import iop.postgres.cdc.payment.business.event.order.OrderCreationEvent;
import iop.postgres.cdc.payment.business.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

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
            OrderCreationEvent orderCreationEvent = objectMapper.readValue(message.getBody(), OrderCreationEvent.class);
            paymentService.createPayment(orderCreationEvent);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (Exception e) {
            log.info("Error while processing message", e);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}