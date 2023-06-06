package iop.postgres.cdc.payment.business.command;

import iop.postgres.cdc.payment.business.commerceItem.CommerceItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@NoArgsConstructor
public class PaymentUpdateCommand extends Command {

    @Serial
    private static final long serialVersionUID = 7316131028973951326L;

    private CommerceItem commerceItem;
}
