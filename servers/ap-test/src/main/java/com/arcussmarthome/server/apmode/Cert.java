package com.arcussmarthome.server.apmode;

import com.arcussmarthome.server.apmode.rsa.PiecedCert;


public class Cert {
	private PiecedCert cert;
	//TODO: get cert from resources and extract pieces
	public PiecedCert getCert(){
		return new PiecedCert();
	}
}
