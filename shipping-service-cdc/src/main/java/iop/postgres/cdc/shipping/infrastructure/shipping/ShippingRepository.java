package iop.postgres.cdc.shipping.infrastructure.shipping;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ShippingRepository extends JpaRepository<ShippingEntity, UUID> {

}
