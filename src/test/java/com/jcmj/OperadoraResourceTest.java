package com.jcmj;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
public class OperadoraResourceTest {

    @Test
    public void testHelloEndpoint() {
        given()
          .when().get("/V1/api-operadora-cartao")
          .then()
             .statusCode(200)
             .body(is("Hello RESTEasy"));
    }

}