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
}
