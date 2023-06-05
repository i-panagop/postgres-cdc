package iop.postgres.cdc.order.business.event.shipping;

import iop.postgres.cdc.order.business.event.Event;
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
    private static final long serialVersionUID = -1340860897199984550L;

    private UUID id;
    private UUID orderId;
}
