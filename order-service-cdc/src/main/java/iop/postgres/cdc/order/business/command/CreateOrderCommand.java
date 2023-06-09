package iop.postgres.cdc.order.business.command;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CreateOrderCommand extends Command {

    @Serial
    private static final long serialVersionUID = 8551571789081229763L;

    private CommandType type;
    private String userEmail;
    private Double amount;
    private List<CommerceItem> commerceItems;

    public record CommerceItem(
        UUID id,
        UUID productId,
        UUID orderId,
        int quantity,
        double price,
        double totalPrice
    ) {

    }
}
