package com.example.sell.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;

/**
 * Cookie 工具类
 * @Author: baiyj
 * @Date: 2019/10/23
 */
public class CookieUtils {

    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           Integer MaxAge){
        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(MaxAge);
        response.addCookie(cookie);
    }

    /**
    * 根据name获取cookie
    *@param  [request, name]
    *@return javax.servlet.http.Cookie
    */
    public static Cookie get(HttpServletRequest request,
                             String name){
        HashMap<String, Cookie> cookieMap = getCookieMap(request);
        if (cookieMap!=null){
            for (String key : cookieMap.keySet()) {
                if (key.equals(name)){
                    return cookieMap.get(key);
                }
            }
        }
        return null;
    }


    private static HashMap<String,Cookie> getCookieMap(HttpServletRequest request){
        HashMap<String, Cookie> hashMap = new HashMap<>();
        Cookie[] cookies = request.getCookies();
        if (cookies!=null){
            for (Cookie cookie : cookies) {
                if (cookie!=null){
                    hashMap.put(cookie.getName(),cookie);
                }
            }
            return hashMap;
        }
      return null;
    }
}
