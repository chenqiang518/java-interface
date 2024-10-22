package com.vernon.jsonschema.utils;

import com.jayway.jsonpath.JsonPath;
import com.vernon.utils.DatabaseUtils;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DatabaseTest {
    static final String DB_URL = "jdbc:mysql://litemall.hogwarts.ceshiren.com:13306/litemall?useSSL=false";
    static final String USER = "test";
    static final String PASS = "test123456";
    static final String QUERY = "select * from litemall_cart where user_id=23 and deleted=0";
    @Test
    void getDataBySql()  {
        // 初始化数据库工具类
        DatabaseUtils databaseUtils = new DatabaseUtils(DB_URL, USER, PASS);
        // 提取查询结果中所有的goods_name
        List<String> dataResult = databaseUtils.getResultStringListBySQL(QUERY, "goods_name");
        // 断言数据库数据是否包含hogwarts
        System.out.println(dataResult);
        assertTrue(dataResult.contains("hogwarts"));

        String resultSetBySQL = databaseUtils.getResultSetBySQL(QUERY);
        List<String> goodsNames = JsonPath.read(resultSetBySQL, "$..goods_name");
        System.out.println(goodsNames);
        assertAll(
                () -> assertThat(goodsNames,hasItems("hogwarts"))
        );

    }
}
