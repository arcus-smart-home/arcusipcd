# IPCD

IP Connected Device (IPCD) is a protocol that various internet connected devices use to communicate with the Arcus platform. Notable consumer products include the IRIS Wifi Smart Plugs, the AOSmith Hot Water Heater, etc.)

This project includes a sample server and client that can be used independently of purchasing devices or setting up arcusplatform.

# Introduction

The IP Connected Device Protocol (IPCD) is intended to support a variety of functions related to the monitoring and control of devices connected to the Internet of Things.  It specifies two different methods for communication between devices and a centralized platform.  On-demand devices use periodic HTTP POSTs to deliver periodic reports and important events and can receive commands from the platform in response.  Persistently connected devices use WebSockets so that the platform can send commands as needed through their always-on connection.  For security reasons, and to facilitate IP connected devices that are behind home Internet gateways performing Network Address Translation (NAT), the IPCD Protocol requires that all connections (on-demand or persistent) be initiated by the device. 

# Building

`./gradlew :ipcd-lib:jar`

The resulting jar will be in ./ipcd-lib/build/libs/ipcd-lib-x.y.z-\*.jar
