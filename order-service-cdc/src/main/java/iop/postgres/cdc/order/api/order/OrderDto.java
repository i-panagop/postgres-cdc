package iop.postgres.cdc.order.api.order;

import iop.postgres.cdc.order.infrastructure.order.OrderEntity;

import java.util.UUID;

public record OrderDto(UUID orderId, UUID userId, Double amount) {

    public static OrderDto of(OrderEntity orderEntity) {
        return new OrderDto(orderEntity.getId(), orderEntity.getUserId(), orderEntity.getAmount());
    }
}

