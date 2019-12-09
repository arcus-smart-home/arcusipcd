package com.arcussmarthome.server.apmode.rsa;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSA_Decryptor{
	private PrivateKey priv;
	private KeyFactory factory;	

	public RSA_Decryptor(){
		try {
			factory = KeyFactory.getInstance("RSA", "BC");
		}
		catch (Exception e){
			System.out.println(e.getMessage());
		}
		try {
			priv = generatePrivateKey(factory, "config/devicekey.pem");
			
		} catch (InvalidKeySpecException | IOException e) {
			System.out.println(e.getMessage());
		}
	}
		
	private static PrivateKey generatePrivateKey(KeyFactory factory, String filename) throws InvalidKeySpecException, FileNotFoundException, IOException {
		PemFile pemFile = new PemFile(filename);
		byte[] content = pemFile.getPemObject().getContent();
		PKCS8EncodedKeySpec privKeySpec = new PKCS8EncodedKeySpec(content);
		return factory.generatePrivate(privKeySpec);
	}
	
	public String decrypt( String text) {
	    try {
	    	byte[] buffer = Base64.getDecoder().decode(text);
	        Cipher rsa;
	        rsa = Cipher.getInstance("RSA/NONE/PKCS1Padding");
	        rsa.init(Cipher.DECRYPT_MODE, priv);
	        byte[] utf8 = rsa.doFinal(buffer);
	        return new String(utf8, "UTF8");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

}