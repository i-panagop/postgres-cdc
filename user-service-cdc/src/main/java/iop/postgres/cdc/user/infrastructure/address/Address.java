package iop.postgres.cdc.user.infrastructure.address;

import iop.postgres.cdc.user.api.address.AddressDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "address_cdc")
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Address implements Serializable {

    @Serial
    private static final long serialVersionUID = -7802341125961237060L;

    @Id
    private UUID id;
    private String street;
    @Column(name = "street_number")
    private String streetNumber;
    private String city;
    private String country;
    @Column(name = "zip_code")
    private String zipCode;

    public static Address of(AddressDto address) {
        UUID addressId = Objects.isNull(address.id()) ? UUID.randomUUID() : address.id();
        return new Address(
            addressId,
            address.street(),
            address.streetNumber(),
            address.city(),
            address.country(),
            address.zipCode()
        );
    }
}
