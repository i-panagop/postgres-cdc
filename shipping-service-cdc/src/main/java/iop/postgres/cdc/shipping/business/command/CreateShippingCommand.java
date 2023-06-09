package iop.postgres.cdc.shipping.business.command;

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
    private static final long serialVersionUID = -7203600796985787202L;

    private CommandType type;
    private UUID orderId;
    private UUID userId;

}
