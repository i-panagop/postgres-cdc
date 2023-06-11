package iop.postgres.cdc.order.connector.catalog;

import java.util.UUID;

public record ProductAvailabilityReqDto(UUID id, int quantity) {
}
