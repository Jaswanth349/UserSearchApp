package com.publicis.sapient.user_search_api.model;


import jakarta.persistence.*;

@Entity
@Table(name = "hair")
public class Hair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String color;
    private String type;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Hair(String color, String type) {
        this.color = color;
        this.type = type;
    }

    public Hair() {
    }
}

