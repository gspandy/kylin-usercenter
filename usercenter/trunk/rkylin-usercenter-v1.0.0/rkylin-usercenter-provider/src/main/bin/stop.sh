#!/bin/bash
if [ -n "$1" ];then
	JAVA_HOME=$1
fi
cd `dirname $0`
BIN_DIR=`pwd`
cd ..
DEPLOY_DIR=`pwd`
CONF_DIR=$DEPLOY_DIR/conf

SERVER_NAME=`sed '/dubbo.application.name/!d;s/.*=//' conf/dubbo.properties | tr -d '\r'`

if [ -z "$SERVER_NAME" ]; then
    SERVER_NAME=`hostname`
fi
echo "ps -f | grep java | grep "$SERVER_NAME" |awk '{print $2}'"
PIDS=`ps -f | grep java | grep "$SERVER_NAME" |awk '{print $2}'`
if [ -z "$PIDS" ]; then
    echo "ERROR: The $SERVER_NAME does not started!"
    exit 0
fi

echo -e "Stopping the $SERVER_NAME ...\c"
for PID in $PIDS ; do
    kill -9 $PID > /dev/null 2>&1
done

COUNT=0


echo "OK!"
echo "PID: $PIDS"
