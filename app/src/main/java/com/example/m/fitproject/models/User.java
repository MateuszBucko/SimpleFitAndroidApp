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
public class User extends Model{
    @Column(name = "Username")
    private String username;
    @Column(name = "Password")
    private  String password;

    @Column(name="Startweight")
    private int startWeight;

    @Column(name="Height")
    private int height;


    @Column(name = "UserFitHistory")
    public List<UserFitHistory> userFitHistory;

    public User() {
        super();
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

    public static List<User> getAllUsers()
    {
        return new Select().from(User.class).execute();
    }

    public static User getUser(String username,String password)
    {
        return new Select()
                .from(User.class)
                .where("Username = ?",username)
                .where("Password = ?",password)
                .orderBy("RANDOM()")
                .executeSingle();
    }


}
