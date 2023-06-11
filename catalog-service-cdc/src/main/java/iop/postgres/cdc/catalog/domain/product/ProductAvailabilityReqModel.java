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
public class ProductAvailabilityReqModel implements Serializable {

    @Serial
    private static final long serialVersionUID = 421395403055055015L;

    private UUID id;
    private int quantity;
}
