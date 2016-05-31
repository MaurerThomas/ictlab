#!/bin/sh
sudo docker run -d -e NODEMANAGER_ALL='http://145.24.222.223:8080/api/containers/' -e NODEMANAGER_START='http://145.24.222.223:54623/api/Command/' -p 8080:8080 --name api -t dockyou-api