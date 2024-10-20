package com.vernon.body.json;


import com.alibaba.fastjson2.JSONObject;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;

public class TestJson {

    @Test
    void objectPostJson(){
        // 直接构造一个 JSON 对象，适用于格式不复杂的场景
        JSONObject requestBody = new JSONObject();
        requestBody.put("key1", "value1");
        requestBody.put("key2", "value2");
        given()
                // body()方法传递 JSON 请求体，类型为 String
                .body(requestBody.toString()).log().all()
        .when()
                .post("https://httpbin.ceshiren.com/post")
                // 打印全部的相应信息
        .then()
                .log().all();
    }

    @Test
    void stringPostJson(){
        // 从现有的请求信息中 copy 的请求体
        String jsonData = "{\"username\":\"hogwarts\",\"password\":\"test12345\",\"code\":\"\"}";
        given()
                .body(jsonData).log().all()
        .when()
                .post("https://litemall.hogwarts.ceshiren.com/admin/auth/login")
                // 打印全部的相应信息
        .then()
                .log().all();

    }
}
