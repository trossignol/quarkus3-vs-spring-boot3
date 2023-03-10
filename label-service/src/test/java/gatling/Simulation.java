package gatling;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

public class Simulation extends io.gatling.javaapi.core.Simulation {

        HttpProtocolBuilder httpProtocol = http
                        .baseUrl("http://localhost:8090")
                        .acceptHeader("application/json");

        ScenarioBuilder scn = scenario("Scenario Name")
                        .exec(http("request_1")
                                        .get("/api/test"));

        {
                setUp(scn.injectOpen(constantUsersPerSec(200).during(10)).protocols(httpProtocol));
        }
}