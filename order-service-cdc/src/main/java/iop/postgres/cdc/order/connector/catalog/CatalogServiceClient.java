package iop.postgres.cdc.order.connector.catalog;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(name = "catalog-service", url = "http://localhost:8088")
public interface CatalogServiceClient {

    @PostMapping("/product/check-availability")
    List<ProductAvailabilityResDto> checkAvailability(
        @RequestBody List<ProductAvailabilityReqDto> productAvailabilityReqDtos
    );
}