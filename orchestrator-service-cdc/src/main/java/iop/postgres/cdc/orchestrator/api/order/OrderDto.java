package iop.postgres.cdc.orchestrator.api.order;

import java.util.UUID;

public record OrderDto(UUID orderId, UUID userId, Double amount) {
}

