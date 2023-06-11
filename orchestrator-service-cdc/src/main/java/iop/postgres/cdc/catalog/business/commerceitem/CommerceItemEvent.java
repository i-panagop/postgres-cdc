package iop.postgres.cdc.catalog.business.commerceitem;

import iop.postgres.cdc.catalog.business.event.Event;
import iop.postgres.cdc.catalog.business.event.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CommerceItemEvent extends Event {

    @Serial
    private static final long serialVersionUID = 4040785234143355958L;

    private UUID id;
    private UUID orderId;
    private UUID productId;
    private int quantity;
    private double price;
    private double totalPrice;

    public CommerceItemEvent(EventType eventType) {
        super(eventType);
    }
}
