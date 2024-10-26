package com.vernon.envs;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;

import static io.restassured.RestAssured.given;

public class TestEnvsYaml {

    @BeforeAll
    static void beforeAll() throws IOException {
        /*
           使用 Jackson 读取 yaml 读取文件
         */
        // 示例化一个 ObjectMapper 对象
        ObjectMapper yamlMapper = new ObjectMapper(new YAMLFactory());

        // 读取 resources 目录中的 envs.yaml 配置文件
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        File yamlFile = new File(Objects.requireNonNull(contextClassLoader.getResource("envs.yaml")).getFile());

        // 定义序列化结构 TypeReference<>()
        TypeReference<HashMap<String,String>> typeRef = new TypeReference<HashMap<String, String>>() {};

        // 解析 envs.yaml 文件内容，并指定 返回内容为 定义序列化结构 typeRef
        HashMap<String, String> envs = yamlMapper.readValue(yamlFile, typeRef);

        // 设定基线域名地址为 选定的域名地址
        RestAssured.baseURI = envs.get(envs.get("default"));

    }

    @Test
    void testEnvsYaml() {
        // 发起请求
        given()
        .when()
                .get("/get")
        .then()
                .log().all()
                .statusCode(200);
    }

}
