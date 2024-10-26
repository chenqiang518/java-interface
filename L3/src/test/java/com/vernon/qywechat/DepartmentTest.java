package com.vernon.qywechat;

import com.jayway.jsonpath.DocumentContext;
import com.vernon.dto.AccessTokenDTO;
import com.vernon.dto.DepartmentDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class DepartmentTest {

    private static String accessToken;
    private static Department department;

   @BeforeAll
    public static void beforeAll() {
        AccessTokenDTO accessTokenDTO = QYWeChatSource.getAccessToken().collect(Collectors.toList()).get(0);

        DocumentContext getAccessToken = GetAccessToken.getInstance().getAccessToken(accessTokenDTO);
        accessToken = getAccessToken.read("$.access_token");
        //accessToken = "";
        department = Department.getInstance();
   }

    @ParameterizedTest
    @MethodSource("com.vernon.qywechat.QYWeChatSource#departmentTest")
    @DisplayName("创建部门测试")
    @Order(1)
    public void createDepartmentTest(DepartmentDTO departmentDTO) {
        DocumentContext context = department.createDepartment(departmentDTO, accessToken);

        int errcode = context.read("$.errcode");
        String errmsg = context.read("$.errmsg");
        Integer actualId = context.read("$.id");

        assertAll(
                () -> assertThat(errcode,equalTo(0)),
                () -> assertThat(errmsg,equalTo("created")),
                () -> assertThat(actualId,equalTo(departmentDTO.getId()))
        );

        getSimpleListTest(departmentDTO);
    }

    @ParameterizedTest
    @MethodSource("com.vernon.qywechat.QYWeChatSource#departmentTest")
    @DisplayName("获取子部门ID列表测试")
    @Order(2)
    public void getSimpleListTest(DepartmentDTO departmentDTO) {

        DocumentContext context = department.getSimpleList(departmentDTO, accessToken);
        int errcode = context.read("$.errcode");
        String errmsg = context.read("$.errmsg");
        List<Integer> ids = context.read("$..id");
        List<Integer> parentIds = context.read("$..parentid");
        List<Integer> orders = context.read("$..order");

        assertAll(
                () -> assertThat(errcode,equalTo(0)),
                () -> assertThat(errmsg,equalTo("ok")),
                () -> assertThat(ids,hasItem(equalTo(departmentDTO.getId()))),
                () -> assertThat(parentIds,hasItem(equalTo(departmentDTO.getParentId()))),
                () -> assertThat(orders,hasItem(equalTo(departmentDTO.getOrder())))
        );

    }


    @ParameterizedTest
    @MethodSource("com.vernon.qywechat.QYWeChatSource#departmentTest")
    @DisplayName("删除部门测试")
    @Order(3)
    public void delDepartmentTest(DepartmentDTO departmentDTO) {

        DocumentContext delContext = department.delDepartment(departmentDTO, accessToken);
        int errcode = delContext.read("$.errcode");
        String errmsg = delContext.read("$.errmsg");

        assertAll(
                () -> assertThat(errcode,equalTo(0)),
                () -> assertThat(errmsg,equalTo("deleted"))
        );

        DocumentContext getContext = department.getSimpleList(departmentDTO, accessToken);

        List<Integer> partyId = getContext.read("$..id");

        assertAll(
                () -> assertThat(partyId, not(hasItem(departmentDTO.getId())))
        );


    }


}
