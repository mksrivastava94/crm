package com.technu.erp.model.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class OrderPageResponse {

    private List<OrderResponse> data;
    private int totalPage;

}
