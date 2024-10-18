package com.vernon.params;

import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class TestParams {
    @Test
    void getURLReq(){
        when()
                .get("https://httpbin.ceshiren.com/get?name=ad&scholl=hogwarts")
        .then()
                .log().all();
    }
    @Test
    void getQueryParamReq(){

        given()
                .log().all()
                .queryParam("name", "ad")
                .queryParam("school", "hogwarts")
        .when()
                .get("https://httpbin.ceshiren.com/get")
        .then()
                .log().all();
    }

    @Test
    void getQueryParamsReq(){
        // 构造请求参数
        HashMap<String, Object> query = new HashMap<>();
        query.put("name", "ad");
        query.put("school", "hogwarts");
        // 在请求中携带参数信息
        given().log().all()
                .queryParams(query)
        .when()
                .get("https://httpbin.ceshiren.com/get")
        .then()
                .log().all();
    }
    @Test
    void postQueryParamsReq(){
        // 构造请求参数
        HashMap<String, Object> query = new HashMap<>();
        query.put("name", "ad");
        query.put("school", "hogwarts");
        // 在请求中携带参数信息
        given()
                .log().all()
                .queryParams(query)
        .when()
                .post("https://httpbin.ceshiren.com/post")
        .then()
                .log().all();
    }
}
