
package com.arcussmarthome.server.apmode;

import com.arcussmarthome.server.apmode.rsa.RSA_Decryptor;

public class EncryptedNetwork {
	private String ssid;
	private String encryptedPassword;

	
	public EncryptedNetwork(String ssid, String password) {
		this.ssid = ssid;
		this.encryptedPassword = password;
		RSA_Decryptor	decryptor = new RSA_Decryptor();	
		password = decryptor.decrypt(password);
		
		System.out.format("User posted SSID [%s] with decrypted password [%s]\n", ssid, password);
	}

	
	
	@Override
	public String toString(){
		return "ssid: "+ssid+", with encrypted password: "+ encryptedPassword +".";
	}
}
