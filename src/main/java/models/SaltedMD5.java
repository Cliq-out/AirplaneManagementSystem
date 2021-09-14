package models;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;

public class SaltedMD5 {
	
	public String[] insertSecureCode(String password ) throws NoSuchAlgorithmException, NoSuchProviderException {
        String passwordToHash = password ;
        byte[] salt = getSalt();
        char[] cbuf = new char[salt.length];
        for (int i = 0; i < salt.length; i++) {
            cbuf[i] = (char) salt[i];
        }
        String s = new String(cbuf);
        String securePassword = getSecurePassword(passwordToHash, salt);
        return new String[] { securePassword,s} ;
    }
	
	public  String  getSaltCode(String password , String saltString)  {
        String passwordToHa = password ;
        String pp = saltString ;
        byte[] out = new byte[pp.length()];
        for (int i = 0; i < pp.length(); i++) {
            out[i] = (byte) pp.charAt(i);
        }
        String securePassword1 = getSecurePassword(passwordToHa, out);
       return securePassword1;
    }
	
    public static String getSecurePassword(String passwordToHash, byte[] salt){
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(salt);
            byte[] bytes = md.digest(passwordToHash.getBytes());
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
   
    public static byte[] getSalt() throws NoSuchAlgorithmException, NoSuchProviderException
    {
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG", "SUN");
        byte[] salt = new byte[16];
        sr.nextBytes(salt);
        return salt;
    }
}
