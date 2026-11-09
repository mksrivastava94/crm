package com.technu.erp.util;

public class FileUtil {
    public static String getImageExtension(String base64){

        String[] strings = base64.split(",");
        String extension;
        switch (strings[0]) {
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            case "data:application/pdf;base64":
                extension = "pdf";
                break;
            default:
                extension = "jpg";
                break;
        }
        return extension;
    }

}
