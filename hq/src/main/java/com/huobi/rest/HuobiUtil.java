package com.huobi.rest;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

public class HuobiUtil {

    public static String hashMac256(String data, String secret) {

    	try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
            sha256_HMAC.init(secret_key);
            String hash = Base64.encodeBase64String(sha256_HMAC.doFinal(data.getBytes()));
            return hash;
        }
        catch (Exception e){
            throw new RuntimeException("Unable to sign message.", e);
        }

    }

    // 加密
    public static String encode(String str) {
        String result = null;
        try {
            org.apache.commons.codec.binary.Base64.encodeBase64String(str.getBytes("utf-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }

    // 解密
    public static String decode(String s) {
        String result = null;
        try {
            result = new String(org.apache.commons.codec.binary.Base64.decodeBase64(s),"utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static String buildQuery(Map<String, String> map){
        if(map == null || map.isEmpty()){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> e:map.entrySet()){
            if(first){
                first = false;
            }else {
                sb.append("&");
            }
            try {
				sb.append( e.getKey() ).append("=").append( urlEncode(e.getValue()) );
			} catch (UnsupportedEncodingException e1) {
				e1.printStackTrace();
			}
        }
        return sb.toString();
    }

    public static String urlEncode(String s) throws UnsupportedEncodingException {
        return URLEncoder.encode(s,"utf-8");
    }


}
