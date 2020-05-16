/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Juan José Ramos
 */
public class Book  implements Serializable {
    int isbn;
    String tittle;
    String autor;
    String category;
    int year;
    ArrayList edition;
    ArrayList editorial;
    String languaje;
    int carnet;

    public Book(int isbn, String tittle, String autor, String category, int year, ArrayList edition, ArrayList editorial, String languaje, int carnet) {
        this.isbn = isbn;
        this.tittle = tittle;
        this.autor = autor;
        this.category = category;
        this.year = year;
        this.edition = edition;
        this.editorial = editorial;
        this.languaje = languaje;
        this.carnet = carnet;
    }
    
    public Book(int isbn, String category) {
        this.isbn = isbn;
        this.category = category;
    }

    public int getIsbn() {
        return isbn;
    }

    public void setIsbn(int isbn) {
        this.isbn = isbn;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public ArrayList getEdition() {
        return edition;
    }

    public void setEdition(ArrayList edition) {
        this.edition = edition;
    }

    public ArrayList getEditorial() {
        return editorial;
    }

    public void setEditorial(ArrayList editorial) {
        this.editorial = editorial;
    }

    public String getLanguaje() {
        return languaje;
    }

    public void setLanguaje(String languaje) {
        this.languaje = languaje;
    }

    public int getCarnet() {
        return carnet;
    }

    public void setCarnet(int carnet) {
        this.carnet = carnet;
    }

    
    
    
    
}
