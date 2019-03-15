#IPCD Device AP Mode Simulator


##Introduction
The IPCD device AP mode simulator is a sample device for the AP Mode pairing protocol.
It can be used to better understand the AP Mode Commissioning Specification.


##Configure
----------------
Minimal configuration is needed.  If required the server port can be changed in the
src/main/resources/application.properties file.  If it is the test client will also
need to be changed.  

##Run
To run the application, execute the run_device bash script:

./run_server.sh

Please verify your environment is configured to use version 1.7 or above of the 
Java VM.


##Console
Currently a minimal console, it currently prints the ssid and encrypted password when
a request is posted to the server. 

###Status
Server has been tested running on OSX Sierra 10.12.6
multiple clients listed in client README.

####ToDo
Extract modulus and exponent from .pem file