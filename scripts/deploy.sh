#!/bin/bash
# 1. 변수 설정
# appspec.yml에서 복사한 jar 파일이 위치한 경로입니다.
# JAR 파일이 하나만 있다고 가정합니다.
JAR_PATH=$(ls /home/ec2-user/app/build/libs/*.jar | head -n 1)
JAR_NAME=$(basename $JAR_PATH)
echo "> 배포할 파일: $JAR_NAME" >> /home/ec2-user/app/deploy.log

# 2. 기존 애플리케이션 종료
echo "> 현재 실행중인 애플리케이션 pid 확인" >> /home/ec2-user/app/deploy.log
CURRENT_PID=$(pgrep -f $JAR_NAME)

if [ -z "$CURRENT_PID" ]; then
    echo "> 현재 구동중인 애플리케이션이 없으므로 종료하지 않습니다." >> /home/ec2-user/app/deploy.log
else
    echo "> kill -15 $CURRENT_PID" >> /home/ec2-user/app/deploy.log
    kill -15 $CURRENT_PID
    sleep 5
fi

# 3. 새 애플리케이션 배포 및 로그 기록
echo "> 새 애플리케이션 배포" >> /home/ec2-user/app/deploy.log
nohup java -jar -Dspring.profiles.active=prod $JAR_PATH >> /home/ec2-user/app/deploy.log 2>/home/ec2-user/app/deploy_err.log &

# 4. Health Check - 애플리케이션이 정상적으로 실행되었는지 확인
echo "> Health check 시작" >> /home/ec2-user/app/deploy.log

for RETRY_COUNT in {1..10}
do
  echo "> #${RETRY_COUNT} 번째 Health check 시도..." >> /home/ec2-user/app/deploy.log
  # Actuator health check API를 호출하여 응답 코드가 200인지 확인
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}" http://localhost:8080/actuator/health)

  if [ "$RESPONSE_CODE" -eq 200 ]; then
    echo "> Health check 성공" >> /home/ec2-user/app/deploy.log
    exit 0 # 성공적으로 스크립트 종료 (CodeDeploy에게 성공 알림)
  fi
  sleep 5
done

echo "> Health check 실패" >> /home/ec2-user/app/deploy.log
exit 1 # 실패 상태로 스크립트 종료 (CodeDeploy에게 실패 알림)