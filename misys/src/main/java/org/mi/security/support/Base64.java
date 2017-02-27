package org.mi.security.support;

import java.io.UnsupportedEncodingException;

/**
 * Created by Administrator on 2015/12/5 0005.
 */
public class Base64 {
    public static String base64Decode(String psw) throws Exception{
        sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
        byte[] bs = decoder.decodeBuffer(psw);
        return new String(bs,"UTF-8");
    }

    public static String base64Eecode(String psw) throws UnsupportedEncodingException {
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        return encoder.encode(psw.getBytes("UTF-8"));
    }
}
