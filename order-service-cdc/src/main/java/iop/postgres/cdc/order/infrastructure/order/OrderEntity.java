package iop.postgres.cdc.order.infrastructure.order;

import iop.postgres.cdc.order.api.order.OrderDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "order_cdc")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -260629746795939855L;

    @Id
    private UUID id;
    @Column(name = "user_id")
    private UUID userId;
    private Double amount;

    public static OrderEntity of(OrderDto orderDto) {
        UUID orderId = Objects.isNull(orderDto.orderId()) ? UUID.randomUUID() : orderDto.orderId();
        return new OrderEntity(orderId, orderDto.userId(), orderDto.amount());
    }
}
