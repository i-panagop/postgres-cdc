package iop.postgres.cdc.catalog.api.product;

import iop.postgres.cdc.catalog.domain.product.ProductAvailabilityReqModel;

import java.util.UUID;

public record ProductAvailabilityReqDto(UUID id, int quantity) {

    public static ProductAvailabilityReqModel to(ProductAvailabilityReqDto productAvailabilityReqDto) {
        return new ProductAvailabilityReqModel(
            productAvailabilityReqDto.id(),
            productAvailabilityReqDto.quantity()
        );
    }
}
