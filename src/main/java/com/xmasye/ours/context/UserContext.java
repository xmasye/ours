package com.xmasye.ours.context;

public class UserContext {

    private static ThreadLocal<String> openidThreadLocal = new ThreadLocal<String>();

    public static String getOpenid() {
        String value = openidThreadLocal.get();
        if(value==null){
            return null;
        }else{
            return value;
        }
    }

    public static void setOpenid(String openid) {
        openidThreadLocal.set(openid);
    }

    public static void clearOpenid(){
        openidThreadLocal.remove();
    }

}
