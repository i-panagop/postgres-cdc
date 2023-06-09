package iop.postgres.cdc.payment.business.command;

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

}
