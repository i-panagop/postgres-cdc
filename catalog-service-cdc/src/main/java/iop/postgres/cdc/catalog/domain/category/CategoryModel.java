package iop.postgres.cdc.catalog.domain.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel implements Serializable {

    @Serial
    private static final long serialVersionUID = -8989508926578296683L;

    private UUID id;
    private String name;
    private int categoryPosition;
    private UUID parentCategoryId;
    private List<CategoryModel> children;

}
