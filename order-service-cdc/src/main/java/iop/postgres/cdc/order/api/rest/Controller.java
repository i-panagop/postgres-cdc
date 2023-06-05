package iop.postgres.cdc.order.api.rest;

import iop.postgres.cdc.order.api.order.OrderDto;
import iop.postgres.cdc.order.business.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class Controller {

    private final OrderService orderService;

    @PostMapping("/write")
    public ResponseEntity<UUID> write(@RequestParam String name) {
        return new ResponseEntity<>(orderService.createOrder(name), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<UUID> create(@RequestBody OrderDto orderDto) {
        return new ResponseEntity<>(orderService.createOrder(orderDto), HttpStatus.OK);
    }

    @PostMapping("/update")
    public ResponseEntity<OrderDto> write(@RequestBody OrderDto orderDto) {
        return new ResponseEntity<>(orderService.updateOrder(orderDto), HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<OrderDto> get(@PathVariable UUID id) {
        return new ResponseEntity<>(orderService.getOrder(id), HttpStatus.OK);
    }

}