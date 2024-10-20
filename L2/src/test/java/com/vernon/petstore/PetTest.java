package com.vernon.petstore;

import com.jayway.jsonpath.DocumentContext;
import com.vernon.dto.PetDTO;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import static io.restassured.specification.ProxySpecification.host;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("宠物商店-宠物模块")
//@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PetTest {

    @BeforeAll
    static void beforeAll() {
        /*RestAssured.proxy = host("127.0.0.1").withPort(8888);
        RestAssured.useRelaxedHTTPSValidation();*/
    }
    // 新增: POST /pet
    @ParameterizedTest
    @MethodSource("com.vernon.petstore.PetSource#addTest")
    @Order(1)
    @DisplayName("新增接口")
    void addTest(PetDTO petDTO) {
        Response response = PetInterface.getInstance().add(petDTO);

        String id = response.getBody().jsonPath().getString("id");
        String name = response.getBody().jsonPath().getString("name");
        assertAll(
                () -> assertThat(id, Matchers.equalTo(petDTO.getPetId())),
                () -> assertThat(name, Matchers.equalTo(petDTO.getPetName()))
        );

        getByPetIdTest(petDTO);

    }

    // 查询: GET /pet/findByStatus?status=available

    @ParameterizedTest
    @MethodSource("com.vernon.petstore.PetSource#getByStatusTest")
    @Order(2)
    @DisplayName("查询byStatus接口")
    void getByStatusTest(PetDTO petDTO){

        DocumentContext parse = PetInterface.getInstance().getByStatus(petDTO);

        List<Long> ids = parse.read("$..id");
        List<String> names = parse.read("$..name");
        List<String> status = parse.read("$..status");
        assertAll(
                () -> assertThat(ids,Matchers.hasItem(Long.valueOf(petDTO.getPetId()))),
                () -> assertThat(names,Matchers.hasItem(petDTO.getPetName())),
                () -> assertThat(status,everyItem(equalTo(petDTO.getStatus())))
        );
    }

    // 查询: GET /pet/{petId}

    @ParameterizedTest
    @MethodSource("com.vernon.petstore.PetSource#getByPetIdTest")
    @Order(2)
    @DisplayName("查询byStatus接口")
    void getByPetIdTest(PetDTO petDTO){

        DocumentContext parse = PetInterface.getInstance().getByPetId(petDTO,200);

        List<Long> ids = parse.read("$..id");
        List<String> names = parse.read("$..name");
        List<String> status = parse.read("$..status");
        assertAll(
                () -> assertThat(ids,Matchers.hasItem(Long.valueOf(petDTO.getPetId()))),
                () -> assertThat(names,Matchers.hasItem(petDTO.getPetName())),
                () -> assertThat(status,everyItem(equalTo(petDTO.getStatus())))
        );
    }

    // 更新: PUT /pet
    @ParameterizedTest
    @MethodSource("com.vernon.petstore.PetSource#updateTest")
    @Order(3)
    @DisplayName("更新接口")
    void updateTest(PetDTO petDTO) {
        DocumentContext update = PetInterface.getInstance().update(petDTO);
        List<Long> ids = update.read("$..id");
        List<String> names = update.read("$..name");
        assertAll(
                () -> assertThat(ids,Matchers.hasItem(Long.valueOf(petDTO.getPetId()))),
                () -> assertThat(names,Matchers.hasItem(petDTO.getPetName()))
        );

        getByPetIdTest(petDTO);

    }

    // 删除: DELETE /pet petId
    @ParameterizedTest
    @MethodSource("com.vernon.petstore.PetSource#deleteTest")
    @Order(4)
    @DisplayName("删除接口")
    void deleteTest(PetDTO petDTO) {

        DocumentContext delete = PetInterface.getInstance().delete(petDTO);

        List<String> message = delete.read("$..message");
        assertAll(

                () -> assertThat(message,everyItem(equalTo(petDTO.getStatus())))
        );

        PetInterface.getInstance().getByPetId(petDTO,404);
    }

}
