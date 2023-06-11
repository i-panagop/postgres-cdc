package iop.postgres.cdc.catalog.api.product;

import iop.postgres.cdc.catalog.domain.product.ProductModel;

import java.util.UUID;

public record ProductDto(UUID id, String name, String description, double price, int stock, UUID categoryId) {

    public static ProductDto from(ProductModel productModel) {
        return new ProductDto(
            productModel.getId(),
            productModel.getName(),
            productModel.getDescription(),
            productModel.getPrice(),
            productModel.getStock(),
            productModel.getCategoryId()
        );
    }

}
