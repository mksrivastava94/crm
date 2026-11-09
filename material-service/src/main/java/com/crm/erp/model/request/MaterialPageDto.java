package com.crm.erp.model.request;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
@ToString
public class MaterialPageDto {

    private List<MaterialDto> data;
    private int totalPage;

}
