default: build

build:
	docker build -f src/main/docker/Dockerfile.native -t label-service .
#	mvn clean package && docker build -f src/main/docker/Dockerfile.jvm -t label-service .

build-loadbalancer:
	docker-compose build loadbalancer

run: build-loadbalancer
	docker-compose up -d

stop:
	docker-compose stop

restart: stop run