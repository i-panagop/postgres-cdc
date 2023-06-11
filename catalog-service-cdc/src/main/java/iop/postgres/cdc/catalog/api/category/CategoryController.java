package iop.postgres.cdc.catalog.api.category;

import iop.postgres.cdc.catalog.domain.category.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDto> getCategory(@PathVariable UUID id) {
        return categoryService.getCategory(id)
            .map(cat -> ResponseEntity.ok(CategoryDto.from(cat)))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/detailed/{id}")
    public ResponseEntity<CategoryDto> getDetailedCategory(@PathVariable UUID id) {
        return categoryService.getDetailedCategory(id)
            .map(cat -> ResponseEntity.ok(CategoryDto.from(cat)))
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/tree")
    public ResponseEntity<CategoryDto> getCategoryTree() {
        return categoryService.getTree("ROOT")
            .map(cat -> ResponseEntity.ok(CategoryDto.from(cat)))
            .orElse(ResponseEntity.notFound().build());
    }

}
