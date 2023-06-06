package iop.postgres.cdc.order.business.command;

import iop.postgres.cdc.order.business.commerceItem.CommerceItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommerceItemCreateCommand extends Command {

    @Serial
    private static final long serialVersionUID = 382928089178370236L;

    private List<CommerceItem> items;

    public CommerceItemCreateCommand(List<CommerceItem> items) {
        this.items = items;
    }

}
