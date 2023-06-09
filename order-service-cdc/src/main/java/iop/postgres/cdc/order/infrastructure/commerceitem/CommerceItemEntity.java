package iop.postgres.cdc.order.infrastructure.commerceitem;

import iop.postgres.cdc.order.business.command.CreateOrderCommand.CommerceItem;
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
@Table(name = "commerce_item_cdc")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class CommerceItemEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 4222219629844104444L;

    @Id
    private UUID id;
    private UUID orderId;
    private UUID productId;
    private int quantity;
    private double price;
    @Column(name = "total_price")
    private double totalPrice;

    public static CommerceItemEntity from(CommerceItem commerceItem, UUID orderId) {
        return new CommerceItemEntity(
            UUID.randomUUID(),
            orderId,
            commerceItem.productId(),
            commerceItem.quantity(),
            commerceItem.price(),
            commerceItem.quantity() * commerceItem.price()
        );
    }
}
