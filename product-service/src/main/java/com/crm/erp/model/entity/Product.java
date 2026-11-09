package com.crm.erp.model.entity;

import com.crm.erp.constant.ProductConstant;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Document(collection="product")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    private String id;

    @NotEmpty(message = ProductConstant.PRODUCT_NAME_NOT_EMPTY)
    private String productName;
    private String materialUsedId;

    @NotEmpty(message = ProductConstant.CATEGORY_NAME_NOT_EMPTY)
    private String category;
    private String dimension;
    private String productImageLink;
    private String polish;

    @NotNull(message = ProductConstant.ACTUAL_PRICE_NOT_EMPTY)
    @DecimalMin(value = "0.0")
    private double actualPrice;
    @NotNull(message = ProductConstant.ACTUAL_PRICE_NOT_EMPTY)
    @DecimalMin(value = "0.0")
    private double price;

    private String fabric;
    private String description;
    private boolean isStock;
    private int quantity;

    @NotNull(message = "OrgId can't be null")
    private String orgId;

    private LocalDate updateDateTime = LocalDate.now();
    private LocalDate createdDateTime = LocalDate.now();
}
