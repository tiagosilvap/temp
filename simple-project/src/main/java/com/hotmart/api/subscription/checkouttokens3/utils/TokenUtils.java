package com.hotmart.api.subscription.checkouttokens3.utils;

import org.springframework.stereotype.Component;

@Component
public class TokenUtils {
    
    public String unzipToken(String checkoutToken) {
        try {
//            String json = HashBuilder.decrypt(""); load antigo
            String json = GzipUtil.unzip(checkoutToken);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
