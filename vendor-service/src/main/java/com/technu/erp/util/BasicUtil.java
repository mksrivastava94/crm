package com.technu.erp.util;

import java.util.regex.Pattern;

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

}

