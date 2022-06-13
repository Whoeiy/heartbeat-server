package com.example.heartbeatserver.util;

import org.springframework.util.DigestUtils;

public class Md5Util {
   public static String getMd5(String s) {
       return DigestUtils.md5DigestAsHex(s.getBytes());
   }
}
