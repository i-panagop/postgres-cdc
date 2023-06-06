package iop.postgres.cdc.commerceitem.business.event;

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
    private static final long serialVersionUID = 8067907873666811766L;

    private UUID id;
    private UUID productId;
    private UUID orderId;
    private int quantity;
    private double price;
    private double totalPrice;

}
