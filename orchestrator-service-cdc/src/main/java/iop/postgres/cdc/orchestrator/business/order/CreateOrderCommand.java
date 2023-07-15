package iop.postgres.cdc.orchestrator.business.order;

import iop.postgres.cdc.orchestrator.business.DomainEnum;
import iop.postgres.cdc.orchestrator.business.command.Command;
import iop.postgres.cdc.orchestrator.business.command.CommandType;
import iop.postgres.cdc.orchestrator.business.commerceitem.CommerceItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrderCommand extends Command {

    @Serial
    private static final long serialVersionUID = 6094365365476124452L;

    private CommandType type;
    private String userEmail;
    private Double amount;
    private List<CommerceItem> commerceItems;

    public CreateOrderCommand(String userEmail, Double amount, List<CommerceItem> commerceItems) {
        super(DomainEnum.ORDER.getName(), CreateOrderCommand.class.getSimpleName());
        this.type = CommandType.CREATE;
        this.userEmail = userEmail;
        this.amount = amount;
        this.commerceItems = commerceItems;
    }

}
