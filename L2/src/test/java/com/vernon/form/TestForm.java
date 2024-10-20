package com.vernon.form;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static io.restassured.specification.ProxySpecification.host;

public class TestForm {
    @Test
    public void testForm() {
        // 添加代理，为的是 charles 能抓到
        RestAssured.proxy=host("127.0.0.1").withPort(8888);
        RestAssured.useRelaxedHTTPSValidation();

        HashMap<String, Object> form = new HashMap<>(){{
            put("firstName", "John");
            put("lastName", "Doe");
        }};
        given()
                // 单个表单参数
                .formParam("myParam", "myValue")
                // 多个表单参数
                .formParams(form)
                .log().headers()
                .log().body()

        .when()
                .post("https://httpbin.ceshiren.com/post")
        .then()
                .log().all()
                .statusCode(200);
    }
}
