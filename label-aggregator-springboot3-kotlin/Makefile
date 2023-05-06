default: run

clean:
	./gradlew clean

dev:
	./gradlew bootRun

build:
	./gradlew clean build

run: build
	java -jar build/libs/aggregator-*-SNAPSHOT.jar

build-native:
	gradle bootBuildImage --imageName=label-aggregator-springboot3-kotlin

run-native:
	docker run --rm -p 8080:8080 label-aggregator-springboot3-kotlin