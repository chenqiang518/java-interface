package com.vernon.base64;

import com.jayway.jsonpath.JsonPath;
import io.restassured.http.ContentType;
import org.apache.commons.codec.binary.Base64;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

public class TestBase64 {

    @Test
    void testBase64() {

        // 定义一个明文字符串
        String str = "hogwarts";

        // 将字符串转换成字节数组
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);

        // 对明文字符串对应的字节数组 使用 Base64.encodeBase64String() 完成 Base64 加密
        String encodeStr = Base64.encodeBase64String(bytes);
        System.out.println(encodeStr);

        String jsonStr = given()
                        .contentType(ContentType.URLENC)
                        .formParams("msg", encodeStr)
                .when()
                        .post("https://httpbin.org/post")
                .then()
                        .statusCode(200)
                        .extract().response().getBody().asString();

        // 提取接口响应的密文
        String encodeMsg = JsonPath.read(jsonStr, "$.form.msg");

        // 对密文使用 Base64.decodeBase64() 完成 Base64 解密
        byte[] arr = Base64.decodeBase64(encodeStr);
        String decodeStr = new String(arr, StandardCharsets.UTF_8);

        // 对响应字段完成断言
        assertAll(
                () ->  assertThat(decodeStr,equalTo("hogwarts"))
        );

    }
}
