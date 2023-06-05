package iop.postgres.cdc.order.business.event;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class OrderEvent extends Event {

    private UUID id;
    private String name;
    private Double amount;
    private String error;
}
