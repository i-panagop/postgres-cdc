package iop.postgres.cdc.payment.business.command;

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
