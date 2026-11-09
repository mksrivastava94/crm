package com.technu.erp.model.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class ProductPageResponse {

    private List<ProductResponse> data;
    private int totalPage;

}
