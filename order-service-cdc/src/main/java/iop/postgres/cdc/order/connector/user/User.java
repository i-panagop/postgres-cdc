package iop.postgres.cdc.order.connector.user;

import java.util.UUID;

public record User(UUID userId, String name, String email) {

}
