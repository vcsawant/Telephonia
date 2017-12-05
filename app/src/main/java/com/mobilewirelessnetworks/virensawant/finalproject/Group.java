package com.mobilewirelessnetworks.virensawant.finalproject;

/**
 * Created by virensawant on 12/3/17.
 */

public class Group {
    private int capacity;
    private int size;
    private String name;

    public Group(){
        name = "null";
        capacity=0;
        size = 0;
    }

    public Group(String name, int capacity){
        this.name = name;
        this.capacity = capacity;
        size = 0;
    }

    public Group(String name, int size, int capacity){
        this.name = name;
        this.capacity = capacity;
        this.size = size;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
