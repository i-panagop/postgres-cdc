package iop.postgres.cdc.catalog.api.product;

import iop.postgres.cdc.catalog.domain.product.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProduct(@PathVariable UUID id) {
        return productService.getProduct(id)
            .map(prod -> ResponseEntity.ok(ProductDto.from(prod)))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/by/category/{id}")
    public ResponseEntity<List<ProductDto>> getProductsByCategory(@PathVariable UUID id) {
        return productService.getProductsByCategory(id)
            .stream()
            .map(ProductDto::from)
            .collect(Collectors.collectingAndThen(Collectors.toList(), ResponseEntity::ok));
    }

    @PostMapping("/check-availability")
    public ResponseEntity<List<ProductAvailabilityResDto>> checkAvailability(
        @RequestBody Set<ProductAvailabilityReqDto> productAvailabilityReqDtos
    ) {
        return ResponseEntity.ok(
            productService.checkAvailability(
                    productAvailabilityReqDtos.stream()
                        .map(ProductAvailabilityReqDto::to)
                        .collect(Collectors.toSet())
                )
                .stream()
                .map(ProductAvailabilityResDto::from)
                .toList()
        );
    }
}
