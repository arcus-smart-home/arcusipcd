package com.arcussmarthome.ipcd.client.comm;

import javax.net.ssl.SSLContext;

/**
 * Creates a bogus {@link javax.net.ssl.SSLContext}.  A client-side context created by this
 * factory accepts any certificate even if it is invalid.
 * <p>
 * TODO implement o and ou checking
 * <p>
 * Modified from {@link io.netty.example.securechat.SecureChatSslContextFactory}
 */
public final class IpcdClientSslContextFactory {

    private static final String PROTOCOL = "TLS";
    private static final SSLContext CONTEXT;

    static {
        SSLContext clientContext;
        try {
           clientContext = SSLContext.getInstance(PROTOCOL);
           clientContext.init(null, IpcdClientSslTrustManagerFactory.getTrustManagers(), null);
           
           /*
           String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
           if (algorithm == null) {
              algorithm = "SunX509";
           }
           
           KeyStore ks = KeyStoreLoader.loadKeyStore("dskeystore.jks", "tempest");
           KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
           kmf.init(ks, "tempest".toCharArray());
           clientContext = SSLContext.getInstance(PROTOCOL);
           clientContext.init(kmf.getKeyManagers(), IpcdClientSslTrustManagerFactory.getTrustManagers(), null);
           */
           
        } catch (Exception e) {
            throw new Error(
                    "Failed to initialize the client-side SSLContext", e);
        }

        CONTEXT = clientContext;
    }

    public static SSLContext getContext() {
        return CONTEXT;
    }

    private IpcdClientSslContextFactory() {
        // Unused
    }
}
