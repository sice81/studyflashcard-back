#!/bin/sh
PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin
JAVA=/usr/bin/java
HOME=./
PID_FILE=/tmp/genius-api-pid

if [ -f ${PID_FILE} ]; then
	echo "shutting down previous..."
	PID=$(cat $PID_FILE)
	kill $(PID)
fi

echo "Starting genius-api ..."
if [ ! -f ${PID_FILE} ]; then
	java -jar $(ls ./build/libs/*.jar) $(cat /home/ec2-user/argument.txt) &
	echo $! > ${PID_FILE}
	echo $!
	echo "genius-api started ..."
fi