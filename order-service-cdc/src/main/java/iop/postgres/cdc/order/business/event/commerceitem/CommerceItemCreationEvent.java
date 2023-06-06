package iop.postgres.cdc.order.business.event.commerceitem;

import iop.postgres.cdc.order.business.event.Event;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CommerceItemCreationEvent extends Event {

    @Serial
    private static final long serialVersionUID = 663500005837430016L;

    private UUID id;
    private UUID productId;
    private UUID orderId;
    private int quantity;
    private double price;
    private double totalPrice;

}
