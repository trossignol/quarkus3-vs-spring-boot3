FROM quay.io/quarkus/ubi-quarkus-graalvmce-builder-image:jdk-21 AS build
COPY --chown=quarkus:quarkus mvnw /code/mvnw
COPY --chown=quarkus:quarkus .mvn /code/.mvn

USER quarkus
WORKDIR /code