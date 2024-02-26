package gatling;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.http;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class Simulation extends io.gatling.javaapi.core.Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json");

    ScenarioBuilder scn = scenario("Scenario")
            .exec(http("request_1")
                    .get("/api/test/async?nb=3"));

    {
        setUp(scn.injectOpen(rampUsersPerSec(1).to(500).during(60)).protocols(httpProtocol));
        // setUp(scn.injectOpen(constantUsersPerSec(700).during(20)).protocols(httpProtocol));
    }
}