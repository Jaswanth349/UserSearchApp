package com.publicis.sapient.user_search_api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String department;
    private String name;
    private String title;
    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;
    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Company() {
    }

    public Company(String department, String name, String title, Address address) {
        this.department = department;
        this.name = name;
        this.title = title;
        this.address = address;
    }
}

