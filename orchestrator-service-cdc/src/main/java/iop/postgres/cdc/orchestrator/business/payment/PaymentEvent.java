package iop.postgres.cdc.orchestrator.business.payment;

import iop.postgres.cdc.orchestrator.business.event.Event;
import iop.postgres.cdc.orchestrator.business.event.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class PaymentEvent extends Event {

    @Serial
    private static final long serialVersionUID = -2287369042155873968L;

    private UUID id;
    private UUID orderId;
    private double amount;

    public PaymentEvent(EventType eventType) {
        super(eventType);
    }
}
