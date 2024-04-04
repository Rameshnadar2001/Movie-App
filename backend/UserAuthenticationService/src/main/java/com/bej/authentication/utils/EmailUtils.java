package com.bej.authentication.utils;

public class EmailUtils {
   public static String getVerifcationUrl(String host,String token){
        return host +"/api/v0/verify?token=" + token;
   }
}
