package org.mi.core.util;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.Sha1Hash;
import org.apache.shiro.crypto.hash.Sha256Hash;

public class EncryptUtil {


    /**
     * SHA-512 算法
     * @param pwd 密码
     * @param salt 盐值
     * @param iterations hash次数
     * @return String 加密后的16进制串
     */
    public static String sha512(String pwd, String salt, int iterations){
        return new DefaultHashService().
                computeHash(
                        new HashRequest.Builder().
                                setAlgorithmName("SHA-512").
                                setSource(pwd).
                                setSalt(salt).setIterations(iterations)
                                .build()
                ).toHex();
    }

    /**
     * SHA-512 算法 hash 1次
     * @param pwd 密码
     * @param salt 盐值
     * @return String 加密后的16进制串
     */
    public static String sha512(String pwd, String salt){
        return sha512(pwd, salt, 1);
    }

    /**
     * SHA-512 算法 hash 1次
     * @param pwd 密码
     * @return String 加密后的16进制串
     */
    public static String sha512(String pwd){
        return sha512(pwd, null);
    }




    /**
     * SHA-256 算法
     * @param pwd 密码
     * @param salt 盐值
     * @param iterations hash次数
     * @return String 加密后的16进制串
     */
    public static String sha256(String pwd, String salt, int iterations){
        return new Sha256Hash(pwd, salt, iterations).toHex();
    }

    /**
     * SHA-256 算法 hash 1次
     * @param pwd 密码
     * @param salt 盐值
     * @return String 加密后的16进制串
     */
    public static String sha256(String pwd, String salt){
        return sha256(pwd, salt, 1);
    }

    /**
     * SHA-256 算法 hash 1次
     * @param pwd 密码
     * @return String 加密后的16进制串
     */
    public static String sha256(String pwd){
        return sha256(pwd, null);
    }


    /**
     * MD5 算法
     * @param pwd 密码
     * @param salt 盐值
     * @param iterations hash次数
     * @return String 加密后的16进制串
     */
    public static String md5(String pwd, String salt, int iterations){
        return new Md5Hash(pwd, salt, iterations).toHex();
    }

    /**
     * MD5 算法 hash 1次
     * @param pwd 密码
     * @param salt 盐值
     * @return String 加密后的16进制串
     */
    public static String md5(String pwd, String salt){
        return md5(pwd, salt, 1);
    }

    /**
     * MD5 算法 hash 1次
     * @param pwd 密码
     * @return String 加密后的16进制串
     */
    public static String md5(String pwd){
        return md5(pwd, null);
    }



    /**
     * SHA-1 算法
     * @param pwd 密码
     * @param salt 盐值
     * @param iterations hash次数
     * @return String 加密后的16进制串
     */
    public static String sha1(String pwd, String salt, int iterations){
        return new Sha1Hash(pwd, salt, iterations).toHex();
    }

    /**
     * SHA-1 算法 hash 1次
     * @param pwd 密码
     * @param salt 盐值
     * @return String 加密后的16进制串
     */
    public static String sha1(String pwd, String salt){
        return sha1(pwd, salt, 1);
    }

    /**
     * SHA-1 算法 hash 1次
     * @param pwd 密码
     * @return String 加密后的16进制串
     */
    public static String sha1(String pwd){
        return sha1(pwd, null);
    }


    /**
     * 生成随机盐值
     * @param numBytes 字节数
     * @return String 16进制盐值
     */
    public static String generatorSalt(int numBytes){
        return new SecureRandomNumberGenerator().nextBytes(numBytes).toHex();
    }

    /**
     * 生成8字节的随机盐值
     * @return String 16个字符的16进制盐值
     */
    public static String generatorSalt8Byte(){
        return generatorSalt(8);
    }

    public static void main(String[] args) {
        System.out.println(EncryptUtil.sha512("admin"));
        System.out.println(EncryptUtil.sha256("admin"));
        System.out.println(EncryptUtil.sha1("admin"));
        System.out.println(EncryptUtil.md5("admin"));
        System.out.println(EncryptUtil.generatorSalt(8));
        String salt = EncryptUtil.generatorSalt(8);
        System.out.println(EncryptUtil.sha1("test", salt, 1024) + " == " + salt);
        System.out.println(EncryptUtil.md5("admin", "admin"+salt, 2));
    }

}
