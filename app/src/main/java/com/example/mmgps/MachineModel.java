package com.example.mmgps;
public class MachineModel {

    private String id;
    private String name;

    public MachineModel(){}

    public MachineModel(String id, String name){
        this.id = id;
        this.name = name;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getId(){
        return this.id;
    }

    public String getName(){
        return this.name;
    }

}