package com.vernon.petstore;

import com.alibaba.fastjson2.JSONObject;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.vernon.dto.PetDTO;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Optional;

import static io.restassured.RestAssured.given;

public class PetInterface {

    private  static final PetInterface instance =new PetInterface();
    //ID 计数器，默认值使 -1
    private int id = -1;
    private static final String REMOTE = "https://petstore.swagger.io/v2";

    private PetInterface(){};
    public static PetInterface getInstance(){
        return instance;
    }
    public int getId() {
        return id;
    }
/*    public String getRemote() {
        return REMOTE;
    }*/
    /*private static String REMOTE;

    @BeforeAll
    static void beforeAll() {
        REMOTE="https://petstore.swagger.io/v2";
        RestAssured.proxy = host("127.0.0.1").withPort(8888);
        RestAssured.useRelaxedHTTPSValidation();
    }*/
    // 新增: POST /pet
    public Response add(PetDTO petDTO) {
        String newPet = "{\n" +
                "  \"id\": "+petDTO.getPetId()+",\n" +
                "  \"category\": {\n" +
                "    \"id\": 0,\n" +
                "    \"name\": \"string\"\n" +
                "  },\n" +
                "  \"name\": \""+petDTO.getPetName()+"\",\n" +
                "  \"photoUrls\": [\n" +
                "    \"string\"\n" +
                "  ],\n" +
                "  \"tags\": [\n" +
                "    {\n" +
                "      \"id\": 0,\n" +
                "      \"name\": \"string\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"status\": \""+petDTO.getStatus()+"\"\n" +
                "}";
        return given()
                    .contentType(ContentType.JSON)
                    .body(newPet)
                .when()
                    .post(REMOTE + "/pet")
                .then()
                    .statusCode(200)
                    .extract().response();

    }


    // 查询byStatus: GET /pet/findByStatus?status=available
    public DocumentContext getByStatus(PetDTO petDTO) {
        JSONObject body = new JSONObject(){{
            put("status",petDTO.getStatus());
        }};

        Response response = given()
                .queryParams(body)
                .when()
                .get(REMOTE + "/pet/findByStatus")
                .then()
                .statusCode(200)
                .extract().response();
        return JsonPath.parse(response.getBody().asString());

    }

    // 查询byPetId: GET /pet/{petId}
    public DocumentContext getByPetId(PetDTO petDTO,int statusCode) {

        Response response = given()
                .when()
                .get(REMOTE + "/pet/"+petDTO.getPetId())
                .then()
                .statusCode(statusCode)
                .extract().response();
        return JsonPath.parse(response.getBody().asString());

    }

    // 更新: PUT /pet
    public DocumentContext update(PetDTO petDTO) {
        JSONObject putBody = new JSONObject(){{
            put("id",petDTO.getPetId());
            put("name",petDTO.getPetName());
        }};
        Response response = given()
                    .contentType(ContentType.JSON)
                    .body(putBody.toString())
                .when()
                    .put(REMOTE + "/pet")
                .then()
                    .statusCode(200)
                    .extract().response();

        return JsonPath.parse(response.getBody().asString());
    }

    // 删除: DELETE /pet/{petId}
    public DocumentContext delete(PetDTO petDTO) {
        Response response = given()
                .when()
                    .delete(REMOTE + "/pet/" + petDTO.getPetId())
                .then()
                    .statusCode(200)
                    .extract().response();

        return JsonPath.parse(response.getBody().toString());
    }
}
