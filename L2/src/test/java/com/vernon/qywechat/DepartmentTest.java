package com.vernon.qywechat;

import com.jayway.jsonpath.DocumentContext;
import com.vernon.dto.AccessTokenDTO;
import com.vernon.dto.DepartmentDTO;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertAll;

public class DepartmentTest {

    private static String accessToken;

   @BeforeAll
    public static void beforeAll() {
        AccessTokenDTO accessTokenDTO =new AccessTokenDTO("wwf69470c1623ad868","eF-hqCKrO_mnuBjDpjDKtyXYVr-knJ9IYZueE7sCzj8");
        DocumentContext getAccessToken = GetAccessToken.getInstance().getAccessToken(accessTokenDTO);
        accessToken = getAccessToken.read("$.access_token");
    }

    @ParameterizedTest
    @MethodSource("com.vernon.qywechat.QYWeChatSource#createDepartmentTest")
    @DisplayName("创建部门测试")
    @Order(1)
    public void createDepartmentTest(DepartmentDTO departmentDTO) {
        System.out.println(accessToken);
        DocumentContext context = Department.getInstance().createDepartment(departmentDTO, accessToken);

        int id = Department.getInstance().getId();
        int errcode = context.read("$.errcode");
        int errmsg = context.read("$.errmsg");
        int actualId = context.read("$.id");

        assertAll(
                () -> assertThat(errcode,equalTo(0)),
                () -> assertThat(errmsg,equalTo("created")),
                () -> assertThat(actualId,equalTo(id))
        );

        getDepartmentTest(departmentDTO);
    }

    @ParameterizedTest
    @MethodSource("com.vernon.qywechat.QYWeChatSource#listDepartmentTest")
    @DisplayName("获取部门列表测试")
    @Order(2)
    public void listDepartmentTest(DepartmentDTO departmentDTO) {

        int id = Department.getInstance().getId();
        DocumentContext context = Department.getInstance().getDepartment(departmentDTO, accessToken);
        int errcode = context.read("$.errcode");
        String errmsg = context.read("$.errmsg");
        List<Integer> ids = context.read("$..id");
        List<String> names = context.read("$..name");
        List<String> nameEn = context.read("$..name_en");
        List<Long> parentIds = context.read("$..parentid");
        List<Integer> orders = context.read("$..order");

        assertAll(
                () -> assertThat(errcode,equalTo(0)),
                () -> assertThat(errmsg,equalTo("ok")),
                () -> assertThat(ids,hasItem(equalTo(id))),
                () -> assertThat(names,hasItem(equalTo(departmentDTO.getName()))),
                () -> assertThat(nameEn,hasItem(equalTo(departmentDTO.getNameEn()))),
                () -> assertThat(parentIds,hasItem(equalTo(departmentDTO.getParentId()))),
                () -> assertThat(orders,hasItem(equalTo(departmentDTO.getOrder())))
        );

    }

    @ParameterizedTest
    @MethodSource("com.vernon.qywechat.QYWeChatSource#getDepartmentTest")
    @DisplayName("获取单个部门详情测试")
    @Order(3)
    public void getDepartmentTest(DepartmentDTO departmentDTO) {

        int id = Department.getInstance().getId();
        DocumentContext context = Department.getInstance().getDepartment(departmentDTO, accessToken);
        int errcode = context.read("$.errcode");
        String errmsg = context.read("$.errmsg");
        List<Integer> ids = context.read("$..id");
        List<String> names = context.read("$..name");
        List<String> nameEn = context.read("$..name_en");
        List<Integer> parentIds = context.read("$..parentid");
        List<Integer> orders = context.read("$..order");

        assertAll(
                () -> assertThat(errcode,equalTo(0)),
                () -> assertThat(errmsg,equalTo("ok")),
                () -> assertThat(ids,everyItem(equalTo(id))),
                () -> assertThat(names,everyItem(equalTo(departmentDTO.getName()))),
                () -> assertThat(nameEn,everyItem(equalTo(departmentDTO.getNameEn()))),
                () -> assertThat(parentIds,everyItem(equalTo(departmentDTO.getParentId()))),
                () -> assertThat(orders,everyItem(equalTo(departmentDTO.getOrder())))
        );

    }

    @ParameterizedTest
    @MethodSource("com.vernon.qywechat.QYWeChatSource#delDepartmentTest")
    @DisplayName("删除部门测试")
    @Order(3)
    public void delDepartmentTest(DepartmentDTO departmentDTO) {

        int id = Department.getInstance().getId();
        DocumentContext delContext = Department.getInstance().delDepartment(departmentDTO, accessToken);
        int errcode = delContext.read("$.errcode");
        String errmsg = delContext.read("$.errmsg");

        assertAll(
                () -> assertThat(errcode,equalTo(0)),
                () -> assertThat(errmsg,equalTo("deleted"))
        );

        DocumentContext getContext = Department.getInstance().getDepartment(departmentDTO, accessToken);

        List<DepartmentDTO> department = getContext.read("$.department");

        assertAll(
                () -> assertThat(department, Matchers.empty())
        );


    }


}
