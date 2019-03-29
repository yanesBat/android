package com.karman.ebcard.service.mapper;

import com.karman.ebcard.domain.*;
import com.karman.ebcard.service.dto.CategoryDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Category and its DTO CategoryDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface CategoryMapper extends EntityMapper<CategoryDTO, Category> {

    @Mapping(source = "category.id", target = "categoryId")
    CategoryDTO toDto(Category category);

    @Mapping(source = "categoryId", target = "category")
    @Mapping(target = "subCategories", ignore = true)
    @Mapping(target = "EBCards", ignore = true)
    Category toEntity(CategoryDTO categoryDTO);

    default Category fromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }
}
