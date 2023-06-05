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
public class OrderCreationEvent extends Event {

    @Serial
    private static final long serialVersionUID = -6838286422387217232L;

    private UUID id;
    private UUID userId;
    private Double amount;

    public OrderCreationEvent(EventType eventType) {
        super(eventType);
    }
}
