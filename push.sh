#!/bin/bash

cd $(dirname $0)

docker push unterstein/dcos-for-java-devs-service:latest
docker push unterstein/dcos-for-java-devs-database:latest
