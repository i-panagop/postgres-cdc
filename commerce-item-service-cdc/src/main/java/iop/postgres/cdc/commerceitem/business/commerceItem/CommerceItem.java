package iop.postgres.cdc.commerceitem.business.commerceItem;

import java.util.UUID;

public record CommerceItem(
    UUID productId,
    UUID orderId,
    int quantity,
    double price
) {

}
