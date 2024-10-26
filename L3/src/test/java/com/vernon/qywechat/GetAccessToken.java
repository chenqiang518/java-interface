package com.vernon.qywechat;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.vernon.dto.AccessTokenDTO;
import com.vernon.dto.SetScopeDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.HashMap;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class GetAccessToken {
    private static final GetAccessToken instance = new GetAccessToken();

    private int id = -1;

    private static final String REMOTE = "https://qyapi.weixin.qq.com";

    private static  String accessToken ;

    private GetAccessToken(){};

    public static GetAccessToken getInstance(){
        return instance;
    }

    public int getId() {
        return ++id;
    }

    // 获取 AccessToken
    @ParameterizedTest
    @MethodSource("com.vernon.qywechat.QYWeChatSource#getAccessToken")
    public DocumentContext getAccessToken(AccessTokenDTO accessTokenDTO) {
        String getTokenURI = "/cgi-bin/gettoken";
        HashMap<String, Object> params = new HashMap<>() {{
            put("corpid", accessTokenDTO.getCorpid());
            put("corpsecret", accessTokenDTO.getCorpsecret());
        }};
        Response response = given()
                .log().all()
                .queryParams(params)
                .when()
                .get(REMOTE + getTokenURI)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();

        int errcode = response.getBody().jsonPath().getInt("errcode");
        String errmsg = response.getBody().jsonPath().getString("errmsg");
        assertThat(errcode, equalTo(0));
        assertThat(errmsg, equalTo("ok"));

        accessToken = response.jsonPath().getString("access_token");
        return JsonPath.parse(response.getBody().asString());

    }


    // 生成allure报告: mvn clean test -Dtest=com.vernon.businesswechat.GetAccessTokenTest allure:report
    // 查看allure报告: mvn allure:serve
}
