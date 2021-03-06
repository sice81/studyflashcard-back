#!/bin/sh
PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin
JAVA=/usr/bin/java
HOME=./
PID_FILE=./genius-api-pid

if [ -f ${PID_FILE} ]; then
        echo "shutting down previous..."
        PID=$(cat $PID_FILE);
        kill $(PID);
        rm $(PID_FILE)
fi

echo "Starting genius-api ..."
java -jar $(ls ./build/libs/*.jar) $(cat /home/ec2-user/argument.txt) &
echo $! > ${PID_FILE}
echo $!
echo "genius-api started ..."