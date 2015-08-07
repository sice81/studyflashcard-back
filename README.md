# studyflashcard-back

1. 이클립스 mars를 다운

2. 이클립스 > Help > Eclipse Marketplace - 'gradle' 설치

3. import > Gradle > Gradle Project > Root folder에 소스 디렉터리 복사 > Build Model

4. 빌드가 끝나면 아래 Project에 studyflashcard-back이 생성됨

경우에 따라 gradlew JVM 파라미터로 아래 설정 바람 
"-Dhttp.proxyHost=127.0.0.1" "-Dhttp.proxyPort=8080" "-Dhttps.proxyHost=127.0.0.1" "-Dhttps.proxyPort=8080"

# 개발환경 설정 - 아마존 EC2

## 타임존 변경
```
sudo mv /etc/localtime /etc/localtime_org
sudo ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime
```

## 자바 1.8 설치
```
sudo yum install java-1.8.0-openjdk-devel
```

## 자바 1.8로 설정
```
sudo alternatives --config java
```

## git 설치 (DEV)
```
yum install git-core
```

## jenkins 설치 (DEV)
```
sudo wget -O /etc/yum.repos.d/jenkins.repo http://pkg.jenkins-ci.org/redhat/jenkins.repo
sudo rpm --import https://jenkins-ci.org/redhat/jenkins-ci.org.key
sudo yum install jenkins
service jenkins restart
```

### jenkins 포트 설정 8081
```
sudo su
vi /etc/init.d/jenkins

83 라인부근에 아래 추가
JENKINS_PORT=8081
```

### jenkins 접속
```
http://[IP]:8081
jenkins 관리 > 플러그인 관리 > 설치가능 > GIT plugin, GitHub plubin 설치
```


## 각종 스크립트 생성
argument.txt, deploy.sh, sdeploy.sh, genius-api 생성

### argument.txt
```
--app.db.driver=org.h2.Driver --app.db.url=jdbc:h2:~/h2db/test --app.db.username=sa
```

### deploy.sh - tar 전체 배포
```sh
#!/bin/sh
service genius-api stop
rm -rf studyflashcard-back*
wget https://pji-tokyo1.s3.amazonaws.com/dist-dev/studyflashcard-back.tar
tar xvf studyflashcard-back.tar
rm studyflashcard-back.tar
service genius-api start
```

### sdeplay.sh - jar 간편 배포
```sh
#!/bin/sh
service genius-api stop
wget https://pji-tokyo1.s3.amazonaws.com/dist-dev/genius-study-0.1.1.jar.original
cp genius-study-0.1.1.jar.original ./studyflashcard-back/lib/genius-study-0.1.1.jar
rm genius-study-0.1.1.jar.original
service genius-api start
```

### genius-api
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
                ${HOME}/bin/studyflashcard-back $(cat /home/ec2-user/argument.txt) &> app.log &
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

## 서비스 등록
```
sudo su
cp genius-api /etc/init.d/
chmod 755 /etc/init.d/genius-api
chkconfig --add genius-api
```

## 서비스 시작
```
service genius-api start; tail -f studyflashcard-back/app.log
```

## S3 CORS
```
<?xml version="1.0" encoding="UTF-8"?>
<CORSConfiguration xmlns="http://s3.amazonaws.com/doc/2006-03-01/">
  <CORSRule>
    <AllowedOrigin>*</AllowedOrigin>
    <AllowedMethod>GET</AllowedMethod>
	<AllowedMethod>OPTIONS</AllowedMethod>
    <AllowedHeader>*</AllowedHeader>
  </CORSRule>
</CORSConfiguration>
```

## 헬스체크 '/nop' 추가, request 인터셉터 예외처리/허용

## 로드밸런스 OUTBOUND 80 풀어주기

## 클라우드프론트
Access-Control-Request-Headers
Access-Control-Request-Method
