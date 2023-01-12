package com.alexey.shop.core.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductsGetDto {

    private List<ProductDto> list;
    private Integer pagesCount;
    private Long recordsTotal;
}
