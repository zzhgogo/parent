#!/usr/bin/env bash

#应用根目录
APP_HOME=$(cd `dirname $0`; pwd)

APP_JAR="${APP_HOME}/springboot-config.jar"

#jvm options
JAVA_OPTS="-Xms2048m -Xmx2048m -Djava.awt.headless=true  -server -XX:+UseParNewGC -XX:+UseConcMarkSweepGC -XX:CMSInitiatingOccupancyFraction=85 -XX:+DisableExplicitGC -Xnoclassgc -Xverify:none -XX:ErrorFile=/data/log/java/java_error_%p.log"

java $JAVA_OPTS -jar $APP_JAR