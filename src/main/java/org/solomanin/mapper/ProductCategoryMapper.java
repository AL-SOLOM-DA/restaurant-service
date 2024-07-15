package org.solomanin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.solomanin.dto.ProductCategoryDto;
import org.solomanin.dto.ProductGetDto;
import org.solomanin.entity.Product;
import org.solomanin.entity.ProductCategory;

@Mapper
public interface ProductCategoryMapper {
    ProductCategoryMapper INSTANCE = Mappers.getMapper(ProductCategoryMapper.class);

    @Mapping(source = "id", target = "productCatId")
    @Mapping(source = "name", target = "productCatName")
    @Mapping(source = "categoryType", target = "productCategoryType")
    ProductCategoryDto productCategoryToProductCategoryDto(ProductCategory productCategory);
}
