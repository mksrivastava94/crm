package com.technu.erp.util;

import com.technu.erp.model.request.MaterialDto;
import com.technu.erp.model.entity.Material;
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


    public static MaterialDto convertMaterialToMaterialDto(Material material){
        return new ModelMapper().map(material,MaterialDto.class);
    }

    public static Material convertMaterialDtoToMaterial(MaterialDto materialDto){
        return new ModelMapper().map(materialDto,Material.class);
    }

    public static List<MaterialDto> convertMaterialListToMaterialDtoList(List<Material> materialList) {
        return materialList.stream()
                .map(material -> new ModelMapper().map(material,MaterialDto.class))
                .collect(Collectors.toList());
    }
}

