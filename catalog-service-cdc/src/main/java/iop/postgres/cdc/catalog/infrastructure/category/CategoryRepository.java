package iop.postgres.cdc.catalog.infrastructure.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {

    Set<Category> findByParentCategoryIdOrderByCategoryPosition(UUID parentCategoryId);

    Optional<Category> findByName(String root);
}
