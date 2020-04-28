/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Juan José Ramos
 */

//clase estudiante
public class User {
    int carnet;
    String name;
    String lastname;
    String career;
    String password;

    public User() {
    }

    public User(int carnet, String name, String username, String career, String password) {
        this.carnet = carnet;
        this.name = name;
        this.lastname = username;
        this.career = career;
        this.password = password;
    }

    public int getCarnet() {
        return carnet;
    }

    public void setCarnet(int carnet) {
        this.carnet = carnet;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String username) {
        this.lastname = username;
    }

    public String getCareer() {
        return career;
    }

    public void setCareer(String career) {
        this.career = career;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
}
