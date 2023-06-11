package iop.postgres.cdc.catalog.domain.category;

import iop.postgres.cdc.catalog.infrastructure.category.Category;
import iop.postgres.cdc.catalog.infrastructure.category.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Optional<CategoryModel> getCategory(UUID id) {
        return categoryRepository.findById(id).map(Category::toModel);
    }

    public Optional<CategoryModel> getDetailedCategory(UUID id) {
        return categoryRepository.findById(id)
            .map(Category::toModel)
            .map(this::populateChildren);
    }

    private CategoryModel populateChildren(CategoryModel categoryModel) {
        if (Objects.isNull(categoryModel)) {
            return null;
        }
        categoryModel.setChildren(
            categoryRepository.findByParentCategoryIdOrderByCategoryPosition(categoryModel.getId())
                .stream()
                .map(Category::toModel)
                .collect(Collectors.toList())
        );
        return categoryModel;
    }

    public Optional<CategoryModel> getTree(String root) {
        CategoryModel rootCat =  categoryRepository.findByName(root)
            .map(Category::toModel)
            .orElse(null);
        populateChildren(rootCat);
        if(Objects.isNull(rootCat)){
            return Optional.empty();
        }
        populateTree(rootCat);
        return Optional.of(rootCat);
    }

    private void populateTree(CategoryModel categoryModel) {
        for (CategoryModel cat : categoryModel.getChildren() ) {
            populateChildren(cat);
            populateTree(cat);
        }
    }
}
