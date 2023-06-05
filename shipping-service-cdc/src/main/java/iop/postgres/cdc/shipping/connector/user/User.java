package iop.postgres.cdc.shipping.connector.user;

import java.util.UUID;

public record User(UUID userId, String name, String email, Address address) {

}
