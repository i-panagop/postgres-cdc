package iop.postgres.cdc.orchestrator.api.order;

import java.util.List;

public record CreateOrderDto(String userEmail, double amount, List<CommerceItemDto> orderItems) {

}
