package com.crm.erp.service;

import java.util.List;
import java.util.stream.Collectors;

import com.crm.erp.enumeration.BucketName;
import com.crm.erp.exception.domain.InvalidOrgIdOrEmpty;
import com.crm.erp.external.client.upload.UploadFeignClient;
import com.crm.erp.model.request.FileUploadRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.crm.erp.dto.PurchaseDto;
import com.crm.erp.dto.PurchaseDateDto;
import com.crm.erp.model.Purchase;
import com.crm.erp.repository.PurchaseRepository;
import com.crm.erp.util.BasicUtil;

import static com.crm.erp.constant.FileConstant.ORG_NAME;

@Service
@Slf4j
public class PurchaseService {
	
	private final PurchaseRepository purchaseBillRepository;
	private final UploadFeignClient uploadFeignClient;

	@Autowired
	public PurchaseService(PurchaseRepository purchaseBillRepository,UploadFeignClient uploadFeignClient) {
		this.purchaseBillRepository = purchaseBillRepository;
		this.uploadFeignClient = uploadFeignClient;
	}

	@Transactional(rollbackFor = Throwable.class)
	public ResponseEntity<PurchaseDto> createPurchase(PurchaseDto request) throws InvalidOrgIdOrEmpty {
		log.info("Method createProductPurchaseBill() is started");

		String base64 = request.getInvoiceLink();
		request.setInvoiceLink("");
		Purchase purchaseBill = BasicUtil.convertPurchaseDtoToPurchase(request);

		Purchase newPurchase = purchaseBillRepository.save(purchaseBill);

		if (!BasicUtil.notEmptyNotNull(base64))
			throw new InvalidOrgIdOrEmpty("Please select product Image");

		String path = ORG_NAME.concat("/").concat(newPurchase.getOrgId()).concat("/").concat(BucketName.PURCHASEBILL.getBucketName());
		FileUploadRequest uploadRequest = FileUploadRequest.builder()
				.id(newPurchase.getId())
				.path(path)
				.data(base64).build();
		path = uploadFeignClient.uploadBase64(uploadRequest).getBody();
		newPurchase.setInvoiceLink(path);

		newPurchase = purchaseBillRepository.save(newPurchase);

		PurchaseDto newPurchaseDto = BasicUtil.convertPurchaseToPurchaseDto(newPurchase);
		log.info("Method createProductPurchaseBill() is Ended");
		return new ResponseEntity<>(newPurchaseDto, HttpStatus.CREATED);
	}

	public ResponseEntity<List<PurchaseDto>> getAllPurchase(String orgId) {
		log.info("Method getAllPurchaseProduct() is started");
		List<Purchase> purchaseList = purchaseBillRepository.findAllByOrgId(orgId);
		log.info("Method getAllPurchaseProduct() is ended");
		return new ResponseEntity<>(BasicUtil.convertProfileListToProfileDto(purchaseList), HttpStatus.OK);
	}

	public ResponseEntity<List<PurchaseDto>> getAllPurchaseByMonth(String orgId,
																		  PurchaseDateDto dateRangeDto) {
		log.info("Method getAllPurchaseProductByMonth() is started");
		List<Purchase> productPurchaseList = purchaseBillRepository.findAllByOrgId(orgId).stream()
				.filter(product -> product.getCreatedDateTime().getMonthValue() == dateRangeDto.getMonth()
						&& product.getCreatedDateTime().getYear() == dateRangeDto.getYear())
				.collect(Collectors.toList());
		log.info("PRODUCT PURCHASE LIST {}", productPurchaseList);
		return new ResponseEntity<>(BasicUtil.convertProfileListToProfileDto(productPurchaseList), HttpStatus.OK);
	}
}
