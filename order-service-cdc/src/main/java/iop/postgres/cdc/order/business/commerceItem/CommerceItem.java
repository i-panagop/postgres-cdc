package iop.postgres.cdc.order.business.commerceItem;

import iop.postgres.cdc.order.business.event.commerceitem.CommerceItemCreationEvent;

import java.util.UUID;

public record CommerceItem(
    UUID id,
    UUID productId,
    UUID orderId,
    int quantity,
    double price,
    double totalPrice
) {

    public static CommerceItem from(CommerceItemCreationEvent commerceItemCreationEvent) {
        return new CommerceItem(
            commerceItemCreationEvent.getId(),
            commerceItemCreationEvent.getProductId(),
            commerceItemCreationEvent.getOrderId(),
            commerceItemCreationEvent.getQuantity(),
            commerceItemCreationEvent.getPrice(),
            commerceItemCreationEvent.getTotalPrice()
        );
    }
}
