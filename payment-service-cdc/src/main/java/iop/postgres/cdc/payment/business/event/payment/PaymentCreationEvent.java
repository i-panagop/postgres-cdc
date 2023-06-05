package iop.postgres.cdc.payment.business.event.payment;

import iop.postgres.cdc.payment.business.event.Event;
import iop.postgres.cdc.payment.business.event.EventType;
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
    private static final long serialVersionUID = -1577768305688569658L;

    private UUID id;
    private UUID orderId;

    public PaymentCreationEvent(EventType eventType) {
        super(eventType);
    }
}
