package com.technu.erp.util;

import com.technu.erp.model.entity.Client;
import com.technu.erp.model.request.ClientRequest;
import com.technu.erp.model.request.ClientUpdateRequest;
import com.technu.erp.model.response.ClientResponse;
import org.modelmapper.ModelMapper;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

public class ParseUtil {

    public static Client prepareClientData(ClientRequest request) {
        return Client.builder()
                .clientName(request.getClientName())
                .primaryContactNo(request.getPrimaryContactNo())
                .secondaryContactNo(request.getSecondaryContactNo())
                .emailId(request.getEmailId())
                .address(request.getAddress())
                .pinCode(request.getPinCode())
                .country(request.getCountry())
                .state(request.getState())
                .createdDate(LocalDate.now())
                .month(LocalDate.now().getMonthValue())
                .year(LocalDate.now().getYear())
                .orgId(request.getOrgId())
                .build();
    }

    public static ClientResponse clientToClientResponse(Client client) {
        return new ModelMapper().map(client,ClientResponse.class);
    }

    public static Client clientUpdateRequestToClient(ClientUpdateRequest request) {
        return new ModelMapper().map(request,Client.class);
    }

    public static List<ClientResponse> clientListToClientResponse(List<Client> list) {
        return list.stream()
                .map(client->new ModelMapper().map(client,ClientResponse.class))
                .collect(Collectors.toList());
    }

}
