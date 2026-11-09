package com.crm.erp.model.entity;

import com.crm.erp.constant.MaterialConstant;
import com.crm.erp.enumeration.UnitType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;


@Document(collection="material")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Material {

    @Id
    private String id;

    @NotEmpty(message = MaterialConstant.CATEGORY_NAME_NOT_EMPTY)
    private String category;

    @NotEmpty(message = MaterialConstant.MATERIAL_NAME_NOT_EMPTY)
    private String materialName;

    private String materialImageLink;
    private String description;

    @NotNull(message = MaterialConstant.PRICE_PER_UNIT_NOT_EMPTY)
    @DecimalMin(value = "0.0")
    private double pricePerUnit;

    private String dimension;

    @NotEmpty(message = MaterialConstant.UNIT_TYPE_NOT_EMPTY)
    private String unitType;

    @NotNull(message = MaterialConstant.QUANTITY_IN_STOCK_NOT_EMPTY)
    @DecimalMin(value = "0")
    private int quantity;
    private int quantityUsed;

    private String orgId;
    private LocalDate updateDate = LocalDate.now();
    private LocalDate createdDate = LocalDate.now();
}
