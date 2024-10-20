package com.vernon.multipart;

import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.specification.ProxySpecification.host;

public class TestMultipart {
    @Test
    void testMultipart(){

        // 需要上传的文件对象
        File file = new File("src/test/resources/hogwarts.txt");

        // 定义本地代理配置
        RestAssured.proxy = host("127.0.0.1").withPort(7897);
        // 忽略 HTTPS 证书校验
        RestAssured.useRelaxedHTTPSValidation();

        given()
                .log().all()
                .contentType("multipart/form-data")
                // 传递文件对象
                .multiPart("hogwarts", file)
                // 传递JSON数据
                .multiPart("ceshiren","{\"hogwarts\",6666}","application/json")
                .when()
                .post("https://httpbin.ceshiren.com/post")
                .then()
                .log().all()
                .statusCode(200);

    }
}
