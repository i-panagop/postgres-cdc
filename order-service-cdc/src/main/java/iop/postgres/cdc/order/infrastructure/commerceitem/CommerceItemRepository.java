package iop.postgres.cdc.order.infrastructure.commerceitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CommerceItemRepository extends JpaRepository<CommerceItemEntity, UUID> {

}
