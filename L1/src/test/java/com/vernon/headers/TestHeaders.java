package com.vernon.headers;

import org.junit.jupiter.api.Test;
import java.util.HashMap;
import static io.restassured.RestAssured.given;

public class TestHeaders {
    @Test
    void header(){
        given()
                .headers("Authorization", "verifyMessage")
                .headers("name", "ad")
                .log().all()
        .when()
                .get("https://httpbin.ceshiren.com/get")
        .then()
                .log().all();
    }

    @Test
    void headerMap(){
        HashMap<String, String> headerMap = new HashMap<>();
        headerMap.put("Authorization", "verifyMessage");
        headerMap.put("name", "ad");
        headerMap.put("school", "hogwarts");
        given()
                .headers(headerMap)
                .log().all()
        .when()
                .get("https://httpbin.ceshiren.com/get")
        .then()
                .log().all();
    }
}
