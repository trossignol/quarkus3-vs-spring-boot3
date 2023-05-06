# Quarkus 3 vs Spring-boot 3

In this project, we want to build some basics apps to test and compare Quarkus 3 and Spring-boot 3. We want to check differents points:
* _JVM_ mode and _native_ mode
* _Reactive_ development
* _Docker_ and _K8S_ deployment
* _Serverless_ with and without _AWS SnapStart_

And we will measure several metrics:
* startup time
* memory footprint
* binary and docker image size
* scalability and response time
* cold start time for serverless
* ...  

## Architecture
```
[Gatling] (load tests)
    --> [Component]
        --> [TODO][PostgreSQL] select request
        -- x3
            --> [label-service] (~150ms tempo)
            --> [TODO][PostgreSQL] insert request
```

## Sources

About Quarkus 3:
* [Release note](https://quarkus.io/blog/road-to-quarkus-3/)
* About [io_uring](https://en.wikipedia.org/wiki/Io_uring) (_`the next generation of asynchronous IO support`_)