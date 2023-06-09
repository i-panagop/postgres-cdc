package iop.postgres.cdc.order.business.order;

import iop.postgres.cdc.order.business.command.CreateOrderCommand;
import iop.postgres.cdc.order.business.command.CreateOrderCommand.CommerceItem;
import iop.postgres.cdc.order.business.command.UpdateOrderCommand;
import iop.postgres.cdc.order.connector.user.User;
import iop.postgres.cdc.order.connector.user.UserServiceClient;
import iop.postgres.cdc.order.infrastructure.commerceitem.CommerceItemEntity;
import iop.postgres.cdc.order.infrastructure.commerceitem.CommerceItemRepository;
import iop.postgres.cdc.order.infrastructure.order.OrderEntity;
import iop.postgres.cdc.order.infrastructure.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CommerceItemRepository commerceItemRepository;
    private final UserServiceClient userServiceClient;

    @Transactional
    public void handleCreateOrderCommand(CreateOrderCommand createOrderCommand) {
        User user = userServiceClient.getUserByEmail(createOrderCommand.getUserEmail());
        //TODO create catalog service
        //validateQuantities(orderItems);
        OrderEntity orderEntity = orderRepository.save(
            new OrderEntity(UUID.randomUUID(), user.userId(), null, null, createOrderCommand.getAmount())
        );
        for (CommerceItem commerceItem : createOrderCommand.getCommerceItems()) {
            commerceItemRepository.save(CommerceItemEntity.from(commerceItem, orderEntity.getId()));
        }
    }

    @Transactional
    public void handleUpdateOrderCommand(UpdateOrderCommand updateOrderCommand) {
        OrderEntity orderEntity = orderRepository.findById(updateOrderCommand.getOrderId()).orElseThrow();
        if (Objects.nonNull(updateOrderCommand.getShippingId())) {
            orderEntity.setShippingId(updateOrderCommand.getShippingId());
        }
        if (Objects.nonNull(updateOrderCommand.getPaymentId())) {
            orderEntity.setPaymentId(updateOrderCommand.getPaymentId());
        }
        orderRepository.save(orderEntity);
    }
}
