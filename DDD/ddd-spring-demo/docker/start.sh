#!/bin/bash

docker stop demo-server && docker rm demo-server
docker rmi 'demo-server'

script_path=$(dirname "$0")
cd "$script_path" && cd ../

./gradlew clean build
rm -rf "$script_path"/application.jar
mv build/libs/*.jar "$script_path"/application.jar

cd "$script_path"

docker-compose up -d
