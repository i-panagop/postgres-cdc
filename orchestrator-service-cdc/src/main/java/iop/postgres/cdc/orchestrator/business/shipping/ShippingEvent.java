package iop.postgres.cdc.orchestrator.business.shipping;

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
public class ShippingEvent extends Event {

    @Serial
    private static final long serialVersionUID = 5843997710914515688L;

    private UUID id;
    private UUID orderId;

    public ShippingEvent(EventType eventType) {
        super(eventType);
    }
}
