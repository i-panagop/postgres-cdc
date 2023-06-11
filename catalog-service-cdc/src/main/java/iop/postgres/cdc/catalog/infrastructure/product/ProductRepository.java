package iop.postgres.cdc.catalog.infrastructure.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {

    Set<Product> findByIdIn(Set<UUID> productIds);

    Set<Product> findByCategoryId(UUID id);
}
