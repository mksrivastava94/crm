package com.crm.erp.util;

import com.crm.erp.model.entity.Product;
import com.crm.erp.model.request.ProductRequest;
import com.crm.erp.model.response.ProductResponse;
import org.modelmapper.ModelMapper;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class BasicUtil {


    private BasicUtil(){
    }

    public static boolean notEmptyNotNull(String input){
        return !(input == null || input.isEmpty());
    }
    public static boolean notEmptyNotNull(Integer input){
        return input != null && input > 0;
    }

    public static boolean notEmptyNotNull(Double input){
        return !(input == null || input > 0);
    }

    public static boolean isValidatePhoneNumber(String input){
        return input.length() == 10;
    }

    public static boolean isValidEmailId(String emailId) {
        String emailRegex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";

        Pattern pat = Pattern.compile(emailRegex);
        if (emailId == null)
            return false;
        return pat.matcher(emailId).matches();
    }


    public static ProductResponse convertProductToProductResponse(Product product){
        return new ModelMapper().map(product, ProductResponse.class);
    }

    public static Product convertProductRequestToProduct(ProductRequest request){
        return new ModelMapper().map(request,Product.class);
    }

    public static List<ProductResponse> convertProductListToProductDtoList(List<Product> productList) {
        return productList.stream()
                .map(product -> new ModelMapper().map(product, ProductResponse.class))
                .collect(Collectors.toList());
    }
}

