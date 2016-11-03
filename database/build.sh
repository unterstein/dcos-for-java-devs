#!/bin/bash

cd $(dirname $0)

docker build --tag unterstein/dcos-for-java-devs-database:latest .
