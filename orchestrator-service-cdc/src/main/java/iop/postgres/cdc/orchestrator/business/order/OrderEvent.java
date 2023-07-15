package iop.postgres.cdc.orchestrator.business.order;

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
public class OrderEvent extends Event {

    @Serial
    private static final long serialVersionUID = 2458744210437013074L;

    private UUID id;
    private UUID userId;
    private UUID shippingId;
    private UUID paymentId;
    private Double amount;

    public OrderEvent(EventType eventType) {
        super(eventType);
    }
}
