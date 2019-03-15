#!/bin/bash

keytool -genkey -keyalg RSA -alias myplatform -keystore config/keystore.jks -storepass password -validity 360 -keysize 2048