package iop.postgres.cdc.catalog.api.category;

import iop.postgres.cdc.catalog.domain.category.CategoryModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public record CategoryDto(
    UUID id,
    String name,
    int categoryPosition,
    UUID parentCategoryId,
    List<CategoryDto> children) {

    public static CategoryDto from(CategoryModel categoryModel) {
        return new CategoryDto(
            categoryModel.getId(),
            categoryModel.getName(),
            categoryModel.getCategoryPosition(),
            categoryModel.getParentCategoryId(),
            Optional.ofNullable(categoryModel.getChildren())
                .orElse(new ArrayList<>())
                .stream()
                .map(CategoryDto::from)
                .collect(Collectors.toList())
        );
    }

}
