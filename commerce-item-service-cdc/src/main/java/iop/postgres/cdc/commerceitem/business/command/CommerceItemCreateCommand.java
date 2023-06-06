package iop.postgres.cdc.commerceitem.business.command;

import iop.postgres.cdc.commerceitem.business.commerceItem.CommerceItem;
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
    private static final long serialVersionUID = 5365750605513478867L;

    private List<CommerceItem> items;

    public CommerceItemCreateCommand(List<CommerceItem> items) {
        this.items = items;
    }

}
