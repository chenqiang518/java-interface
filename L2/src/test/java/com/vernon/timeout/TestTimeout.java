package com.vernon.timeout;

import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.config.RestAssuredConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.specification.ProxySpecification.host;

public class TestTimeout {

    @BeforeAll
    static void setupClass() {
        RestAssured.baseURI = "https://httpbin.ceshiren.com";
        // 配置全局代理
        RestAssured.proxy = host("localhost").withPort(8888);
        // 忽略 HTTPS 校验
        RestAssured.useRelaxedHTTPSValidation();
    }
    @Test
    void testCase1(){
        given()
        .when()
                .get("/get")
        .then()
                .statusCode(200);

    }
    @Test
    void testCase2(){

        // 创建 HttpClientConfig 实例 并设置 超时时间为3秒
        HttpClientConfig httpClientConfig = HttpClientConfig
                .httpClientConfig()
                .setParam("http.connection.timeout", 5000)
                .setParam("http.socket.timeout", 3000);

        // 创建 RestAssuredConfig 实例 并传入 HttpClientConfig 实例对象
        RestAssuredConfig restAssuredConfig = RestAssuredConfig
                .config()
                .httpClient(httpClientConfig);

        given()
                // 传入 RestAssuredConfig 实例 设置超时时间为3秒
                .config(restAssuredConfig)
                // 配置局部代理
                .proxy("localhost", 8888)
                // 忽略 HTTPS 校验
                .relaxedHTTPSValidation()
                .log().all()
        .when()
                .get("/delay/10")
        .then()
                .statusCode(200);

    }
    @Test
    void testCase3(){

        given()
        .when()
                .get("/get")
        .then()
                .statusCode(200);


    }
}
