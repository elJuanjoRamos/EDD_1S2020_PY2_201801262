/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Structures.AVLTree;
import Structures.BTree;
import Structures.HashTable;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
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
    BTree arbolb = new BTree();
    HashTable table = new HashTable(7);
    
    
    //VARIABLES GLOBALES AUXILIARES
    private ObservableList<User> users;

    public StructureController() {
        users = FXCollections.observableArrayList();
    }
    
    
    
    //Avl
    public void Insert(String k){
        arbol.Insert(k);
        
    }
    public void PrintAvl(){
        try {
            AVLNode a = arbol.getRoot();
            if (a == null) {
                System.out.println("null");
            } else {
                a.Print();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    public void Delete(String k){
        arbol.Delete(k);
    }
    
    
    //ARBOL B
    public void InsertB(String categoryName, int isbn, String tittle, String autor, String editorial, int year, ArrayList edition, ArrayList category, String languaje, int carnet){
        arbol.InsertB(categoryName, new Book(isbn, tittle, autor, editorial, year, edition, category, languaje, carnet));
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

}
