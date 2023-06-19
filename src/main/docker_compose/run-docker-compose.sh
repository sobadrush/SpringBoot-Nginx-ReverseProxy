#!/bin/sh

EXEC_DIR=$PWD
cd $EXEC_DIR
echo "執行目錄: $EXEC_DIR"

# 倒數 function
countdown() {
  seconds=$1
  echo "倒數 $seconds 秒開始"
  for ((i=seconds; i>=1; i--)); do
    echo "...$i..."
    sleep 1
  done
  echo "倒數結束"
}

docker-compose stop
countdown 2
docker-compose rm -f
countdown 2
docker-compose up -d



