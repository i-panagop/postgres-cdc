package iop.postgres.cdc.order.connector.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import iop.postgres.cdc.order.business.event.shipping.ShippingCreationEvent;
import iop.postgres.cdc.order.business.order.OrderService;
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

    private final ObjectMapper objectMapper;
    private final OrderService orderService;

    @RabbitListener(queues = "OrderQueue")
    public void receiveMessage(Message message, Channel channel) throws IOException {
        try {
            ShippingCreationEvent shippingCreationEvent =
                objectMapper.readValue(message.getBody(), ShippingCreationEvent.class);
            orderService.handleShipping(shippingCreationEvent);
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
        } catch (Exception e) {
            log.info("Error while processing message", e);
            channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
        }
    }
}