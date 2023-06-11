package iop.postgres.cdc.catalog.api.product;

import iop.postgres.cdc.catalog.domain.product.ProductAvailabilityResModel;

import java.util.UUID;

public record ProductAvailabilityResDto(UUID id, boolean available, double price) {

    public static ProductAvailabilityResDto from(ProductAvailabilityResModel productAvailabilityResModel) {
        return new ProductAvailabilityResDto(
            productAvailabilityResModel.getId(),
            productAvailabilityResModel.isAvailable(),
            productAvailabilityResModel.getPrice()
        );
    }
}
