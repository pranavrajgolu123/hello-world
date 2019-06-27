package com.example.raj.fireex;

/**
 * Created by RAJ on 8/8/2017.
 */

public class Person {


    private String name;
    private String address;

    public Person() {
      /*Blank default constructor essential for Firebase*/
    }
    //Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
