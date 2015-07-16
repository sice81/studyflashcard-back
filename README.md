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

