package com.vernon.businesswechat;

import com.vernon.dto.AccessTokenDTO;
import com.vernon.source.GetAccessToken;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetAccessTokenTest {
    private static final String REMOTE = "https://qyapi.weixin.qq.com";

    @Test
    public void getAccessToken() {
        String getTokenURI = "/cgi-bin/gettoken";
        HashMap<String,Object> params = new HashMap<>(){{
            put("corpid","wwf69470c1623ad868");
            put("corpsecret","eF-hqCKrO_mnuBjDpjDKtyXYVr-knJ9IYZueE7sCzj8");
        }};
        Response response = given()
                .log().all()
                .queryParams(params)
            .when()
                .get(REMOTE+getTokenURI)
            .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        int errcode=response.getBody().jsonPath().getInt("errcode");
        String errmsg=response.getBody().jsonPath().getString("errmsg");
        assertThat(errcode,equalTo(0));
        assertThat(errmsg,equalTo("ok"));


    }

    @ParameterizedTest
    @MethodSource("com.vernon.source.GetAccessToken#getAccessToken")
    public void getAccessToken(AccessTokenDTO accessTokenDTO) {
        String getTokenURI = "/cgi-bin/gettoken";
        HashMap<String,Object> params = new HashMap<>(){{
            put("corpid",accessTokenDTO.getCorpid());
            put("corpsecret",accessTokenDTO.getCorpsecret());
        }};
        Response response = given()
                .log().all()
                .queryParams(params)
                .when()
                .get(REMOTE+getTokenURI)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        int errcode=response.getBody().jsonPath().getInt("errcode");
        String errmsg=response.getBody().jsonPath().getString("errmsg");
        assertThat(errcode,equalTo(0));
        assertThat(errmsg,equalTo("ok"));


    }

    // 生成allure报告: mvn clean test -Dtest=com.vernon.businesswechat.GetAccessTokenTest allure:report
    // 查看allure报告: mvn allure:serve
}
