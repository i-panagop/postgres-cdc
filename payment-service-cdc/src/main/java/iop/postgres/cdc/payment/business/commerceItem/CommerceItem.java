package iop.postgres.cdc.payment.business.commerceItem;

import java.util.UUID;

public record CommerceItem(
    UUID id,
    UUID productId,
    UUID orderId,
    int quantity,
    double price,
    double totalPrice
) {

}
