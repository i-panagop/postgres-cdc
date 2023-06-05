package iop.postgres.cdc.order.api.order;

import java.util.List;

public record CreateOrderDto(String userEmail, double amount, List<CommerceItemDto> orderItems) {

}
