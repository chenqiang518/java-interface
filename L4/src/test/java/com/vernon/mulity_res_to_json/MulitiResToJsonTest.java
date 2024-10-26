package com.vernon.mulity_res_to_json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertAll;

public class MulitiResToJsonTest {


    public String mulitiResToJson(String originRes) {

        if (originRes.startsWith("<?xml")) {
            XmlMapper xmlMapper = new XmlMapper();
            try {
                JsonNode node = xmlMapper.readTree(originRes.getBytes());
                ObjectMapper jsonMapper = new ObjectMapper();
                originRes = jsonMapper.writeValueAsString(node);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return originRes;
    }

    @Test
    public void mulitiResToJsonTest(){

        String xmlResBody = given()
                .when()
                        .get("https://www.nasa.gov/rss/dyn/lg_image_of_the_day.rss")
                .then()
                        .statusCode(200)
                        .extract().response().getBody().asString();

        String jsonRes = mulitiResToJson(xmlResBody);
        JSONArray read = JsonPath.read(jsonRes, "$..title");
        assertAll(
                () -> assertThat(read.get(0),equalTo("NASA Image of the Day"))
        );

    }

}

