package com.arcussmarthome.server.apmode;

import java.security.KeyFactory;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
public class Controller {
	
	KeyFactory factory;
	
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
		Security.addProvider(new BouncyCastleProvider());
	}
	
	@RequestMapping(value = "/v1/vendor", method=RequestMethod.GET)
	public Vendor vendor(){
			return new Vendor();
	}
	
	@RequestMapping(value = "/v1/model", method=RequestMethod.GET)
	public Model model(){
			return new Model();
	}
	
	@RequestMapping(value = "/v1/serial", method=RequestMethod.GET)
	public Serial serial(){
			return new Serial();
	}
	
	@RequestMapping(value = "/v1/status", method=RequestMethod.GET)
	public Status status(){
			return new Status();
	}	

	@RequestMapping(value = "/v1/cert", method=RequestMethod.GET)
	public Cert cert(){
			return new Cert();
	}
	
	@RequestMapping(value = "/v1/scanresults", method=RequestMethod.GET)
	public ScanResults scanresults(){
			return new ScanResults();
	}
	
	@CrossOrigin(origins = "*", methods=RequestMethod.POST)
	@RequestMapping(value = "v1/network", method=RequestMethod.POST)
	public ResponseEntity<Void>add(@RequestBody EncryptedNetwork en) {
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@RequestMapping(value= "/v1/network", method=RequestMethod.OPTIONS)
	public void corsHeaders(HttpServletResponse response) {
	    response.addHeader("Access-Control-Allow-Origin", "*");
	    response.addHeader("Access-Control-Allow-Methods", "POST, OPTIONS");
	    response.addHeader("Access-Control-Request-Methods", "POST");
	    response.addHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with, user-agent, referrer");
	    response.setStatus(HttpServletResponse.SC_NO_CONTENT);
	}

}
