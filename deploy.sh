#!/bin/bash
gradle shadowJar
scp -i ~/git/Radico/keys/radico.pem build/libs/sql-extractor-1.0-SNAPSHOT-all.jar ec2-user@ci.radi.co:sql-extractor-1.0-SNAPSHOT-all.jar
