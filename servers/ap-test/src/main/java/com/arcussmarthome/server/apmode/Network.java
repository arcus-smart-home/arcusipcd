package com.arcussmarthome.server.apmode;

	public class Network{ 
		private String ssid;
		private final String security = "WPA2-PSK";
		private int channel;
		private final int signal = 75;
		
		Network(String ssid, int channel) {
			this.ssid = ssid;
			this.channel = channel;
		}
		
		public String getSsid(){
			return ssid;
		}
				
		public String getSecurity(){
			return security;
		}
		
		 public int getChannel(){
			return channel;
		}
		
		public int getSignal(){
			return signal;
		}
		
	}
