package com.vernon.response.json;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class TestAssertionBody {
    // 断言是否相等
    @Test
    void jsonBody(){
        given()
                .when()
                .get("https://httpbin.hogwarts.ceshiren.com/get")  // 发起GET请求
                .then()
                .log().all()  // 打印响应结果
                // 结合hamcrest响应体断言
                .body("origin", equalTo("113.89.246.184"));  //
    }
    // 断言是否相等
    @Test
    void doubleBody(){
        given()
                .when()
                .get("https://httpbin.hogwarts.ceshiren.com/get")  // 发起GET请求
                .then()
                .log().all()  // 打印响应结果
                // 结合hamcrest响应体断言
                .body("headers.Host", equalTo("httpbin.hogwarts.ceshiren.com"));  //
    }

    // 断言元素是否包含。
    @Test
    void extractArray(){
        String jsonData = "{\"username\":\"hogwarts\",\"password\":\"test12345\",\"code\":[1,2,3]}";
        given()
                .body(jsonData)
                .when()
                .post("https://httpbin.hogwarts.ceshiren.com/post")
                .then()
                .log().all()
                .body("json.code", hasItem(1));
    }

}
