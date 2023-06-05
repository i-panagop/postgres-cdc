package iop.postgres.cdc.order.connector.commerceitem;

import iop.postgres.cdc.order.api.order.CommerceItemDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "order-item-service", url = "http://localhost:8084")
public interface CommerceItemClient {

    @PostMapping("/check")
    ItemAvailabilityResponse checkAvailability(@RequestBody List<CommerceItemDto> orderItems);


}