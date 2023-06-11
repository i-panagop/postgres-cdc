package iop.postgres.cdc.catalog.business.shipping;

import iop.postgres.cdc.catalog.business.DomainEnum;
import iop.postgres.cdc.catalog.business.command.Command;
import iop.postgres.cdc.catalog.business.command.CommandType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreateShippingCommand extends Command {

    @Serial
    private static final long serialVersionUID = 8293297877338795434L;

    private CommandType type;
    private UUID orderId;
    private UUID userId;

    public CreateShippingCommand(UUID orderId, UUID userId) {
        super(DomainEnum.SHIPPING.getName(), CreateShippingCommand.class.getSimpleName());
        this.type = CommandType.CREATE;
        this.orderId = orderId;
        this.userId = userId;
    }

}
