package com.vernon.jsonschema.verify;


import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

// auth 鉴权
public class AddAuth {

    @Test
    void addAuth() {
        given()
                .auth().preemptive().basic("user", "ad")
        .when()
                .get("https://httpbin.ceshiren.com/basic-auth/user/ad")
        .then()
                .log().all();
    }
}
