package iop.postgres.cdc.commerceitem.business.event;

import iop.postgres.cdc.commerceitem.business.commerceItem.CommerceItem;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CommerceItemCreationEvent extends Event {

    @Serial
    private static final long serialVersionUID = 8067907873666811766L;

    private List<CommerceItem> items;
}
