package org.solomanin.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.solomanin.enums.CategoryType;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategory {
    private Long id;
    private String name;
    private CategoryType categoryType;
    private List<Product> products;
}
