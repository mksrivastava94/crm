package com.crm.erp.util;

import com.crm.erp.dto.SaleBillDto;
import com.crm.erp.model.SaleBill;
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


    public static SaleBillDto convertSaleBillToSaleBillDto(SaleBill saleBill){
        return new ModelMapper().map(saleBill,SaleBillDto.class);
    }

    public static SaleBill convertSaleBillDtoToSaleBill(SaleBillDto saleBillDto){
        return new ModelMapper().map(saleBillDto,SaleBill.class);
    }
    public static List<SaleBillDto> convertSaleListToSaleDtoList(List<SaleBill> saleBillList) {
        return saleBillList.stream()
                .map(saleBill -> new ModelMapper().map(saleBill,SaleBillDto.class))
                .collect(Collectors.toList());
    }
}

