package com.vernon.petstore;

import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class QueryTest {

    private static final String REMOTE="https://petstore.swagger.io/v2";

    // 1. 获取接口信息
    //  get url: /pet/findByStatus
    //  请求参数: status=available
    // 2. 编写测试用例
    // 3. 断言
    @Test
    public void search() {
        // 提取请求路径
        String findByStatusURI="/pet/findByStatus";
        // 提取请求参数
        HashMap<String,Object> query = new HashMap<>(){{
            put("status","available");
        }};
        Response response = given()
                .log().all()
                .queryParams(query)
                .when()
                .get(REMOTE + findByStatusURI)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
        // Response 获取相应体
        String string = response.getBody().asString();
        assertNotEquals("[]", string);
    }

    @ParameterizedTest
    @MethodSource("com.vernon.source.SearchSource#searchParams")
    public void searchParams(String status) {
        // 提取请求路径
        String findByStatusURI="/pet/findByStatus";
        // 提取请求参数
        HashMap<String,Object> query = new HashMap<>(){{
            put("status",status);
        }};
        Response response = given()
                .log().all()
                .queryParams(query)
                .when()
                .get(REMOTE + findByStatusURI)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
        // Response 获取相应体
        String string = response.getBody().asString();
        assertNotEquals("[]", string);
    }
}
