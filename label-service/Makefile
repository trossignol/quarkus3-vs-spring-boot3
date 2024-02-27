default: build

dev:
	mvn clean quarkus:dev

gatling:
	mvn gatling:test

build:
docker build -f Dockerfile.native -t label-service .
	#	mvn clean package && docker build -f Dockerfile.jvm -t label-service .

build-load-balancer:
	docker-compose build load-balancer

run: build-load-balancer
	docker-compose up -d

stop:
	docker-compose stop

restart:
	stop run

perf:
	oha -c 2000 -z 10s "http://localhost:8090/api/test"