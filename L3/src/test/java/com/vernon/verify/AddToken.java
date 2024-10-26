package com.vernon.verify;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

// token鉴权
public class AddToken {
 //
    @Test
    void addToken() {
        // token 的获取方法
        String body = "{\"username\":\"admin123\",\"password\":\"admin123\",\"code\":\"\"}";
        String token=given()
                .contentType(ContentType.JSON)
                .body(body)
        .when()
                .post("https://litemall.hogwarts.ceshiren.com/admin/auth/login")
        .then()
                .log().all()
                .extract().path("data.token");

        // 添加 token 以解决接口鉴权问题
        // 强调: token 的认证过程，尽量与研发确认
        given()
                .header("X-Litemall-Admin-Token", token)
        .when()
                .get("https://litemall.hogwarts.ceshiren.com/admin/goods/list")
        .then()
                .log().all();

    }
}
