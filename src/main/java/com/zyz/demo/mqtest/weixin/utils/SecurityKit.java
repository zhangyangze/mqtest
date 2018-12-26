package com.zyz.demo.mqtest.weixin.utils;/**
 * Created by zhangyangze on 2018/12/25
 */

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * class name SecurityKit
 *
 * @author zhang yang ze
 * @date 2018/12/25
 */
public class SecurityKit {

    /**
     * 对字符串进行sha1加密
     * @param str
     * @return
     */
    public static String sha1(String str){
        StringBuffer sb = new StringBuffer();
        try {
            MessageDigest digest = MessageDigest.getInstance("sha1");
            digest.update(str.getBytes());
            byte[] digestMsg = digest.digest();
            for(byte b : digestMsg){
                sb.append(String.format("%02x",b));
            }
            return  sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

}
