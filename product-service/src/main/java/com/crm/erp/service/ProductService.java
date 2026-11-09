package com.crm.erp.service;

import com.crm.erp.external.UploadFeignClient;
import com.crm.erp.model.request.ProductRequest;
import com.crm.erp.model.response.ProductPageResponse;
import com.crm.erp.exception.domain.ProductExistException;
import com.crm.erp.exception.domain.ProductImageNotFoundException;
import com.crm.erp.exception.domain.ProductNotFoundException;
import com.crm.erp.model.entity.Product;
import com.crm.erp.model.request.FileUploadRequest;
import com.crm.erp.model.response.ProductResponse;
import com.crm.erp.repository.ProductRepository;
import com.crm.erp.util.BasicUtil;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolationException;
import java.util.Optional;

import static com.crm.erp.constant.FileConstant.ORG_NAME;
import static com.crm.erp.constant.ProductConstant.PRODUCT_NOT_FOUND;
import static com.crm.erp.enumeration.BucketName.PRODUCT;
import static com.crm.erp.util.BasicUtil.*;

@Service
@Log4j2
public class ProductService {

    private final ProductRepository productRepository;
    private final UploadFeignClient uploadFeignClient;

    @Autowired
    public ProductService(ProductRepository productRepository, UploadFeignClient uploadFeignClient) {
        this.productRepository = productRepository;
        this.uploadFeignClient = uploadFeignClient;
    }

    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<ProductResponse> createProduct(ProductRequest request) throws ProductExistException, ProductImageNotFoundException {
        log.info("Method createProduct() started");
        isProductExist(request.getProductName());
        String base64 = request.getProductImageLink();
        request.setProductImageLink("");
        Product product = convertProductRequestToProduct(request);
        Product newProduct = productRepository.save(product);

        if (!BasicUtil.notEmptyNotNull(base64))
            throw new ProductImageNotFoundException("Please select product Image");

        String path = ORG_NAME.concat("/").concat(newProduct.getOrgId()).concat("/").concat(PRODUCT.getBucketName());

        FileUploadRequest uploadRequest = FileUploadRequest.builder()
                .id(newProduct.getId())
                .path(path)
                .data(base64)
                .build();

        path = uploadFeignClient.uploadBase64(uploadRequest).getBody();
        newProduct.setProductImageLink(path);
        newProduct = productRepository.save(newProduct);
        log.info("Method createProduct() ended");
        return new ResponseEntity<>(convertProductToProductResponse(newProduct), HttpStatus.CREATED);

    }

    public ResponseEntity<ProductPageResponse> getAllProduct(final String orgId, final String category, final int page, final int pageSize) {
        log.info("Method getAllProduct() started");
        Pageable pageable = PageRequest.of(page,pageSize);

        Page<Product> pages = null;
        if( category == null || category.equalsIgnoreCase("All"))
            pages = productRepository.findAllByOrgId(orgId,pageable);
        else
            pages = productRepository.findAllByOrgIdAndCategory(orgId,category,pageable);

        ProductPageResponse productPageResponse = getProductPageData(pages);
        log.info("Method getAllProduct() ended");
        return ResponseEntity.ok(productPageResponse);
    }

    private ProductPageResponse getProductPageData(Page<Product> pages) {
        ProductPageResponse productPageResponse = new ProductPageResponse();
        productPageResponse.setData(convertProductListToProductDtoList(pages.getContent()));
        productPageResponse.setTotalPage(pages.getTotalPages());
        return productPageResponse;
    }

    public ResponseEntity<ProductResponse> getProductById(String id) throws ProductNotFoundException {
        log.info("Method getProductById() started");
        Product eProduct = findProductById(id);
        ProductResponse productResponse = convertProductToProductResponse(eProduct);
        log.info("Method getProductById() ended");
        return ResponseEntity.ok(productResponse);
    }

    public ResponseEntity<ProductResponse> updateProduct(String id, ProductRequest request) throws ProductNotFoundException, ConstraintViolationException {
        log.info("Method updateProduct() started");
        findProductById(id);
        log.info("Product is Updated : ",id);
        Product productUpdate = productRepository.save(convertProductRequestToProduct(request));
        log.info("Method updateProduct() ended");
        return new ResponseEntity<>(convertProductToProductResponse(productUpdate), HttpStatus.OK);
    }

    public void deleteProductById(String id) throws ProductNotFoundException {
        log.info("Method deleteProductById() started");
        findProductById(id);
        productRepository.deleteById(id);
        log.info("Method deleteProductById() ended");
    }

    public Product findProductById(String id) throws ProductNotFoundException{
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(PRODUCT_NOT_FOUND));
    }

    private void isProductExist(String productName) throws ProductExistException {
        Optional<Product> product = productRepository.findByProductName(productName);
        if (product.isPresent())
            throw new ProductExistException("Product name already registered.");

    }

}
