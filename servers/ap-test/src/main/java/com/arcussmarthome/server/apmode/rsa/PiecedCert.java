package com.arcussmarthome.server.apmode.rsa;

public class PiecedCert {
	private String n = "00:eb:0f:f9:4c:5a:4f:93:2d:52:67:57:e4:92:8a:"+
			"f1:49:6f:ad:41:7c:0f:71:b2:c0:14:1f:9c:1c:96:"+
	        "5a:94:b9:97:4c:83:fa:fb:7b:5d:8b:d2:11:ab:21:"+
	        "39:d6:75:0e:e3:f0:4d:03:e5:f4:ab:ff:d2:87:72:"+
	        "c6:0c:5b:f8:6a:b3:29:06:1d:45:33:f3:1f:68:79:"+
	        "13:f6:19:4c:01:19:5e:3e:8f:15:49:bc:d7:a5:2a:"+
	        "9b:1f:6b:c3:25:05:ff:e1:a7:67:81:4e:9e:a6:38:"+
	        "3d:ee:f5:d5:ad:97:21:22:45:ec:20:88:55:03:cb:"+
	        "1f:6a:4a:dc:b6:b8:35:69:91";
	
	private String e = "010001";
	
	public String getN(){
		return n;
	}
	
	public String getE(){
		return e;
	}
	
	@Override
	public String toString(){
		return "\"n\" = "+n+ " \"e\" "+ e;
	}

}
