package com.crm.erp.service;

import com.crm.erp.exception.domain.ClientNotFoundException;
import com.crm.erp.exception.domain.OrderNotFoundException;
import com.crm.erp.exception.domain.ProductNotFoundException;
import com.crm.erp.external.ClientFeignClient;
import com.crm.erp.external.ProductFeignClient;
import com.crm.erp.model.entity.Order;
import com.crm.erp.model.request.OrderDateRequest;
import com.crm.erp.model.request.OrderRequest;
import com.crm.erp.model.response.ClientResponse;
import com.crm.erp.model.response.OrderPageResponse;
import com.crm.erp.model.response.OrderResponse;
import com.crm.erp.model.response.ProductResponse;
import com.crm.erp.repository.OrderRepository;
import com.crm.erp.util.ConversionUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.crm.erp.util.ConversionUtil.*;

@Log4j2
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ClientFeignClient clientService;
    private final ProductFeignClient productService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ClientFeignClient clientService, ProductFeignClient productService) {
        this.orderRepository = orderRepository;
        this.clientService = clientService;
        this.productService = productService;
    }


    @Transactional(rollbackFor = Throwable.class)
    public ResponseEntity<OrderResponse> addOrder(OrderRequest request) throws ClientNotFoundException, ProductNotFoundException {
        log.info("Method createOrder() is started");

        ClientResponse client = clientService.getClientById(request.getOrderBy()).getBody();
        ProductResponse product = productService.getProductById(request.getProductId()).getBody();

        if(client == null)
            throw new ClientNotFoundException("Invalid Client id "+request.getOrderBy());
        else if(product == null)
            throw new ProductNotFoundException("Invalid Product id "+request.getProductId());

        Order order = convertOrderRequestToOrder(request, product.getProductName(), client.getClientName());
        order = orderRepository.save(order);
        log.info("Order is created "+order.getId());
        OrderResponse response = convertOrderToOrderResponse(order);
        log.info("Method createOrder() is ended");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<OrderResponse> getOrderById(String id) throws OrderNotFoundException {
        log.info("Method getOrderById() is started");
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Invalid order id : " + id));
        log.info("Method getOrderById() is ended");
        return new ResponseEntity<>(convertOrderToOrderResponse(order), HttpStatus.OK);
    }

    public ResponseEntity<OrderPageResponse> getAllOrder(final String orgId, final int page, final int pageSize) {
        log.info("Method getAllOrder() is started");
        Pageable pageable = PageRequest.of(page, pageSize);
        Page<Order> pages = orderRepository.findAllByOrgId(orgId, pageable);
        OrderPageResponse data = getOrderPageData(pages);
        log.info("Method getAllOrder() is ended");
        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    public ResponseEntity<List<OrderResponse>> getAllOrderByMonth(String orgId, OrderDateRequest dateRangeDto) {
        log.info("Method getAllOrderByMonth() is started");

        if(dateRangeDto.getMonth() == 0 || dateRangeDto.getYear() == 0)
            throw new IllegalStateException("Please mention date and month ");

        List<OrderResponse> response = orderRepository.findAllByOrgId(orgId)
                .stream()
                .filter(order -> order.getCreatedDateTime().getMonthValue() == dateRangeDto.getMonth()
                        && order.getCreatedDateTime().getYear() == dateRangeDto.getYear())
                .map(data -> convertOrderToOrderResponse(data))
                .collect(Collectors.toList());

        log.info("Method getAllOrderByMonth() is ended");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    public ResponseEntity<List<OrderResponse>> getAllOrderByClientId(String orgId, String orderBy) {
        log.info("Method getAllOrderByClientId() is started");

        List<OrderResponse> response = orderRepository.findAllByOrgIdAndOrderBy(orgId, orderBy)
                .stream()
                .map(data -> convertOrderToOrderResponse(data))
                .collect(Collectors.toList());

        log.info("Method getAllOrderByClientId() is ended");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


}
