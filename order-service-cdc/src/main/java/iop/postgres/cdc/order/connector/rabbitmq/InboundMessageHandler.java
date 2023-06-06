package iop.postgres.cdc.order.connector.rabbitmq;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rabbitmq.client.Channel;
import iop.postgres.cdc.order.business.event.commerceitem.CommerceItemCreationEvent;
import iop.postgres.cdc.order.business.event.payment.PaymentCreationEvent;
import iop.postgres.cdc.order.business.event.shipping.ShippingCreationEvent;
import iop.postgres.cdc.order.business.order.OrderService;
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

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @RabbitListener(queues = "OrderQueue")
    public void receiveMessage(Message message, Channel channel)
        throws IOException {
        try {
            Map<String, Object> headers = message.getMessageProperties().getHeaders();
            if (MapUtils.isEmpty(headers) || !headers.containsKey("eventType")) {
                throw new RuntimeException("Event type not found");
            }
            log.info("EventType is {}", headers.get("eventType"));
            if (ShippingCreationEvent.class.getSimpleName().equalsIgnoreCase((String) headers.get("eventType"))) {
                orderService.handleShippingCreation(objectMapper.readValue(message.getBody(), ShippingCreationEvent.class));
            } else if (PaymentCreationEvent.class.getSimpleName().equalsIgnoreCase((String) headers.get("eventType"))) {
                orderService.handlePaymentCreation(objectMapper.readValue(message.getBody(), PaymentCreationEvent.class));
            } else if (CommerceItemCreationEvent.class.getSimpleName().equalsIgnoreCase((String) headers.get("eventType"))) {
                orderService.handleCommerceItemCreation(objectMapper.readValue(message.getBody(),
                    CommerceItemCreationEvent.class));
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