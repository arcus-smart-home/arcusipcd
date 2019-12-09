#!/bin/bash

java -cp ".:devices/:config/:lib/*" com.arcussmarthome.ipcd.client.comm.IpcdClient $1 $2
