package com.duanzi.he.duanzi.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


public class MD5Util {
    private static MD5Util instance;



    public String getMD5String(String... values) {
        MessageDigest md5;
        try {
            String str = "";
            for (String value : values) {
                str += value;
            }
//            LogUtil.e("拼接的字符串："+str);
            md5 = MessageDigest.getInstance("MD5");
            md5.update(str.getBytes());
            return getString(md5.digest());
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    private String getString(byte[] bytes) {
        // 首先初始化一个字符数组，用来存放每个16进制字符

        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};

        // new一个字符数组，这个就是用来组成结果字符串的（解释一下：一个byte是八位二进制，也就是2位十六进制字符（2的8次方等于16的2次方））

        char[] resultCharArray = new char[bytes.length * 2];

        // 遍历字节数组，通过位运算（位运算效率高），转换成字符放到字符数组中去

        int index = 0;

        for (byte b : bytes) {

            resultCharArray[index++] = hexDigits[b >>> 4 & 0xf];

            resultCharArray[index++] = hexDigits[b & 0xf];

        }
        // StringBuilder sb = new StringBuilder();
        // for (int i = 0; i < bytes.length; i++) {
        // String hex = Integer.toHexString(0xFF & bytes[i]);
        // sb.append(hex);
        // }
       String str= new String(resultCharArray);
//        LogUtil.e("加密后的字符串："+str);
        return str;
    }

    private MD5Util() {

    }

    public static MD5Util getInstance() {
        if (instance == null) {
            synchronized (MD5Util.class) {
                if (instance == null) {
                    instance = new MD5Util();
                }
            }
        }
        return instance;
    }
}
