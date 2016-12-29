package com.example.m.fitproject.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by M on 27.12.2016.
 */
@Table(name = "User")
public class User extends Model {

    @Column(name = "Username")
    private String username;
    @Column(name = "Password")
    private String password;

    @Column(name = "Startweight")
    private int startWeight;

    @Column(name = "Height")
    private int height;


    public User() {
        super();
    }

    public User(String username, String password, int startWeight, int height) {
        this.username = username;
        this.password = password;
        this.startWeight = startWeight;
        this.height = height;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getStartWeight() {
        return startWeight;
    }

    public void setStartWeight(int startWeight) {
        this.startWeight = startWeight;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }


    public static List<User> getAllUsers() {
        return new Select().from(User.class).execute();
    }

    public static User getUser(String username, String password) {
        return new Select()
                .from(User.class)
                .where("Username = ?", username)
                .where("Password = ?", password)
                .orderBy("RANDOM()")
                .executeSingle();
    }
    public static User getUser(Long id) {
        return new Select()
                .from(User.class)
                .where("Id = ?", id)
                .executeSingle();
    }

    public List<UserFitHistory> getUserFitHistory() {
        return getMany(UserFitHistory.class, "User");
    }

    public UserFitHistory getLastestRecord() {
        List<UserFitHistory> userFitHistories = getUserFitHistory();
        UserFitHistory finalhistory = null;
        if (userFitHistories != null) {
            if(userFitHistories.size()>0) {
                finalhistory = userFitHistories.get(0);
                for (UserFitHistory ufh : userFitHistories) {
                    if (ufh.getDate().after(finalhistory.getDate())) {
                        finalhistory = ufh;
                    }
                }
            }
        }
        return finalhistory;
    }


}
