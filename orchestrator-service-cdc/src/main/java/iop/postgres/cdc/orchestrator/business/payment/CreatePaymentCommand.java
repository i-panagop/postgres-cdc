package iop.postgres.cdc.orchestrator.business.payment;

import iop.postgres.cdc.orchestrator.business.DomainEnum;
import iop.postgres.cdc.orchestrator.business.command.Command;
import iop.postgres.cdc.orchestrator.business.command.CommandType;
import iop.postgres.cdc.orchestrator.business.commerceitem.CommerceItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreatePaymentCommand extends Command {

    @Serial
    private static final long serialVersionUID = -3251161936945603932L;

    private CommandType type;
    private UUID orderId;
    private double amount;
    private Set<CommerceItem> commerceItems = new HashSet<>();

    public CreatePaymentCommand(UUID orderId, double amount, Set<CommerceItem> commerceItems) {
        super(DomainEnum.PAYMENT.getName(), CreatePaymentCommand.class.getSimpleName());
        this.type = CommandType.CREATE;
        this.orderId = orderId;
        this.amount = amount;
        this.commerceItems = commerceItems;
    }

}
