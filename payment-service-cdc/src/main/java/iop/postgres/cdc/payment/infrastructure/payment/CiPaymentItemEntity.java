package iop.postgres.cdc.payment.infrastructure.payment;

import iop.postgres.cdc.payment.business.command.CommerceItem;
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

import java.util.UUID;

@Entity
@Table(name = "ci_payment_item_cdc")
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@AllArgsConstructor
public class CiPaymentItemEntity {

    @Id
    private UUID id;
    @EqualsAndHashCode.Include
    @Column(name = "payment_id")
    private UUID paymentId;
    @EqualsAndHashCode.Include
    @Column(name = "product_id")
    private UUID productId;
    private int quantity;
    private double price;
    @Column(name = "total_price")
    private double totalPrice;

    public static CiPaymentItemEntity from(CommerceItem commerceItem, UUID paymentId) {
        return new CiPaymentItemEntity(
            UUID.randomUUID(),
            paymentId,
            commerceItem.productId(),
            commerceItem.quantity(),
            commerceItem.price(),
            commerceItem.totalPrice()
        );
    }

}
