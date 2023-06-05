package iop.postgres.cdc.order.business.order;

import iop.postgres.cdc.order.api.order.OrderDto;
import iop.postgres.cdc.order.connector.user.User;
import iop.postgres.cdc.order.connector.user.UserServiceClient;
import iop.postgres.cdc.order.infrastructure.order.OrderEntity;
import iop.postgres.cdc.order.infrastructure.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserServiceClient userServiceClient;

    public UUID createOrder(String userEmail, double amount) {
        User user = userServiceClient.getUserByEmail(userEmail);
        OrderEntity orderEntity = new OrderEntity(UUID.randomUUID(), user.userId(), amount);
        orderEntity = orderRepository.save(orderEntity);
        return orderEntity.getId();
    }

    public OrderDto updateOrder(OrderDto orderDto) {
        return OrderDto.of(orderRepository.save(OrderEntity.of(orderDto)));
    }

    public OrderDto getOrder(UUID id) {
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow();
        return OrderDto.of(orderEntity);
    }
}
