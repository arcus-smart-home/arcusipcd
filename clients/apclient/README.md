#IPCD Device AP Mode Commissioning Server


##Introduction
The IPCD device AP mode Commissioning Server is a minimal server for testing the AP Mode commissioning.
It can be used to better understand the AP Mode Commissioning Specification.


##Configure
----------------
Minimal configuration is needed.  If the server port is changed, or the server is not running at 192.168.42.101 per the specification, the local server URL can be edited in the ap.js script file.

`host = "http://192.168.42.101/v1/"`

Ensure the `/v1/` remains at the end of the URL or the client will not find the appropriate server resources.

##Run
To start the server, execute the run bash script:

./run.sh

Please verify your environment is configured to use version 1.7 or above of the 
Java VM.

##Browser
Open http://<server>:8080 to get the AP Mode page.
Labels need to be formatted as button, so for now click text to make something happen.

###Status
Client has been successfully tested running in Chrome, Firefox and Safari on OSX Sierra 10.12.6
Client has been successfully tested running in Chrome, Firefox and Edge on Windows 10 ver 1703
Client has been successfully tested running in Chrome, and  Safari on iOs 10.13.5
Client has been successfully tested running in Chrome 8.1.0
Other clients are unknown.

#### Notes
Added RSA, this is to test the device implementation.
Decodes JSON data, uncomment JSON.parse lines for device sending text.


####ToDo

Improve usability(highlight buttons, format output). 
