#!/bin/bash

cd $(dirname $0)

mvn clean install
docker build --tag unterstein/dcos-for-java-devs-service:latest .
