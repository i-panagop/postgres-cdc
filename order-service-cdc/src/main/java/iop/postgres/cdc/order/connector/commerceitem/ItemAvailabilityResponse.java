package iop.postgres.cdc.order.connector.commerceitem;

import java.util.List;
import java.util.UUID;

public record ItemAvailabilityResponse(
    boolean allAvailable,
    List<UUID> unavailableProducts
) {

}
