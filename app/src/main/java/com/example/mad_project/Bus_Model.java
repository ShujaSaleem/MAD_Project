package com.example.mad_project;

public class Bus_Model {

    String Bus_id,Bus_Number, Bus_Image, Bus_Type;

    public Bus_Model()
    {

    }

    public Bus_Model(String bus_id, String bus_Number, String bus_Image, String bus_Type) {
        Bus_id = bus_id;
        Bus_Number = bus_Number;
        Bus_Image = bus_Image;
        Bus_Type = bus_Type;
    }

    public String getBus_id() {
        return Bus_id;
    }

    public void setBus_id(String bus_id) {
        Bus_id = bus_id;
    }

    public String getBus_Number() {
        return Bus_Number;
    }

    public void setBus_Number(String bus_Number) {
        Bus_Number = bus_Number;
    }

    public String getBus_Image() {
        return Bus_Image;
    }

    public void setBus_Image(String bus_Image) {
        Bus_Image = bus_Image;
    }

    public String getBus_Type() {
        return Bus_Type;
    }

    public void setBus_Type(String bus_Type) {
        Bus_Type = bus_Type;
    }
}
