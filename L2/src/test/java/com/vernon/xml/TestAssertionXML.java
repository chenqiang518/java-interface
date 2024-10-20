package com.vernon.xml;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class TestAssertionXML {
    @Test
    public void testAssertionXML() throws IOException {
        // 定义请求体数据: 源自文件XML对象
        File file = new File("src/test/resources/add.xml");
        FileInputStream fis = new FileInputStream(file);
        String xmlBody = IOUtils.toString(fis, StandardCharsets.UTF_8);
        fis.close();
        given()
                .contentType("application/xml")
                .body(xmlBody)
        .when()
                .post("http://dneonline.com//calculator.asmx")

        .then()
                .statusCode(200)
                .body("//AddResult.text()",equalTo(2));
    }
}
