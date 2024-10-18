package com.vernon.response.json;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static io.restassured.RestAssured.given;

public class TestBody {
    // 响应嵌套提取。
    @Test
    void extractJson(){
        String jsonData = "{\"username\":\"hogwarts\",\"password\":\"test12345\",\"code\":[1,2,3]}";
        ArrayList<Integer> data = given()
                .body(jsonData)
                .when()
                .post("https://httpbin.hogwarts.ceshiren.com/post")
                .then()
                .log().all()
                .extract().path("json.code");
        System.out.println(data);
    }
    // 提取数组中的元素。
    @Test
    void extractArray(){
        String jsonData = "{\"username\":\"hogwarts\",\"password\":\"test12345\",\"code\":[1,2,3]}";
        Integer data = given()
                .body(jsonData)
                .when()
                .post("https://httpbin.hogwarts.ceshiren.com/post")
                .then()
                .log().all()
                .extract().path("json.code[0]");
        System.out.println(data);
    }
}