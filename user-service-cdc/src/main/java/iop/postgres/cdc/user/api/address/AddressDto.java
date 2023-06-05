package iop.postgres.cdc.user.api.address;

import iop.postgres.cdc.user.infrastructure.address.Address;

import java.util.UUID;

public record AddressDto(UUID id, String street, String streetNumber, String city, String country, String zipCode) {

    public static AddressDto of(Address address) {
        return new AddressDto(
            address.getId(),
            address.getStreet(),
            address.getStreetNumber(),
            address.getCity(),
            address.getCountry(),
            address.getZipCode()
        );
    }
}
