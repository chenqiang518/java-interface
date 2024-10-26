package com.vernon.verify;

import io.restassured.filter.cookie.CookieFilter;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

// Cookie鉴权
public class AddCookieByFilter {

    // 问题：通常 cookie 是不能像用户名密码一样有直接的数据的。
    // 解决方案: 模拟正常的set-cookie的过程

    @Test
    void addCookieByFilter(){

        CookieFilter cookieFilter = new CookieFilter();
        // 第一次请求: 客户端向服务端发起 set-cookie 的请求操作
        // -> https://httpbin.ceshiren.com/cookies/set/user/ad
        // <- set-cookie
        // -> redirect https://httpbin.ceshiren.com/cookies

        given()
                .redirects().follow(false).filter(cookieFilter)
        .when()
                .get("https://httpbin.ceshiren.com/cookies/set/user/ad")
        .then()
                .log().all();

        // 问题: 如何在收到 set-cookie 后，将对应的 cookie 信息提取出来，并添加在下一次请求的 cookie 中。
        // 解决方案: 使用 cookieFilter
        // 第二次请求, 添加 cookie

        given()
                .filter(cookieFilter)
        .when()
                .get("https://httpbin.ceshiren.com/cookies")
        .then()
                .log().all();

        // 第三次请求添加新的 cookie 字段值
        given()
                .filter(cookieFilter).cookie("counts","2")
        .when()
                .get("https://httpbin.ceshiren.com/cookies")
        .then()
                .log().all();
    }
}
