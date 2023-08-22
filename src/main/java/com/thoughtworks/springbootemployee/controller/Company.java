package com.thoughtworks.springbootemployee.controller;

public class Company {

    private Long id;
    private String name;


    public Company(Long id, String name) {
        this.id = id;
        this.name = name;
    }
    public Long getCompanyId() {
        return id;
    }
    public String getCompanyName() {
        return name;
    }
    public void setCompanyName(String companyName) {
        this.name = companyName;
    }
    public void setId(Long id){
        this.id = id;
    }

}
