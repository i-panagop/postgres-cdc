package iop.postgres.cdc.order.business.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UpdateOrderCommand extends Command {

    @Serial
    private static final long serialVersionUID = -8294159390127975870L;

    private CommandType type;
    private UUID orderId;
    private UUID paymentId;
    private UUID shippingId;

}
