/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Structures.AVLTree;
import Structures.BTree;
import Structures.DoubleList;
import Structures.HashTable;
import Structures.SimpleList;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import models.*;
import nodes.*;

/**
 *
 * @author Juan José Ramos
 */
public class StructureController {

    public static StructureController instancia;

    public static StructureController getInstancia() {
        if (instancia == null) {
            instancia = new StructureController();
        }
        return instancia;
    }
    
    
    //VARIABLES GLOBALES DE ESTRUCTURAS
    AVLTree arbol = new AVLTree();
    HashTable table = new HashTable(7);
    SimpleList simpleList = new SimpleList();
    DoubleList dbList = new DoubleList();
    
    //VARIABLES GLOBALES AUXILIARES
    private ObservableList<User> users;
    private ObservableList<Book> books;
    private ObservableList<Object> category;
    private ObservableList<Object> computers;
    private ObservableList<Book> generalBook;
    
    
    public StructureController() {
        users = FXCollections.observableArrayList();
        books = FXCollections.observableArrayList();
        category = FXCollections.observableArrayList();
        computers = FXCollections.observableArrayList();
        generalBook = FXCollections.observableArrayList();
        InsertTable(1, "Usuario1", "U", "U", "123");
        InsertTable(2, "Usuario2", "U", "U", "123");
    }

    public SimpleList getSimpleList() {
        return simpleList;
    }
    
    //Avl
    public void InsertAVL(String k, int id){
        arbol.Insert(k, id);   
    }
    public AVLNode searchAVL(String k) {
        return arbol.getNode(arbol.getRoot(), k);
    }
    public void PrintAvl(){
        try {
            arbol.Print(); 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void DeleteAVL(String k){
        arbol.Delete(k);
    }
    public ObservableList<Book> getMyBooks(int carnet){
        this.books.clear();
        System.out.println(carnet);
        ArrayList ar = arbol.getBObjects(carnet);
        System.out.println(ar);
        if (ar != null) {
            for (Object o : ar) {
                this.books.add((Book)o);
            }
        } else {
            return null;
        }
        
        return this.books;
    }
    public ObservableList<Book> getGeneralBooks(){
        this.generalBook.clear();
        ArrayList ar = arbol.getBGeneralObjects();
        if (ar != null) {
            for (Object o : ar) {
                this.generalBook.add((Book)o);
            }
        } else {
            return null;
        }
        
        return this.generalBook;
    }
    
    public ObservableList<Object> getMyCategory(int carnet){
        this.category.clear();
        
        ArrayList ar = arbol.getCObjects(carnet);
        
        if (ar != null) {
            for (Object o : ar) {
                this.category.add((Category)o);
            }
        } else {
            return null;
        }
        
        return this.category;
    }
    public ObservableList<Object> getMyCategory(){
        this.category.clear();
        ArrayList ar = arbol.getCObjects();
        if (ar != null) {
            System.out.println("llego");
            for (Object o : ar) {
                this.category.add(o.toString());
            }
        } else {
            return null;
        }
        return this.category;
    }
    
    //ARBOL B
    public void InsertB(String categoryName, int isbn, String tittle, String autor, int year, ArrayList edition, ArrayList editorial, String languaje, int carnet){
        
        if (searchAVL(categoryName) == null) {
            
            InsertAVL(categoryName, carnet);
        }
        Book b = new Book(isbn, tittle, autor, categoryName, year, edition, editorial, languaje, carnet);
        arbol.InsertB(categoryName, b);
    }
    
    public boolean searchKey(int key) {
        
        return arbol.SearchKey(key);
        
        
    }
    
    public void PrintB(String x){
        try {
            arbol.PrintB(x);
        } catch (Exception e) {
            System.out.println(e);
        }
        
    }
    public void DeleteB(int key, String category){
        arbol.DeleteB(key, category);
        
    }
    
    //HASH TABLE
    
    public void InsertTable(int carnet, String name, String lastname, String carrer, String password){        
        User u = new User(carnet, name, lastname, carrer, MD5(password));
        table.add(carnet, u); 
    }
    public void DeleteTable(int key){
        table.remove(key);
    }
    
    public void UpdateTable(int carnet, String name, String lastname, String carrer, String password){
        User u = new User(carnet, name, lastname, carrer, MD5(password));
        table.update(carnet, u);
    }
    public void PrintTable() {
        try {
            table.Print();    
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public User searchUser(int carnet){
        return (User)table.get(carnet);
    }
    public User searchUser(int carnet, String password){
            return (User)table.get(carnet, MD5(password));
    }
    
    public ObservableList<User> getUsers(){
        this.users.clear();
        for (Object object : table.getHashTable()) {
            if (object != null) {
                HashNode hash = (HashNode)object;
                User u = (User)(hash).getValue();
                this.users.add(u);
                if (hash.getNext() != null) {
                    HashNode temp = hash.getNext();
                    while (temp != null) {
                        users.add(((User)temp.getValue()));
                        temp = temp.getNext();
                    }
                }
            }
        }
        return this.users;
    } 
    
    //Simple List
    public void InsertSimple(String ip, int port){
        simpleList.add(ip, port);
    }
    
    public void DeleteSimple(String ip){
       simpleList.delete(ip);
    }
    public void PrintSimple(){
        try {
            simpleList.Print();
        } catch (Exception e) {
            System.out.println(e);
        }        
    }
    
    public ObservableList<Object> getComputers() {
        this.computers.clear();
        ArrayList ar = simpleList.getBObjects();
        if (ar != null) {
            for (Object o : ar) {
                this.computers.add(o.toString());
            }
        } else {
            return null;
        }
        return this.computers;
    }
    
    public String MD5(String md5) {
        try {
             java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
             byte[] array = md.digest(md5.getBytes());
             StringBuffer sb = new StringBuffer();
             for (int i = 0; i < array.length; ++i) {
               sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
            }
             return sb.toString();
         } catch (java.security.NoSuchAlgorithmException e) {
         }
         return null;
     }
    
    public void InsertDoubleList(String ip, Object port) {
        dbList.addLastNode(ip, port);
    }

    
    
    public void PrintDouble(){
        try {
            dbList.Print();
        } catch (Exception e) {
            System.out.println(e);
        }        
    }

}
