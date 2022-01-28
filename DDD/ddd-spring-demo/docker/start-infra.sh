#!/bin/bash

docker stop mysql-demo redis-demo && docker rm mysql-demo redis-demo

script_path=$(dirname "$0")
cd "$script_path" && cd ../

docker-compose -f "$script_path"/docker-compose-infra.yml up -d
# build 시 테스트와 서버에 필요한 환경 구성을 위한 compose
