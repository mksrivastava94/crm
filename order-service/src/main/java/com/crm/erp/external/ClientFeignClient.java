package com.crm.erp.external;

import com.crm.erp.exception.domain.ClientNotFoundException;
import com.crm.erp.model.response.ClientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "client")
public interface ClientFeignClient {

    @GetMapping("/api/v1/client/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable String id) throws ClientNotFoundException;

}
