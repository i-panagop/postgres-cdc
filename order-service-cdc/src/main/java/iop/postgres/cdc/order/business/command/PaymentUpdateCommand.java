package iop.postgres.cdc.order.business.command;

import iop.postgres.cdc.order.business.commerceItem.CommerceItem;
import iop.postgres.cdc.order.business.event.commerceitem.CommerceItemCreationEvent;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;

@Getter
@Setter
@NoArgsConstructor
public class PaymentUpdateCommand extends Command {

    @Serial
    private static final long serialVersionUID = 1297564878445876407L;

    private CommerceItem commerceItem;

    public PaymentUpdateCommand(CommerceItem commerceItem) {
        this.commerceItem = commerceItem;
    }

    public static PaymentUpdateCommand from(CommerceItemCreationEvent commerceItemCreationEvent) {
        return new PaymentUpdateCommand(CommerceItem.from(commerceItemCreationEvent));
    }
}
