package org.solomanin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.solomanin.entity.Product;
import org.solomanin.entity.ProductCategory;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductGetDto{
//    @JsonProperty(value = "prod_id")
    private Long prodId;

//    @JsonProperty("prod_name")
    private String prodName;

//    @JsonProperty("prod_price")
    private BigDecimal prodPrice;

//    @JsonProperty("prod_quantity")
    private int prodQuantity;

//    @JsonProperty("prod_available")
    private boolean prodAvailable;

//    @JsonProperty("productCategory")
//    private List<ProductCategory> productCategories;
}
