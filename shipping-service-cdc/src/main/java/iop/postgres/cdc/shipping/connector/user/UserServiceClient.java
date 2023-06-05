package iop.postgres.cdc.shipping.connector.user;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "user-service", url = "http://localhost:8081")
public interface UserServiceClient {

    @GetMapping("/get/{id}")
    @CircuitBreaker(name = "user-service-circuit-breaker")
    User getById(@PathVariable("id") UUID id);
}