package iop.postgres.cdc.shipping.business.event.shipping;

import iop.postgres.cdc.shipping.business.event.Event;
import iop.postgres.cdc.shipping.business.event.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ShippingCreationEvent extends Event {

    @Serial
    private static final long serialVersionUID = 2799464399950472472L;

    private UUID id;
    private UUID orderId;

    public ShippingCreationEvent(EventType eventType) {
        super(eventType);
    }
}
