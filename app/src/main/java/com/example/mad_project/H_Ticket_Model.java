package com.example.mad_project;

public class H_Ticket_Model {

    String name, gender, cnic, seat, fromto, datetime, fare, type, image, user_id;

    public H_Ticket_Model()
    {

    }

    public H_Ticket_Model(String name, String gender, String cnic, String seat, String fromto, String datetime, String fare, String type, String image, String user_id) {
        this.name = name;
        this.gender = gender;
        this.cnic = cnic;
        this.seat = seat;
        this.fromto = fromto;
        this.datetime = datetime;
        this.fare = fare;
        this.type = type;
        this.image = image;
        this.user_id = user_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCnic() {
        return cnic;
    }

    public void setCnic(String cnic) {
        this.cnic = cnic;
    }

    public String getSeat() {
        return seat;
    }

    public void setSeat(String seat) {
        this.seat = seat;
    }

    public String getFromto() {
        return fromto;
    }

    public void setFromto(String fromto) {
        this.fromto = fromto;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }
}
