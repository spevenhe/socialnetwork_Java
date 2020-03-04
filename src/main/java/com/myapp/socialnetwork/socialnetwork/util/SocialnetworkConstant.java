package com.myapp.socialnetwork.socialnetwork.util;

public interface SocialnetworkConstant {
    /**
     * 激活成功
     */
    int ACTIVATION_SUCCESS = 0;

    /**
     * 激活失败
     */
    int ACTIVATION_REPEAT = 1;

    /**
     * 激活失败
     */
    int ACTIVATION_FAILURE = 2;

    /**
     * 默认状态登录凭证的超时时间
     */
    int DEAFULT_EXPIRED_SECONDS = 3600*12;

    /**
     * 记住状态登录凭证超时时间
     */
    int REMEMBER_EXPIRED_SECONDS = 3600*24*100;

}
