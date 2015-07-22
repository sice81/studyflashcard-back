# studyflashcard-back

1. 이클립스 mars를 다운

2. 이클립스 > Help > Eclipse Marketplace - 'gradle' 설치

3. import > Gradle > Gradle Project > Root folder에 소스 디렉터리 복사 > Build Model

4. 빌드가 끝나면 아래 Project에 studyflashcard-back이 생성됨

경우에 따라 gradlew JVM 파라미터로 아래 설정 바람 
"-Dhttp.proxyHost=127.0.0.1" "-Dhttp.proxyPort=8080" "-Dhttps.proxyHost=127.0.0.1" "-Dhttps.proxyPort=8080"

# 개발환경 설정 - 아마존 EC2

1. java -version > 버전 확인 8 필요

2. yum install java-1.8.0-openjdk-devel

3. sudo alternatives --config java > 2번 선택

## 배포

wget https://pji-tokyo1.s3.amazonaws.com/dist-dev/studyflashcard-back.tar
tar xvf studyflashcard-back.tar
cd /home/ec2-user/studyflashcard-back;./bin/studyflashcard-back

sudo su
yum update

/home/ec2-user/studyflashcard-back

1. 아래 /etc/init.d/genius-api 로 파일생성
2. 서비스등록 - chkconfig --add genius-api
3. service start genius-api
```sh
#!/bin/sh
### BEGIN INIT INFO
# Provides:          vsftpdg
# Required-Start:    $local_fs $remote_fs $network $syslog
# Required-Stop:     $local_fs $remote_fs $network $syslog
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# X-Interactive:     true
# Short-Description: Start/stop vsftpdg server
### END INIT INFO

PATH=/bin:/sbin:/usr/bin:/usr/sbin:/usr/local/bin:/usr/local/sbin
JAVA=/usr/bin/java
HOME=/home/ec2-user/studyflashcard-back

case $1 in
    start)
        echo "Starting genius-api ..."
        if [ ! -f ${HOME}/pid ]; then
                cd ${HOME}
                ${HOME}/bin/studyflashcard-back &> app.log &
            echo $! > ${HOME}/pid
            echo "genius-api started ..."
        else
            echo "genius-api is already running ..."
        fi
    ;;
    stop)
        if [ -f ${HOME}/pid ]; then
            PID=$(cat ${HOME}/pid);
            echo "Stopping genius-api ..."
            kill $PID;
            echo "genius-api stopped ..."
            rm ${HOME}/pid
        else
            echo "genius-api is not running ..."
        fi
    ;;
    restart)
        if [ -f /home/ec2-user/genius-study/pid ]; then
            PID=$(cat /home/ec2-user/genius-study/pid);
            echo "Stopping vsftpdg ...";
            kill $PID;
            echo "Vsftpdg stopped ...";
            rm /home/ec2-user/genius-study/pid

            echo "Starting vsftpdg ..."
            nohup java -jar /usr/local/vsftpdg/vsftpdg_server.jar /usr/local/vsftpdg 2>> /dev/null >> /dev/null &
            echo $! > /usr/local/vsftpdg/pid
            echo "Vsftpdg started ..."
        else
            echo "Vsftpdg is not running ..."
        fi
    ;;
esac
```
