package com.crm.erp.model.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

@Data
public class ClientPageResponse {

    private List<ClientResponse> clientList;
    private int totalPage;

}
