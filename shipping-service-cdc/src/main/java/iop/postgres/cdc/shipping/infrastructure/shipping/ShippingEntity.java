package iop.postgres.cdc.shipping.infrastructure.shipping;

import iop.postgres.cdc.shipping.business.event.order.OrderCreationEvent;
import iop.postgres.cdc.shipping.connector.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Entity
@Table(name = "shipping_cdc")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class ShippingEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = -7845006530663759070L;

    @Id
    private UUID id;
    private UUID orderId;
    private UUID userId;
    private String name;
    private String email;
    private String street;
    @Column(name = "street_number")
    private String streetNumber;
    private String city;
    private String country;
    @Column(name = "zip_code")
    private String zipCode;

    public static ShippingEntity of(OrderCreationEvent orderCreationEvent, User user) {
        return new ShippingEntity(
            UUID.randomUUID(),
            orderCreationEvent.getId(),
            orderCreationEvent.getUserId(),
            user.name(),
            user.email(),
            user.address().street(),
            user.address().streetNumber(),
            user.address().city(),
            user.address().country(),
            user.address().zipCode()
        );
    }
}