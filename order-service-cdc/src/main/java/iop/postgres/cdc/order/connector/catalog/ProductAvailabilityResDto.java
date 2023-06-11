package iop.postgres.cdc.order.connector.catalog;

import java.util.UUID;

public record ProductAvailabilityResDto(UUID id, boolean available, double price) {
}
