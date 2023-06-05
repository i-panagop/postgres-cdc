package iop.postgres.cdc.payment.infrastructure.payment;

import iop.postgres.cdc.payment.business.event.order.OrderCreationEvent;
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
@Table(name = "payment_cdc")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -7591331522339656660L;

    @Id
    private UUID id;
    private UUID orderId;
    private double amount;

    public static PaymentEntity of(OrderCreationEvent orderCreationEvent) {
        return new PaymentEntity(
            UUID.randomUUID(),
            orderCreationEvent.getId(),
            orderCreationEvent.getAmount()
        );
    }
}
