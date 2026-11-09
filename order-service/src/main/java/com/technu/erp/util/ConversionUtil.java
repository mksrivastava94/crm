package com.technu.erp.util;

import com.technu.erp.model.entity.Order;
import com.technu.erp.model.request.OrderRequest;
import com.technu.erp.model.response.OrderPageResponse;
import com.technu.erp.model.response.OrderResponse;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

public class ConversionUtil {

    private ConversionUtil(){}

    public static Order convertOrderRequestToOrder(OrderRequest request, String productName, String clientName) {

        return Order.builder()
                .orgId(request.getOrgId())
                .paymentMode(request.getPaymentMode())
                .message(request.getMessage())
                .status(request.getStatus())
                .quantity(request.getQuantity())
                .deliveryDate(request.getDeliveryDate())
                .productId(request.getProductId())
                .productName(productName)
                .clientName(clientName)
                .advanceAmount(request.getAdvanceAmount())
                .createdDateTime(request.getCreatedDateTime())
                .updatedDateTime(request.getUpdatedDateTime())
                .orderBy(request.getOrderBy())
                .build();
    }

    public static OrderResponse convertOrderToOrderResponse(Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .orgId(order.getOrgId())
                .paymentMode(order.getPaymentMode())
                .message(order.getMessage())
                .status(order.getStatus())
                .quantity(order.getQuantity())
                .deliveryDate(order.getDeliveryDate())
                .productId(order.getProductId())
                .productName(order.getProductName())
                .advanceAmount(order.getAdvanceAmount())
                .createdDateTime(order.getCreatedDateTime())
                .updatedDateTime(order.getUpdatedDateTime())
                .orderBy(order.getOrderBy())
                .clientName(order.getClientName())
                .build();
    }

    public static OrderPageResponse getOrderPageData(Page<Order> pages) {
        OrderPageResponse orderPageResponse = new OrderPageResponse();

        orderPageResponse.setData(pages
                .getContent()
                .stream()
                .map(data -> convertOrderToOrderResponse(data))
                .collect(Collectors.toList()));
        orderPageResponse.setTotalPage(pages.getTotalPages());

        return orderPageResponse;
    }

}
