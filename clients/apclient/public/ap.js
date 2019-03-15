
host = "http://192.168.42.101/v1/"

var cert= null;

$(document).ready(function(){
    $("vendorButton").click(function(){
        $.get(host+"vendor", function(data){
        	var vendor;
        	if(data.vendor) {
        		vendor = data.vendor
        	} else {
        		vendor = data
        	}
       		$('.vendor-id').append(vendor);
        });
    });
    
    $("modelButton").click(function(){
        $.get(host+"model", function(data){
        	var model;
        	if(data.model){
        		model = data.model	
        	} else {
        		model = data
        	}
       		$('.model-id').append(model);
        });
    });

    $("serialButton").click(function(){
        $.get(host+"serial", function(data){
        	var serial;
        	if(data.serial){
        		serial = data.serial
        	} else {
        		serial = data
        	}
       		$('.serial-id').append(serial);
        });
    });

    $("certButton").click(function(){
        $.get(host+"cert", function(data){
			if(!data.cert) {
				data = JSON.parse(data)
			}
			cert = data.cert		
       		$('.cert-id').append(JSON.stringify(cert));
       	});
    });

    $("statusButton").click(function(){
        $.get(host+"status", function(data){
        	var status;
        	if(data.status){
        		status = data.status
        	} else {
        		status = data
        	}
       		$('.status-id').append(status);
        });
    });

    $("scanButton").click(function(){
        $.get(host+"scanresults", function(data){
            var tbl_body = document.createElement("tbody");
            var odd_even = false;
            if(!data.scanresults) {
  		        var data = JSON.parse(data);
  		    } 
            for (var i=0;i<data.scanresults.length;i++) {
                var network = data.scanresults[i];
                console.log("Found network:"+JSON.stringify(network));
                var tbl_row = tbl_body.insertRow();
                tbl_row.className = odd_even ? "odd" : "even";
                var cell = tbl_row.insertCell();
                cell.appendChild(document.createTextNode(network.ssid.toString()));
                cell = tbl_row.insertCell();
                cell.appendChild(document.createTextNode(JSON.stringify(network.security)));
                cell = tbl_row.insertCell();
                cell.appendChild(document.createTextNode(network.channel.toString()));
                cell = tbl_row.insertCell();
                cell.appendChild(document.createTextNode(network.signal.toString()));
            }
       		$('#scan-results').append(tbl_body);
        });
    });

    $("sendButton").click(function(){
			var ssid_name = document.querySelector('.ssid_name').value;
			var pass = document.querySelector('.password').value;
			var encrypt_pass = encryptData(pass, cert)
    	$.ajax({
    		url: host+"network",
     		method: "POST",
        	data : JSON.stringify({
        		ssid : ssid_name,
        		password : encrypt_pass
        		}),
        	contentType: "application/json"
        	});
    	});

});

    function encryptData(text, cert){
    	console.log("Encrypting ["+ text + "] with cert:" ,cert);
    	var rsa = new RSAKey()
    	rsa.setPublic(cert.n, cert.e);
        var encrypted = rsa.encrypt(text);
        var base64 = hex2b64(encrypted);
        console.log("Encrypted password ["+ linebrk(base64, 64) +"]");
        return base64
    }
