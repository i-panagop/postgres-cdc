package iop.postgres.cdc.catalog.infrastructure.category;

import iop.postgres.cdc.catalog.domain.category.CategoryModel;
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
@Table(name = "category_cdc")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {

    @Serial
    private static final long serialVersionUID = 8037770600311991620L;

    @Id
    private UUID id;
    private String name;
    @Column(name = "category_position")
    private int categoryPosition;
    @Column(name = "parent_category_id")
    private UUID parentCategoryId;

    public static CategoryModel toModel(Category category) {
        return new CategoryModel(category.getId(), category.getName(), category.getCategoryPosition(),
            category.getParentCategoryId(), null);
    }

}
