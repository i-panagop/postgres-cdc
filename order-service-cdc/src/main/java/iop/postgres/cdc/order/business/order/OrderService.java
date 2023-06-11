package iop.postgres.cdc.order.business.order;

import iop.postgres.cdc.order.business.command.CreateOrderCommand;
import iop.postgres.cdc.order.business.command.CreateOrderCommand.CommerceItem;
import iop.postgres.cdc.order.business.command.UpdateOrderCommand;
import iop.postgres.cdc.order.connector.catalog.CatalogServiceClient;
import iop.postgres.cdc.order.connector.catalog.ProductAvailabilityReqDto;
import iop.postgres.cdc.order.connector.catalog.ProductAvailabilityResDto;
import iop.postgres.cdc.order.connector.user.User;
import iop.postgres.cdc.order.connector.user.UserServiceClient;
import iop.postgres.cdc.order.infrastructure.commerceitem.CommerceItemEntity;
import iop.postgres.cdc.order.infrastructure.commerceitem.CommerceItemRepository;
import iop.postgres.cdc.order.infrastructure.order.OrderEntity;
import iop.postgres.cdc.order.infrastructure.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CommerceItemRepository commerceItemRepository;
    private final UserServiceClient userServiceClient;
    private final CatalogServiceClient catalogServiceClient;

    @Transactional
    public void handleCreateOrderCommand(CreateOrderCommand createOrderCommand) {
        User user = userServiceClient.getUserByEmail(createOrderCommand.getUserEmail());
        checkAvailability(createOrderCommand.getCommerceItems());
        OrderEntity orderEntity = orderRepository.save(
            new OrderEntity(UUID.randomUUID(), user.userId(), null, null, createOrderCommand.getAmount())
        );
        for (CommerceItem commerceItem : createOrderCommand.getCommerceItems()) {
            commerceItemRepository.save(CommerceItemEntity.from(commerceItem, orderEntity.getId()));
        }
    }

    private void checkAvailability(List<CommerceItem> commerceItems) {
        List<ProductAvailabilityResDto> productAvailabilityResModels = catalogServiceClient.checkAvailability(
            commerceItems.stream()
                .map(commerceItem -> new ProductAvailabilityReqDto(commerceItem.productId(), commerceItem.quantity()))
                .collect(Collectors.toList())
        );
        for (ProductAvailabilityResDto productAvailabilityResModel : productAvailabilityResModels) {
            if (!productAvailabilityResModel.available()) {
                throw new RuntimeException("Product " + productAvailabilityResModel.id() + " is not available");
            }
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
