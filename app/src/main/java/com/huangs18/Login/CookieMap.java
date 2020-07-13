package com.huangs18.Login;

import java.util.HashMap;

public class CookieMap {
    public HashMap<String, String> cookies = new HashMap();
    public String toCookieString() {
        String cookieString = "";
        for (HashMap.Entry<String, String> cookie : cookies.entrySet()) {
            cookieString += cookie.getKey() + "=" + cookie.getValue() + ";";
        }
        return cookieString.substring(0, cookieString.length() - 1);
    }
    public void saveCookies(String cookieString) {
        // 没有cookies则收到null
        if (cookieString == null)	return;
        String[] cookieStringList = cookieString.split(";");
        for (String eachCookie : cookieStringList) {
            String[] cookieKeyValue = eachCookie.split("=");
            if (cookieKeyValue.length > 1 && !cookieKeyValue[0].equals(" Path")) {
                if (!cookies.containsKey(cookieKeyValue[0])) {
                    cookies.put(cookieKeyValue[0], cookieKeyValue[1]);
                }
            }
        }
    }
    public boolean isEmpty() {
        return cookies.isEmpty();
    }
}
