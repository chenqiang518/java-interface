package com.vernon.qywechat;

import com.alibaba.fastjson2.JSONObject;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.vernon.dto.DepartmentDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class Department {
    private static final Department instance = new Department();

    private int id = 0;
    private static final String REMOTE = "https://qyapi.weixin.qq.com";

    private Department() {
    }

    public static Department getInstance() {
        return instance;
    }

    public int getId() {
        return ++id;
    }

    // 创建部门
    public DocumentContext createDepartment(DepartmentDTO departmentDTO, String accessToken) {
        String createURI = "/cgi-bin/department/create";
        JSONObject create = new JSONObject() {{
            put("name", departmentDTO.getName());
            put("name_en", departmentDTO.getNameEn());
            put("parentid", departmentDTO.getParentId());
            put("order", departmentDTO.getOrder());
            put("id", getId());
        }};
        Response response = given()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(create.toString())
                    .queryParam("access_token", accessToken)
                .when()
                    .post(REMOTE + createURI)
                .then()
                    .log().all()
                    .statusCode(200)
                    .extract().response();
        return JsonPath.parse(response.getBody().asString());
    }

    // 获取部门列表
    public DocumentContext listDepartment(DepartmentDTO departmentDTO, String accessToken) {
        String listURI = "/cgi-bin/department/list";
        JSONObject params=new JSONObject();
        if (departmentDTO.getId() > 0) {
            params = new JSONObject() {{
                put("id", departmentDTO.getId());
            }};
        }
        Response response = given()
                    .log().all()
                    .queryParams(params)
                .when()
                    .get(REMOTE + listURI)
                .then()
                    .log().all()
                    .statusCode(200)
                    .extract().response();
        return JsonPath.parse(response.getBody().asString());
    }

    // 获取单个部门详情
    public DocumentContext getDepartment(DepartmentDTO departmentDTO, String accessToken) {
        String getURI = "/cgi-bin/department/get";
        JSONObject params = new JSONObject() {{
                put("id", departmentDTO.getId());
            }};

        Response response = given()
                .log().all()
                .queryParams(params)
                .when()
                .get(REMOTE + getURI)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
        return JsonPath.parse(response.getBody().asString());
    }

    // 删除部门
    public DocumentContext delDepartment(DepartmentDTO departmentDTO , String accessToken) {
        String getURI = "/cgi-bin/department/delete";
        JSONObject params = new JSONObject() {{
            put("id", departmentDTO.getId());
        }};

        Response response = given()
                .log().all()
                .queryParams(params)
                .when()
                .get(REMOTE + getURI)
                .then()
                .log().all()
                .statusCode(200)
                .extract().response();
        return JsonPath.parse(response.getBody().asString());
    }

}
