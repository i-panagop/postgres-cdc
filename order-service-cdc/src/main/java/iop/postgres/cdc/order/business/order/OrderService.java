package iop.postgres.cdc.order.business.order;

import iop.postgres.cdc.order.api.order.OrderDto;
import iop.postgres.cdc.order.infrastructure.order.OrderEntity;
import iop.postgres.cdc.order.infrastructure.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public UUID createOrder(String name) {
        OrderEntity orderEntity = new OrderEntity(UUID.randomUUID(), name, 0.0d);
        orderEntity = orderRepository.save(orderEntity);
        return orderEntity.getId();
    }

    public UUID createOrder(OrderDto orderDto) {
        return orderRepository.save(OrderEntity.of(orderDto)).getId();
    }

    public OrderDto updateOrder(OrderDto orderDto) {
        return OrderDto.of(orderRepository.save(OrderEntity.of(orderDto)));
    }

    public OrderDto getOrder(UUID id) {
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow();
        return OrderDto.of(orderEntity);
    }
}
