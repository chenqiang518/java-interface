package com.vernon.dto;

public class DepartmentDTO {
    private String name;
    private String nameEn;
    private int parentId;
    private int order;
    private int id;

    public DepartmentDTO() {}

    public DepartmentDTO(String name, String nameEn, int parentId, int order) {
        this.name = name;
        this.nameEn = nameEn;
        this.parentId = parentId;
        this.order = order;
    }

    public String getName() {
        return name;
    }

    public String getNameEn() {
        return nameEn;
    }

    public int getParentId() {
        return parentId;
    }

    public int getOrder() {
        return order;
    }
    public int getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return "DepartmentDTO{" +
                "name='" + name + '\'' +
                ", nameEn='" + nameEn + '\'' +
                ", parentId=" + parentId +
                ", order=" + order +
                ", id=" + id +
                '}';
    }
}
