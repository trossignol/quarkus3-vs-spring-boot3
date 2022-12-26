package gatling;

import static io.gatling.javaapi.core.CoreDsl.rampUsersPerSec;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.http.HttpDsl.http;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.http.HttpProtocolBuilder;

public class Simulation extends io.gatling.javaapi.core.Simulation {

    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8080")
            .acceptHeader("application/json");

    ScenarioBuilder scn = scenario("Scenario")
            .exec(http("request_1")
                    //.get("/api/test"));
     .get("/api/test/async"));
    // .get("/fruit/id/1"));

    {
        setUp(scn.injectOpen(rampUsersPerSec(1).to(30).during(10)).protocols(httpProtocol));
    }
}