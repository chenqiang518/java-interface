package com.vernon.jsonschema;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class TestJsonSchema {

    @Test
    void func() {

        given()
                .header("Hello", "Hogwarts")
        .when()
                .get("https://httpbin.ceshiren.com/get")  // 发送请求
        .then()
                .log().all()  // 打印完整的响应信息
                .assertThat()
                .body(matchesJsonSchemaInClasspath("schema.json"));  // JSON Schema 断言
    }
}
