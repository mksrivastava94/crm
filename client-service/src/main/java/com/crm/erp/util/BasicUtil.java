package com.crm.erp.util;

import com.crm.erp.model.response.ClientResponse;
import com.crm.erp.model.entity.Client;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.stream.Collectors;

public class BasicUtil {

    private BasicUtil(){}

    public static boolean notEmptyNotNull(String input){
        return !(input == null || input.isEmpty() );
    }

    public static boolean notEmptyNotNull(Integer input){
        return input != null && input > 0;
    }

    public static boolean notEmptyNotNull(Double input){
        return input != null && input > 0.0;
    }

    public static boolean isValidatePhoneNumber(String input){
        return input.length() == 10;
    }

}
