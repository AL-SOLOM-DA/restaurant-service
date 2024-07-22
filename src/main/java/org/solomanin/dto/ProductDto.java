package org.solomanin.dto;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {
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
}
