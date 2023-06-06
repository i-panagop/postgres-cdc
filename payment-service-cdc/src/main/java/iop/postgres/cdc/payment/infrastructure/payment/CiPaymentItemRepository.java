package iop.postgres.cdc.payment.infrastructure.payment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CiPaymentItemRepository extends JpaRepository<CiPaymentItemEntity, UUID> {
}
