package com.thoughtworks.springbootemployee.controller;

public class Employee {
    private Long id;
    private String name;
    private Integer age;
    private String gender;
    private Integer salary;
    private Long companyId;

    public Employee(Long id, String name, Integer age, String gender, Integer salary, Long companyId) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
        this.companyId = companyId;
    }

    public Long getEmployeeId() {
        return id;
    }

    public String getEmployeeName() {
        return name;
    }

    public Integer getEmployeeAge() {
        return age;
    }

    public String getEmployeeGender() {
        return gender;
    }

    public Integer getEmployeeSalary() {
        return salary;
    }

    public Long getCompanyId() {
        return companyId;
    }
    public void setEmployeeName(String name) {
        this.name = name;
    }

    public void setEmployeeAge(Integer age) {
        this.age = age;
    }

    public void setEmployeeGender(String gender) {
        this.gender = gender;
    }

    public void setEmployeeSalary(Integer salary) {
        this.salary = salary;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }
}
