#!/bin/bash

cd $(dirname $0)

docker build --tag dcos-for-java-devs/database:latest .
