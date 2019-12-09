package com.arcussmarthome.server.apmode;

public class ScanResults {
	private final Network[] scanresults;
	
	public ScanResults(){
		Network test = new Network("test", 7);
		Network iris = new Network("Iris", 11);
		scanresults = new Network[] {test, iris};
	}
	public Network[] getscanresults() {
		return scanresults;
	}
	
}
