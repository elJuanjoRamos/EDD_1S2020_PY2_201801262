/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.util.ArrayList;

/**
 *
 * @author Juan José Ramos
 */
public class Book {
    int isbn;
    String tittle;
    String autor;
    String editorial;
    int year;
    ArrayList edition;
    ArrayList category;
    String languaje;
    int carnet;

    public Book(int isbn, String tittle, String autor, String editorial, int year, ArrayList edition, ArrayList category, String languaje, int carnet) {
        this.isbn = isbn;
        this.tittle = tittle;
        this.autor = autor;
        this.editorial = editorial;
        this.year = year;
        this.edition = edition;
        this.category = category;
        this.languaje = languaje;
        this.carnet = carnet;
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

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
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

    public ArrayList getCategory() {
        return category;
    }

    public void setCategory(ArrayList category) {
        this.category = category;
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
