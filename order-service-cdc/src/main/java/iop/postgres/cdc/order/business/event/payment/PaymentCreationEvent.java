package iop.postgres.cdc.order.business.event.payment;

import iop.postgres.cdc.order.business.event.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PaymentCreationEvent extends Event {

    @Serial
    private static final long serialVersionUID = -1853774372840350266L;

    private UUID id;
    private UUID orderId;
}
