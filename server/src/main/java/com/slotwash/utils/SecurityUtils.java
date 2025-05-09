package com.slotwash.utils;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

public final class SecurityUtils {

    private SecurityUtils() {}

    public static String getCurrentUserLogin(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if(authentication!=null){
            Map<String,String> properties;
            try{
                properties = BeanUtils.describe(authentication);
            }
            catch(Exception e){
                return null;
            }
            if(properties.containsKey("principal")){
                return properties.get("principal");
            }
        }
        return null;
    }
    
}