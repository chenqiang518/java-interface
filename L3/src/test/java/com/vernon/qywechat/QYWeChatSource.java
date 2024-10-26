package com.vernon.qywechat;

import com.vernon.dto.AccessTokenDTO;
import com.vernon.dto.DepartmentDTO;

import java.util.stream.Stream;

public class QYWeChatSource {

    private static int id = (int) (Math.random()*1000000);

    public static Stream<AccessTokenDTO> getAccessToken() {
        return Stream.of(new AccessTokenDTO("wwf69470c1623ad868","sn0VAZ4wxM0ELk0PYXgbS1QUpVXtTLnTd1N_ib5wTO4"));

    }

    public static Stream<DepartmentDTO> departmentTest(){
        return Stream.of(new DepartmentDTO(){{
            this.setName("VERNON_"+id+"_部门自动化测试");
            this.setNameEn("VERNON_"+id);
            this.setParentId(1);
            this.setOrder(1);
            this.setId(id);
        }});
    }

}
