package com.example.m.fitproject.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * Created by M on 27.12.2016.
 */
@Table(name = "UserFitHistory")
public class UserFitHistory extends Model{
    @Column(name = "Weight")
    private int weight;
    @Column(name = "Height")
    private int height;
    @Column(name = "BMI")
    private double bmi;

    @Column(name = "User")
    private User user;

    public UserFitHistory(int weight, int height, double bmi) {
        this.weight = weight;
        this.height = height;
        this.bmi = bmi;
    }

    public UserFitHistory()
    {
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
}
