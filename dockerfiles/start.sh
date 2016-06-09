#!/bin/sh
sudo docker run -d -e NODEMANAGER='http://145.24.222.223:8080/nodemanager/api' -p 8080:8080 --name api -t dockyou-api