package iop.postgres.cdc.order.business.order;

import iop.postgres.cdc.order.api.order.CommerceItemDto;
import iop.postgres.cdc.order.api.order.OrderDto;
import iop.postgres.cdc.order.business.command.CommerceItemCreateCommand;
import iop.postgres.cdc.order.business.command.PaymentUpdateCommand;
import iop.postgres.cdc.order.business.event.EventType;
import iop.postgres.cdc.order.business.event.commerceitem.CommerceItemCreationEvent;
import iop.postgres.cdc.order.business.event.payment.PaymentCreationEvent;
import iop.postgres.cdc.order.business.event.shipping.ShippingCreationEvent;
import iop.postgres.cdc.order.connector.rabbitmq.OutboundMessageHandler;
import iop.postgres.cdc.order.connector.user.User;
import iop.postgres.cdc.order.connector.user.UserServiceClient;
import iop.postgres.cdc.order.infrastructure.order.OrderEntity;
import iop.postgres.cdc.order.infrastructure.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserServiceClient userServiceClient;
    private final OutboundMessageHandler outboundMessageHandler;

    public UUID createOrder(String userEmail, double amount, List<CommerceItemDto> orderItems) {
        User user = userServiceClient.getUserByEmail(userEmail);
        //TODO create catalog service
        //validateQuantities(orderItems);
        OrderEntity orderEntity = orderRepository.save(
            new OrderEntity(UUID.randomUUID(), user.userId(), null, null, amount)
        );
        publishCommerceItemCreationEvent(orderItems, orderEntity.getId());
        return orderEntity.getId();
    }

    private void publishCommerceItemCreationEvent(List<CommerceItemDto> orderItems, UUID orderId) {
        outboundMessageHandler.send(
            new CommerceItemCreateCommand(
                orderItems.stream()
                    .map(ci -> CommerceItemDto.to(ci, orderId))
                    .toList()
            )
        );
    }

    public OrderDto updateOrder(OrderDto orderDto) {
        return OrderDto.of(orderRepository.save(OrderEntity.of(orderDto)));
    }

    public OrderDto getOrder(UUID id) {
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow();
        return OrderDto.of(orderEntity);
    }

    @Transactional
    public void handleShippingCreation(ShippingCreationEvent shippingCreationEvent) {
        OrderEntity orderEntity = orderRepository.findById(shippingCreationEvent.getOrderId()).orElseThrow();
        orderEntity.setShippingId(shippingCreationEvent.getId());
        orderRepository.save(orderEntity);
    }

    @Transactional
    public void handlePaymentCreation(PaymentCreationEvent paymentCreationEvent) {
        if(!EventType.INSERT.equals(paymentCreationEvent.getType())){
            return;
        }
        OrderEntity orderEntity = orderRepository.findById(paymentCreationEvent.getOrderId()).orElseThrow();
        orderEntity.setPaymentId(paymentCreationEvent.getId());
        orderRepository.save(orderEntity);
    }

    public void handleCommerceItemCreation(CommerceItemCreationEvent commerceItemCreationEvent) {
        outboundMessageHandler.send(
            PaymentUpdateCommand.from(commerceItemCreationEvent)
        );
    }
}
