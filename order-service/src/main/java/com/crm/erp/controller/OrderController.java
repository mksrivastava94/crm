package com.crm.erp.controller;

import com.crm.erp.exception.domain.ClientNotFoundException;
import com.crm.erp.exception.domain.ProductNotFoundException;
import com.crm.erp.model.request.OrderDateRequest;
import com.crm.erp.model.request.OrderRequest;
import com.crm.erp.model.response.OrderPageResponse;
import com.crm.erp.exception.ExceptionHandling;
import com.crm.erp.exception.domain.OrderNotFoundException;
import com.crm.erp.model.response.OrderResponse;
import com.crm.erp.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/order")
public class OrderController extends ExceptionHandling {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/add")
    public ResponseEntity<OrderResponse> addOrder(@RequestBody OrderRequest request) throws ClientNotFoundException, ProductNotFoundException {
        return orderService.addOrder(request);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable String id) throws OrderNotFoundException {
        return orderService.getOrderById(id);
    }

    @GetMapping("/list/{orgId}/{page}/{pageSize}")
    public ResponseEntity<OrderPageResponse> getAllOrder(@PathVariable String orgId, @PathVariable int page, @PathVariable int pageSize) {
        return orderService.getAllOrder(orgId,page,pageSize);
    }

    @GetMapping("/list/{orgId}/{clientId}")
    public ResponseEntity<List<OrderResponse>> getAllOrderByClientId(@PathVariable String orgId, @PathVariable String clientId) {
        return orderService.getAllOrderByClientId(orgId,clientId);
    }

    @GetMapping("/date/{orgId}")
    public ResponseEntity<List<OrderResponse>> getAllOrderByMonth(@PathVariable String orgId,@RequestBody OrderDateRequest request) {
        return orderService.getAllOrderByMonth(orgId, request);
    }
}
