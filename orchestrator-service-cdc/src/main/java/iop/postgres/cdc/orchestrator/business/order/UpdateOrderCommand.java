package iop.postgres.cdc.orchestrator.business.order;

import iop.postgres.cdc.orchestrator.business.DomainEnum;
import iop.postgres.cdc.orchestrator.business.command.Command;
import iop.postgres.cdc.orchestrator.business.command.CommandType;
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
    private static final long serialVersionUID = 3214714372665102856L;

    private CommandType type;
    private UUID orderId;
    private UUID paymentId;
    private UUID shippingId;

    public UpdateOrderCommand(UUID orderId, UUID paymentId, UUID shippingId) {
        super(DomainEnum.ORDER.getName(), UpdateOrderCommand.class.getSimpleName());
        this.type = CommandType.UPDATE;
        this.orderId = orderId;
        this.paymentId = paymentId;
        this.shippingId = shippingId;
    }

}
