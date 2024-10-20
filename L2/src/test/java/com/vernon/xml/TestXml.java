package com.vernon.xml;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static io.restassured.RestAssured.given;

public class TestXml {
    @Test
    public void testXml() throws IOException {

        // 定义请求体数据：源自 XML 文件对象
        File file = new File("src/test/resources/add.xml");
        FileInputStream fis = new FileInputStream(file);
        String xmlStr = IOUtils.toString(fis, StandardCharsets.UTF_8);
        fis.close();
        given()
                // 定制请求内容媒体类型
                .contentType("text/xml; charset=utf-8")
                // 定制请求体数据
                .body(xmlStr)
                .log().all()
        .when()
                .post("http://dneonline.com//calculator.asmx")
        .then()
                .log().all()
                .statusCode(200);

    }
}
