package com.arcussmarthome.ipcd.server.netty;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.Security;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IpcdServerTlsContext {

	private static final Logger logger = LoggerFactory.getLogger(IpcdServerTlsContext.class);
	
	private static final String PROTOCOL = "TLS";
	
	private boolean useTls = true;
	private final SSLContext _context;
	
	public IpcdServerTlsContext() {
		this (true,
				System.getProperty("keystore.file.path"), 
				System.getProperty("keystore.file.password"), 
				System.getProperty("keystore.file.path"));
	}
	
	public IpcdServerTlsContext(Boolean useTls, String keystoreFilePath, String keystoreFilePassword, String keyPassword) {
		
		this.useTls = useTls;
		
		if (useTls) {
			SSLContext serverContext = null;
	        try {
	            String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
	            if (algorithm == null) { algorithm = "SunX509";  }
	
	            try {
	                KeyStore ks = KeyStore.getInstance("JKS");
	                //FileInputStream fin = new FileInputStream(keystoreFilePath);
	                
	                ks.load(getKeyStoreInputStream(keystoreFilePath), keystoreFilePassword.toCharArray());
	                KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
	                kmf.init(ks, keyPassword.toCharArray());
	
	                serverContext = SSLContext.getInstance(PROTOCOL);
	                serverContext.init(kmf.getKeyManagers(), null, null);
	            } catch (Exception e) {
	                throw new Error("Failed to initialize the server-side SSLContext", e);
	            }
	        } catch (Exception ex) {
	            logger.error("Error initializing SslContextManager.", ex);
	        } finally {
	        	_context = serverContext;
	        }
		} else {
			_context = null;
		}
	}
	
	private InputStream getKeyStoreInputStream(String keystoreFilePath) throws IOException {
		// first try to load the keystore file relative to the main server class or jar
		InputStream keystoreInputStream = null;
		try {
			keystoreInputStream = this.getClass().getResourceAsStream("/" + keystoreFilePath);
			if (keystoreInputStream == null) throw new IllegalStateException("Keystore not Found");
			return keystoreInputStream;
		} catch (Exception e) {
			logger.debug("Did not find keystore file " + keystoreFilePath + " on classpath");
		}
		
		// if that failed, try as absolute path
		try {
			keystoreInputStream = new FileInputStream(keystoreFilePath);
			return keystoreInputStream;
		} catch (Exception e) {
			logger.debug("Did not find keystore file " + keystoreFilePath + " as fully-qualified path on filesystem");
		}
		
		throw new IOException("Could not find keystore file at " + keystoreFilePath);
		
	}
	
	
	public SSLContext getContext() {
        return _context;
    }

	public boolean useTls() {
		return useTls;
	}
	
	public void setUseTls(boolean useTls) {
		this.useTls = useTls;
	}

	
	
	
}
