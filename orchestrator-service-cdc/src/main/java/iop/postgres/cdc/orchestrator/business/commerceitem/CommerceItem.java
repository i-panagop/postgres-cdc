package iop.postgres.cdc.orchestrator.business.commerceitem;

import java.util.UUID;

public record CommerceItem(
    UUID id,
    UUID orderId,
    UUID productId,
    int quantity,
    double price,
    double totalPrice
) {

    public static CommerceItem from(CommerceItemEvent commerceItemEvent) {
        return new CommerceItem(
            commerceItemEvent.getId(),
            commerceItemEvent.getOrderId(),
            commerceItemEvent.getProductId(),
            commerceItemEvent.getQuantity(),
            commerceItemEvent.getPrice(),
            commerceItemEvent.getTotalPrice()
        );
    }
}
