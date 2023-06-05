package iop.postgres.cdc.order.api.order;

import iop.postgres.cdc.order.business.commerceItem.CommerceItem;

import java.util.UUID;
import javax.validation.constraints.Min;

public record CommerceItemDto(
    UUID productId,
    @Min(value = 1, message = "Quantity must be at least 1")
    int quantity,
    double price
) {

    public static CommerceItem to(CommerceItemDto commerceItemDto, UUID orderId) {
        return new CommerceItem(
            commerceItemDto.productId(),
            orderId,
            commerceItemDto.quantity(),
            commerceItemDto.price()
        );
    }
}
