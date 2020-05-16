/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;

/**
 *
 * @author Juan José Ramos
 */
public class Category implements Serializable{
    int id;
    int idUser;
    int booksQuantiy;
    String name;
    boolean haveBooks;

    public Category(int id, int idUser, int booksQuantiy, String name, boolean haveBooks) {
        this.id = id;
        this.idUser = idUser;
        this.booksQuantiy = booksQuantiy;
        this.name = name;
        this.haveBooks = haveBooks;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getBooksQuantiy() {
        return booksQuantiy;
    }

    public void setBooksQuantiy(int booksQuantiy) {
        this.booksQuantiy = booksQuantiy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isHaveBooks() {
        return haveBooks;
    }

    public void setHaveBooks(boolean haveBooks) {
        this.haveBooks = haveBooks;
    }
     
    
    
     
}
