package com.vernon.response.status;

import static io.restassured.RestAssured.given;
import org.junit.jupiter.api.Test;

public class TestAssertionStatusCode {
    @Test
    void testStatusCode(){
        given()
        .when()
                .get("https://httpbin.ceshiren.com/get")  // 发起GET请求
        .then()
                .log().all()  // 打印响应结果
                .statusCode(200);  // 响应状态码断言
    }

}