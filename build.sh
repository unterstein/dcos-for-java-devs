#!/bin/bash

cd $(dirname $0)

./service/build.sh
./database/build.sh
