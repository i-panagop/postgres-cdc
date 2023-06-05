package iop.postgres.cdc.order.business.event.order;

import iop.postgres.cdc.order.business.event.Event;
import iop.postgres.cdc.order.business.event.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class OrderUpdateEvent extends Event {

    @Serial
    private static final long serialVersionUID = 1490691021813932303L;

    private UUID id;
    private UUID userId;
    private Double amount;

    public OrderUpdateEvent(EventType eventType) {
        super(eventType);
    }
}
