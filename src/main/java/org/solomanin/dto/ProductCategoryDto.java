package org.solomanin.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.solomanin.entity.Product;
import org.solomanin.enums.CategoryType;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductCategoryDto {
    private Long productCatId;
    private String productCatName;
    private CategoryType productCategoryType;
    private List<Product> products;
}
