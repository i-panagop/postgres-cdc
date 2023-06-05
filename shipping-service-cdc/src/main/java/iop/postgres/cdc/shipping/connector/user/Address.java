package iop.postgres.cdc.shipping.connector.user;

import java.util.UUID;

public record Address(UUID id, String street, String streetNumber, String city, String country, String zipCode) {

}
