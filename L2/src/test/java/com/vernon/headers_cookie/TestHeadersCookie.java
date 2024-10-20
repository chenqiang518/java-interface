package com.vernon.headers_cookie;

import com.alibaba.fastjson2.JSONObject;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.specification.ProxySpecification.host;

public class TestHeadersCookie {

    @Test
    public void testHeaders() {

        // 配置添加代理，方便 Charles 监听 请求和响应
        RestAssured.proxy=host("127.0.0.1").withPort(8888);
        // 忽略 HTTPS 校验
        RestAssured.useRelaxedHTTPSValidation();

        JSONObject headers = new JSONObject(){{
            put("header1", "value1");
            put("header2","value2");
        }};
        given()
                // 添加单个 header 键值对，且支持多个值
                .header("User-Agent", "Mozilla","Chrome")
                // 添加多个 header 键值对
                .headers(headers)
        .when()
                .get("https://httpbin.ceshiren.com/get")
        .then()
                .statusCode(200);

    }

    @Test
    public void testCookies() {
        // 配置添加代理，方便 Charles 监听 请求和响应
        RestAssured.proxy=host("127.0.0.1").withPort(8888);
        // 忽略 HTTPS 校验
        RestAssured.useRelaxedHTTPSValidation();

        given()
                // 通过header/headers 添加 Cookie
                .header("Cookie", "my_cookie1=value1")
                // 通过cookie 添加 单个cookie信息
                .cookie("my_cookie2","value2")
                // 通过 cookies 添加多个 cookie信息
                .cookies("1_cookie","1_value","2_cookie","2_value")
        .when()
                .get("https://httpbin.ceshiren.com/get")
        .then()
                .statusCode(200);
    }
}
