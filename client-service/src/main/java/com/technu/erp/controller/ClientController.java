package com.technu.erp.controller;

import com.technu.erp.external.ProfileFeignClient;
import com.technu.erp.model.request.ClientDateRequest;
import com.technu.erp.model.request.ClientRequest;
import com.technu.erp.model.request.ClientUpdateRequest;
import com.technu.erp.model.response.ClientResponse;
import com.technu.erp.model.response.ClientPageResponse;
import com.technu.erp.exception.ExceptionHandling;
import com.technu.erp.exception.domain.*;
import com.technu.erp.model.response.KeyValueResponse;
import com.technu.erp.service.ClientService;
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
