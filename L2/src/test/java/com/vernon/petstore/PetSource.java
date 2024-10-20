package com.vernon.petstore;

import com.vernon.dto.PetDTO;

import java.util.stream.Stream;

public class PetSource {

    public static Stream<PetDTO> addTest(){
        return Stream.of(new PetDTO(){{
            this.setPetId("12345677654321");
            this.setPetName("newPetName");
            this.setStatus("available");
        }});
    }


    public static Stream<PetDTO> getByStatusTest(){
        return Stream.of(new PetDTO(){{
            this.setPetId("12345677654321");
            this.setPetName("newPetName");
            this.setStatus("available");
        }});
    }

    public static Stream<PetDTO> getByPetIdTest(){
        return Stream.of(new PetDTO(){{
            this.setPetId("12345677654321");
            this.setPetName("newPetName");
            this.setStatus("available");
        }});
    }

    public static Stream<PetDTO> updateTest(){

        return Stream.of(new PetDTO(){{
            this.setPetId("12345677654321");
            this.setPetName("updatePetName");
            this.setStatus("available");
        }});
    }

    public static Stream<PetDTO> deleteTest(){
        return Stream.of(new PetDTO(){{
            this.setPetId("12345677654321");
            this.setPetName("newPetName");
            this.setStatus("available");
        }});
    }
}
