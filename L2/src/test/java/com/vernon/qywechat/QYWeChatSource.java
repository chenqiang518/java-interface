package com.vernon.qywechat;

import com.vernon.dto.AccessTokenDTO;
import com.vernon.dto.DepartmentDTO;
import com.vernon.dto.SetScopeDTO;

import java.util.ArrayList;
import java.util.stream.Stream;

public class QYWeChatSource {

    private static int id = (int) (Math.random()*1000000);

    public static Stream<AccessTokenDTO> getAccessToken() {
        return Stream.of(new AccessTokenDTO("wwf69470c1623ad868","eF-hqCKrO_mnuBjDpjDKtyXYVr-knJ9IYZueE7sCzj8"));

    }

    public static Stream<DepartmentDTO> createDepartmentTest(){
        return Stream.of(new DepartmentDTO(){{
            this.setName("测试研发中心质量部");
            this.setNameEn("RDSHZLB");
            this.setParentId(1688856693496739L);
            this.setOrder(1);
            this.setId(id);
        }});
    }

    public static Stream<DepartmentDTO> listDepartmentTest(){
        return Stream.of(new DepartmentDTO(){{
            this.setName("测试研发中心质量部");
            this.setNameEn("RDSHZLB");
            this.setParentId(1688856693496739L);
            this.setOrder(1);
            this.setId(id);
        }});
    }

    public static Stream<DepartmentDTO> getDepartmentTest(){
        return Stream.of(new DepartmentDTO(){{
            this.setName("测试研发中心质量部");
            this.setNameEn("RDSHZLB");
            this.setParentId(1688856693496739L);
            this.setOrder(1);
            this.setId(id);
        }});
    }

    public static Stream<DepartmentDTO> delDepartmentTest(){
        return Stream.of(new DepartmentDTO(){{
            this.setName("测试研发中心质量部");
            this.setNameEn("RDSHZLB");
            this.setParentId(1688856693496739L);
            this.setOrder(1);
            this.setId(id);
        }});
    }

}
