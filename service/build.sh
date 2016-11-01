#!/bin/bash

cd $(dirname $0)

mvn clean install
docker build --tag dcos-for-java-devs/service:latest .
