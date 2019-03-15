#!/bin/bash

java -cp ".:devices/:config/:lib/*" com.iris.ipcd.client.ux.VisualClient ws://localhost:443/ipcd/1.0 BlackBox-Switch1-123456789