package com.crm.erp.util;

import com.crm.erp.dto.PurchaseDto;
import com.crm.erp.model.Purchase;
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


    public static PurchaseDto convertPurchaseToPurchaseDto(Purchase purchase){
        return new ModelMapper().map(purchase, PurchaseDto.class);
    }

    public static Purchase convertPurchaseDtoToPurchase(PurchaseDto purchaseDto){
        return new ModelMapper().map(purchaseDto, Purchase.class);

    }
    public static List<PurchaseDto> convertProfileListToProfileDto(List<Purchase> purchaseList) {
        return purchaseList.stream()
                .map(purchase -> new ModelMapper().map(purchase, PurchaseDto.class))
                .collect(Collectors.toList());
    }
}

