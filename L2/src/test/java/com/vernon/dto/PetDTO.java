package com.vernon.dto;

public class PetDTO {
    private String petId;
    private String petName;
    private String status;

    public String getPetId() {
        return petId;
    }

    public String getPetName() {
        return petName;
    }


    public String getStatus() {
        return status;
    }

    public void setPetId(String petId) {
        this.petId = petId;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "PetDTO{" +
                "petId='" + petId + '\'' +
                ", petName='" + petName + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
