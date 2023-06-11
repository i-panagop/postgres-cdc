package iop.postgres.cdc.catalog.domain.product;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 6239318190113912447L;

    private UUID id;
    private String name;
    private String description;
    private double price;
    private int stock;
    private UUID categoryId;
}
