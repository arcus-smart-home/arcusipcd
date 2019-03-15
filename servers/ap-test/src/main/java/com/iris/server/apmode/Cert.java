package com.iris.server.apmode;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import com.iris.server.apmode.rsa.PiecedCert;


public class Cert {
	private PiecedCert cert;
	//TODO: get cert from resources and extract pieces
	public PiecedCert getCert(){
		return new PiecedCert();
	}
}
