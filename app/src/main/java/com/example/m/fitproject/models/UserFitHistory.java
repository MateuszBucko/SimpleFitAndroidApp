package com.example.m.fitproject.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.Date;
import java.util.List;

/**
 * Created by M on 27.12.2016.
 */
@Table(name = "UserFitHistory")
public class UserFitHistory extends Model {

    @Column(name = "Weight")
    private int weight;
    @Column(name = "Height")
    private int height;
    @Column(name = "BMI")
    private double bmi;
    @Column(name = "Photo")
    private String photo;
    @Column(name = "Date")
    private Date date;

    @Column(name = "user")
    private User user;

    public UserFitHistory(int weight, int height, double bmi, User user) {
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
        this.user = user;
    }

    public UserFitHistory() {
        super();
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public double getBmi() {
        return bmi;
    }

    public void setBmi(double bmi) {
        this.bmi = bmi;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public static List<UserFitHistory> getAll()
    {
        return new Select().from(UserFitHistory.class).execute();
    }


}
