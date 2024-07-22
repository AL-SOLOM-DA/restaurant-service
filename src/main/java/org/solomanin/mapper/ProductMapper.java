package org.solomanin.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.solomanin.dto.ProductDto;
import org.solomanin.entity.Product;

@Mapper
public interface ProductMapper {
    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    @Mapping(source = "id", target = "prodId")
    @Mapping(source = "name", target = "prodName")
    @Mapping(source = "price", target = "prodPrice")
    @Mapping(source = "quantity", target = "prodQuantity")
    @Mapping(source = "available", target = "prodAvailable")
    ProductDto productToProductDTO(Product product);
}
