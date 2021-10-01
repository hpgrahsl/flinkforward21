#!/bin/sh

set -eo pipefail

WORKING_DIR=`pwd`
echo $WORKING_DIR

echo "building petclinic monolith ..."
cd $WORKING_DIR/spring-petclinic
mvn clean package -DskipTests

echo "building quarkus owner service ..."
cd $WORKING_DIR/quarkus-owner-service
mvn clean package

echo "building flink custom functions ..."
cd $WORKING_DIR/custom-functions
mvn clean package

echo "building flink table joiner ..."
cd $WORKING_DIR/flink-table-joiner
mvn clean package

echo "DONE!"
