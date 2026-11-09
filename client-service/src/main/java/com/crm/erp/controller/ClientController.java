package com.crm.erp.controller;

import com.crm.erp.external.ProfileFeignClient;
import com.crm.erp.model.request.ClientDateRequest;
import com.crm.erp.model.request.ClientRequest;
import com.crm.erp.model.request.ClientUpdateRequest;
import com.crm.erp.model.response.ClientResponse;
import com.crm.erp.model.response.ClientPageResponse;
import com.crm.erp.exception.ExceptionHandling;
import com.crm.erp.exception.domain.*;
import com.crm.erp.model.response.KeyValueResponse;
import com.crm.erp.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
public class ClientController extends ExceptionHandling {

    private final ClientService clientService;
    private final ProfileFeignClient profileFeignClient;

    @Autowired
    public ClientController(ClientService clientService, ProfileFeignClient profileFeignClient) {
        this.clientService = clientService;
        this.profileFeignClient = profileFeignClient;
    }

    @PostMapping("/add")
    public ResponseEntity<ClientResponse> createClient(@RequestBody ClientRequest request) throws ClientExistException {
            return clientService.createClient(request);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ClientResponse> updateClient(@PathVariable String id, @RequestBody ClientUpdateRequest request) throws ClientNotFoundException {
        return clientService.updateClient(id, request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClientResponse> getClientById(@PathVariable String id) throws ClientNotFoundException {
        return clientService.findById(id);
    }

    @GetMapping("/list/{orgId}/{page}/{pageSize}")
    public ResponseEntity<ClientPageResponse> getAllClient(@PathVariable String orgId, @PathVariable int page, @PathVariable int pageSize){
        return clientService.getAllClient(orgId, page, pageSize);
    }
    @GetMapping("/date/{orgId}")
    public ResponseEntity<List<ClientResponse>> getAllClientByMonth(@PathVariable String orgId, @RequestBody ClientDateRequest profileDateDto) {
        return clientService.getAllClientByMonth(orgId,profileDateDto);
    }

    @DeleteMapping("/delete/{id}")
    public void clientDeleteById(@PathVariable String id) throws ClientNotFoundException {
        clientService.clientDeleteById(id);
    }

    @GetMapping("/list/key-value/{orgId}")
    public ResponseEntity<KeyValueResponse> getAllClintKeyValue(@PathVariable String orgId){
        return clientService.getAllClientKeyValue(orgId);
    }

}
