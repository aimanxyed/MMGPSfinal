package com.example.mmgps;

public class Machine {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Machine(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Machine(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
