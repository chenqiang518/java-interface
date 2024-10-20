package com.vernon.json;

import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class TestJson {

    @Test
    public void testJsonStr() {
        String jsonStr = "{\"name\":\"Vernon\",\"age\":20}";
        given()
                .log().all()
                .contentType("application/json; charset=utf-8")
                .body(jsonStr)
        .when()
                .post("https://httpbin.ceshiren.com/post")
        .then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void testJsonObj() {
        JSONObject jsonObj = new JSONObject(){{
            put("name", "Vernon");
            put("age", 20);
        }};
        given()
        .log().all()
                .contentType("application/json; charset=utf-8")
                .body(jsonObj)
        .when()
                .post("https://httpbin.ceshiren.com/post")
        .then()
                .log().all()
                .statusCode(200);
    }
}
