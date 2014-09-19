#!/bin/bash

set -x
set -e

WORKSPACE=$1
BUILD_NUMBER=$2

cd native 
HADOOP_SRC=/usr/src/hadoop make clean all 
mvn -f jenkins/repository/pom.xml deploy:deploy-file --settings jenkins/repository/settings.xml -Durl=dav:https://repository-kosei.forge.cloudbees.com/release -DrepositoryId=kosei.release -Dfile=${WORKSPACE}/native/ad-creator -DgroupId=com.kosei.jobs -DartifactId=ad-creator -Dversion=0.${BUILD_NUMBER} -Dpackaging=binary
