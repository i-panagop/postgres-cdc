package iop.postgres.cdc.payment.business.event.order;

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
public class OrderCreationEvent extends Event {

    @Serial
    private static final long serialVersionUID = 2458744210437013074L;

    private UUID id;
    private UUID userId;
    private Double amount;

    public OrderCreationEvent(EventType eventType) {
        super(eventType);
    }
}
