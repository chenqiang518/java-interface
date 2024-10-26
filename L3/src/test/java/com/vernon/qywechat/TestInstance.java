package com.vernon.qywechat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class TestInstance {
    private static int id;

    @BeforeAll
    static void setUpBeforeClass() throws Exception {
        Department department = Department.getInstance();
        id = department.getId();

    }
    @Test
    public void test() {
        System.out.println(id);
        System.out.println(id);

    }


    @Test
    public void test2() {
        System.out.println(id);
        System.out.println(id);
    }
}
