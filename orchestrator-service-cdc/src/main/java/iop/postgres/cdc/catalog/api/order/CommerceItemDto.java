package iop.postgres.cdc.catalog.api.order;


import iop.postgres.cdc.catalog.business.commerceitem.CommerceItem;

import java.util.UUID;
import javax.validation.constraints.Min;

public record CommerceItemDto(
    UUID productId,
    @Min(value = 1, message = "Quantity must be at least 1")
    int quantity,
    double price
) {

    public static CommerceItem to(CommerceItemDto commerceItemDto) {
        return new CommerceItem(
            null,
            null,
            commerceItemDto.productId(),
            commerceItemDto.quantity(),
            commerceItemDto.price(),
            commerceItemDto.price() * commerceItemDto.quantity()
        );
    }
}
