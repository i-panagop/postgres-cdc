package iop.postgres.cdc.catalog.infrastructure.product;

import iop.postgres.cdc.catalog.domain.product.ProductModel;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "product_cdc")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Product implements Serializable {

    @Serial
    private static final long serialVersionUID = 4642545600304878052L;

    @Id
    private UUID id;
    private String name;
    private String description;
    private double price;
    private int stock;
    @Column(name = "category_id")
    private UUID categoryId;

    public static ProductModel toModel(Product product) {
        return new ProductModel(
            product.getId(),
            product.getName(),
            product.getDescription(),
            product.getPrice(),
            product.getStock(),
            product.getCategoryId()
        );
    }
}
