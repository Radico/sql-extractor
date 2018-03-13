#!/bin/bash
gradle shadowJar
scp -i ~/git/Radico/keys/radico.pem build/libs/sqlserver-extractor-1.0-SNAPSHOT-all.jar ec2-user@ci.radi.co:sqlserver-extractor-1.0-SNAPSHOT-all.jar
