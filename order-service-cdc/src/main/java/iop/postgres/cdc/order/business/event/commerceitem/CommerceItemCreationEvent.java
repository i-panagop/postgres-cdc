package iop.postgres.cdc.order.business.event.commerceitem;

import iop.postgres.cdc.order.business.commerceItem.CommerceItem;
import iop.postgres.cdc.order.business.event.Event;
import iop.postgres.cdc.order.business.event.EventType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommerceItemCreationEvent extends Event {

    @Serial
    private static final long serialVersionUID = 663500005837430016L;

    private List<CommerceItem> items;

    public CommerceItemCreationEvent(EventType eventType, List<CommerceItem> items) {
        super(eventType);
        this.items = items;
    }
}
