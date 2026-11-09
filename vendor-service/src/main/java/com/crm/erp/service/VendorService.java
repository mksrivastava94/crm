package com.crm.erp.service;


import com.crm.erp.exception.domain.VendorExistException;
import com.crm.erp.exception.domain.VendorNotFoundException;
import com.crm.erp.model.entity.Vendor;
import com.crm.erp.model.response.KeyValueResponse;
import com.crm.erp.model.request.VendorDateRequest;
import com.crm.erp.model.request.VendorRequest;
import com.crm.erp.model.response.VendorPageResponse;
import com.crm.erp.model.response.VendorResponse;
import com.crm.erp.repository.VendorRepository;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.crm.erp.constant.VendorConstant.VENDOR_NOT_FOUND;
import static com.crm.erp.util.ConversionUtil.*;

@Service
@Log4j2
public class VendorService {

    private static final Logger LOGGER = LoggerFactory.getLogger(VendorService.class);

    private final VendorRepository vendorRepository;

    @Autowired
    public VendorService(VendorRepository vendorRepository) {
        this.vendorRepository = vendorRepository;
    }

    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<VendorResponse> createVendor(VendorRequest request) throws VendorExistException {
        LOGGER.info("Method createVendor() started");
        isEmailOrPhoneExist(request.getEmailId(),request.getPrimaryContactNo());
        Vendor vendor = convertVendorRequestToVendor(request);
        Vendor newVendor = vendorRepository.save(vendor);
        VendorResponse response = convertVendorToVendorResponse(newVendor);
        LOGGER.info("Method createVendor() ended");
        return new ResponseEntity<>(response, HttpStatus.CREATED);

    }

    public ResponseEntity<VendorPageResponse> getAllVendor(final String orgId, final int page, final int pageSize) {
        LOGGER.info("Method getAllVendor() started");
        Pageable pageable = PageRequest.of(page,pageSize);
        Page<Vendor> pages = vendorRepository.findAllByOrgId(orgId,pageable);
        VendorPageResponse vendorPageResponse = getEmployeePageData(pages);
        LOGGER.info("Method getAllVendor() ended");
        return new ResponseEntity<>(vendorPageResponse,HttpStatus.OK);
    }

    private VendorPageResponse getEmployeePageData(Page<Vendor> pages) {
        VendorPageResponse vendorPageResponse = new VendorPageResponse();
        vendorPageResponse.setVendorList(convertVendorListToVendorResponseList(pages.getContent()));
        vendorPageResponse.setTotalPage(pages.getTotalPages());
        return vendorPageResponse;
    }

    public ResponseEntity<VendorResponse> getVendorById(String id) throws VendorNotFoundException {
        LOGGER.info("Method getVendorById() started");
        VendorResponse response = convertVendorToVendorResponse(findByVendorId(id));
        LOGGER.info("Method getVendorById() ended");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<VendorResponse> updateVendor(String id, VendorRequest request) throws VendorNotFoundException, ConstraintViolationException {
        LOGGER.info("Method updateVendor() started");

        if(!id.equals(request.getId()))
            throw new VendorNotFoundException("Invalid Vendor Id : "+id);
        
        findByVendorId(id);
        Vendor vendor = convertVendorRequestToVendor(request);
        vendor.setId(id);
        Vendor updatedVendor = vendorRepository.save(vendor);
        VendorResponse vendorDtoUpdate = convertVendorToVendorResponse(updatedVendor);
        LOGGER.info("Method updateVendor() ended");
        return new ResponseEntity<>(vendorDtoUpdate, HttpStatus.OK);
    }

    public void deleteVendorById(String id) throws VendorNotFoundException {
        LOGGER.info("Method deleteVendorById() started");
        findByVendorId(id);
        vendorRepository.deleteById(id);
        LOGGER.info("Method deleteVendorById() ended");
    }

    private void isEmailOrPhoneExist(String emailId, String primaryContactNo) throws VendorExistException {
         Optional<Vendor> vendor = vendorRepository.findFirstByEmailIdOrPrimaryContactNo(emailId,primaryContactNo);

         if(vendor.isPresent()) {
             LOGGER.error("Phone number or email Id is already register");
             throw new VendorExistException("Phone number or email Id is already register");
         }
    }


    private Vendor findByVendorId(String id) throws VendorNotFoundException {
        return vendorRepository.findById(id)
                .orElseThrow(()->new VendorNotFoundException(VENDOR_NOT_FOUND));
    }

    public ResponseEntity<List<VendorResponse>> getAllVendorByMonth(String orgId, VendorDateRequest vendorDateDto) {
        LOGGER.info("Method getAllVendorByMonth() is started");

        List<Vendor> vendorList = vendorRepository.findAllByOrgId(orgId).stream()
                .filter(vendor -> vendor.getCreatedDateTime().getMonthValue() == vendorDateDto.getMonth()
                        && vendor.getCreatedDateTime().getYear() == vendorDateDto.getYear())
                .collect(Collectors.toList());

        List<VendorResponse> response = convertVendorListToVendorResponseList(vendorList);

        LOGGER.info("Method getAllVendorByMonth() is ended");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    public ResponseEntity<KeyValueResponse> getAllVendorKeyValue(String orgId) {
        log.info("Method getAllVendorKeyValue() is started");

        List<KeyValueResponse> vendorKeyValueList = vendorRepository.findAllByOrgId(orgId)
                .stream()
                .map(vendor->new KeyValueResponse(vendor.getVendorName(),vendor.getId()))
                .collect(Collectors.toList());

        log.info("Method getAllVendorKeyValue() is ended");
        return new ResponseEntity(vendorKeyValueList,HttpStatus.OK);
    }
}
