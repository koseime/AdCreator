#!/bin/bash

set -x
set -e

WORKSPACE=$1
BUILD_NUMBER=$2

cd native 
HADOOP_SRC=/usr/src/hadoop make clean all check
mvn -f jenkins/repository/pom.xml deploy:deploy-file --settings jenkins/repository/settings.xml -Durl=dav:https://repository-kosei.forge.cloudbees.com/private -DrepositoryId=kosei.private -Dfile=${WORKSPACE}/native/ad-creator -DgroupId=com.kosei.jobs -DartifactId=ad-creator -Dversion=0.${BUILD_NUMBER} -Dpackaging=binary
mvn -f jenkins/repository/pom.xml deploy:deploy-file --settings jenkins/repository/settings.xml -Durl=dav:https://repository-kosei.forge.cloudbees.com/private -DrepositoryId=kosei.private -Dfile=${WORKSPACE}/native/ad-creator-client -DgroupId=com.kosei.jobs -DartifactId=ad-creator-client -Dversion=0.${BUILD_NUMBER} -Dpackaging=binary
