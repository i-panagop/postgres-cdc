package iop.postgres.cdc.orchestrator.api.rest;

import iop.postgres.cdc.orchestrator.api.order.CommerceItemDto;
import iop.postgres.cdc.orchestrator.api.order.CreateOrderDto;
import iop.postgres.cdc.orchestrator.business.RequestHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final RequestHandler requestHandler;

    @PostMapping("/create")
    public ResponseEntity<Boolean> create(@RequestBody CreateOrderDto createOrderDto) {
        return new ResponseEntity<>(
            requestHandler.createOrder(
                createOrderDto.userEmail(),
                createOrderDto.amount(),
                createOrderDto.orderItems().stream()
                    .map(CommerceItemDto::to)
                    .toList()
            ),
            HttpStatus.OK
        );
    }

}
