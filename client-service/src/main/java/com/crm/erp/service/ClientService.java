package com.crm.erp.service;

import com.crm.erp.exception.domain.ClientExistException;
import com.crm.erp.exception.domain.ClientNotFoundException;
import com.crm.erp.model.entity.Client;
import com.crm.erp.model.request.ClientDateRequest;
import com.crm.erp.model.request.ClientRequest;
import com.crm.erp.model.request.ClientUpdateRequest;
import com.crm.erp.model.response.ClientPageResponse;
import com.crm.erp.model.response.ClientResponse;
import com.crm.erp.model.response.KeyValueResponse;
import com.crm.erp.repository.ClientRepository;
import com.crm.erp.util.ParseUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.crm.erp.constant.ClientConstant.ALREADY_REGISTER_EMAIL_OR_PHONE;
import static com.crm.erp.constant.ClientConstant.CLIENT_NOT_FOUND;


@Service
@AllArgsConstructor
@Slf4j
public class ClientService {

    private final ClientRepository clientRepository;

    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<ClientResponse> createClient(ClientRequest request) throws ClientExistException {
        log.info("Method createClient() started");
        isEmailOrPhoneExist(request.getEmailId(), request.getPrimaryContactNo());
        Client client = ParseUtil.prepareClientData(request);
        Client newClient = clientRepository.save(client);
        ClientResponse response = ParseUtil.clientToClientResponse(newClient);
        log.info("Method createClient() ended");
        return new ResponseEntity<>(response,HttpStatus.CREATED);
    }

    public ResponseEntity<ClientPageResponse> getAllClient(final String orgId, final int page, final int pageSize) {
        log.info("Method getAllClient() started");
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Client> pages = clientRepository.findAllByOrgId(orgId,pageable);
        ClientPageResponse clientPageResponse = getClientPageData(pages);
        log.info("Method getAllClient() ended");
        return new ResponseEntity<>(clientPageResponse,HttpStatus.OK);
    }

    private ClientPageResponse getClientPageData(Page<Client> pages) {
        ClientPageResponse clientPageResponse = new ClientPageResponse();
        clientPageResponse.setClientList(ParseUtil.clientListToClientResponse(pages.getContent()));
        clientPageResponse.setTotalPage(pages.getTotalPages());
        return clientPageResponse;
    }

    public ResponseEntity<ClientResponse> findById(String id) throws ClientNotFoundException {
        log.info("Method findById() started");
        Client eClient = findClientById(id);
        log.info("Method findById() ended");
        return  new ResponseEntity<>(ParseUtil.clientToClientResponse(eClient),HttpStatus.OK);
    }

    public ResponseEntity<ClientResponse> updateClient(String id, ClientUpdateRequest request) throws ClientNotFoundException {
        log.info("Method updateClient() started");
        findClientById(id);
        Client eClient = ParseUtil.clientUpdateRequestToClient(request);
        eClient = clientRepository.save(eClient);
        log.info("Method updateClient() ended");
        return new ResponseEntity<>(ParseUtil.clientToClientResponse(eClient), HttpStatus.ACCEPTED);
    }

    public void clientDeleteById(String id) throws ClientNotFoundException {
        log.info("Method clientDeleteById() started");
        findClientById(id);
        clientRepository.deleteById(id);
        log.info("Method clientDeleteById() started");
    }

    private void isEmailOrPhoneExist(String emailId, String primaryContactNo) throws ClientExistException {
        Optional<Client> eClient = clientRepository.findFirstByEmailIdOrPrimaryContactNo(emailId,primaryContactNo);
        if(eClient.isPresent())
            throw new ClientExistException(ALREADY_REGISTER_EMAIL_OR_PHONE);
    }

    private Client findClientById(String id) throws ClientNotFoundException {
        return clientRepository.findById(id)
                .orElseThrow(()-> new ClientNotFoundException(CLIENT_NOT_FOUND));
    }

    public ResponseEntity<List<ClientResponse>> getAllClientByMonth(String orgId, ClientDateRequest dateRangeDto) {
        log.info("Method getAllClientByMonth() is started");
        List<Client> clientList = clientRepository.findAllByOrgId(orgId).stream()
                .filter(client -> client.getCreatedDate().getMonthValue() == dateRangeDto.getMonth()
                        && client.getCreatedDate().getYear() == dateRangeDto.getYear())
                .collect(Collectors.toList());
        log.info("Method getAllClientByMonth() is ended");

        return new ResponseEntity<>(ParseUtil.clientListToClientResponse(clientList),HttpStatus.OK);
    }

    public ResponseEntity<KeyValueResponse> getAllClientKeyValue(String orgId) {
        log.info("Method getAllClientKeyValue() is started");

        List<KeyValueResponse> keyValueList = clientRepository.findAllByOrgId(orgId)
                .stream()
                .map(client->new KeyValueResponse(client.getClientName(),client.getId()))
                .collect(Collectors.toList());

        log.info("Method getAllClientKeyValue() is ended");
        return new ResponseEntity(keyValueList,HttpStatus.OK);
    }
}
