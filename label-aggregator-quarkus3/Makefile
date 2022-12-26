default: build

build:
	docker build -f src/main/docker/Dockerfile.native -t label-aggregator-quarkus3 .

run:
	docker run -p 8080:8080 --network="host" label-aggregator-quarkus3