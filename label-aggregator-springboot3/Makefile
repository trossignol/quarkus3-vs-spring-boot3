default: build

build:
	docker build -f Dockerfile.native -t label-aggregator-springboot3 .

run:
	docker run -p 8080:8080 --network="host" label-aggregator-springboot3