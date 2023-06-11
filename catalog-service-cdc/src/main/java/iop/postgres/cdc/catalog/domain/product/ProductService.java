package iop.postgres.cdc.catalog.domain.product;

import iop.postgres.cdc.catalog.infrastructure.product.Product;
import iop.postgres.cdc.catalog.infrastructure.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Optional<ProductModel> getProduct(UUID id) {
        return productRepository.findById(id).map(Product::toModel);
    }

    public Set<ProductAvailabilityResModel> checkAvailability(
        Set<ProductAvailabilityReqModel> productAvailabilityReqModels
    ) {
        if (CollectionUtils.isEmpty(productAvailabilityReqModels)) {
            return Set.of();
        }
        Set<ProductModel> productModels = productRepository.findByIdIn(
                productAvailabilityReqModels.stream()
                    .map(ProductAvailabilityReqModel::getId)
                    .collect(Collectors.toSet())
            )
            .stream()
            .map(Product::toModel)
            .collect(Collectors.toSet());
        Set<ProductAvailabilityResModel> resModels = new HashSet<>();
        for (ProductAvailabilityReqModel req : productAvailabilityReqModels) {
            ProductAvailabilityResModel res = new ProductAvailabilityResModel();
            res.setId(req.getId());
            productModels.stream()
                .filter(p -> p.getId().equals(req.getId()))
                .findFirst()
                .ifPresent(p -> {
                    res.setAvailable(p.getStock() >= req.getQuantity());
                    res.setPrice(p.getPrice());
                });
            resModels.add(res);
        }
        return resModels;
    }

    public List<ProductModel> getProductsByCategory(UUID id) {
        return productRepository.findByCategoryId(id)
            .stream()
            .map(Product::toModel)
            .collect(Collectors.toList());
    }
}
