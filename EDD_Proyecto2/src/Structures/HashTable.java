/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Structures;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import models.User;
import nodes.HashNode;

/**
 *
 * @author Juan José Ramos
 */
public class HashTable<K, Object> {
    
    
    
    // hashTable is used to store array of chains 
    private ArrayList<HashNode<K, Object>> hashTable; 
  
    // Current capacity of array list 
    private int numBuckets; 
  
    // Current size of array list 
    private int size; 
  
    // Constructor (Initializes capacity, size and 
    // empty chains. 
    public HashTable(int n) 
    { 
        hashTable = new ArrayList<>(); 
        numBuckets = n; 
        size = 0; 
  
        // Create empty chains 
        for (int i = 0; i < numBuckets; i++) 
            hashTable.add(null); 
    } 
  
    public int size() { return size; } 
    public boolean isEmpty() { return size() == 0; } 
  
    // This implements hash function to find index 
    // for a key 
    private int getHashFunction(K key) 
    { 
        int hashCode = key.hashCode(); 
        int index = hashCode % numBuckets; 
        return index; 
    } 
  
    // Metodo Eliminar
    public Object remove(K key) 
    { 
        // Aplique la función hash para encontrar el índice para la clave dada 
        int index = getHashFunction(key); 
  
        // Get head of chain 
        HashNode<K, Object> head = hashTable.get(index); 
  
        //Buscar el key en la lista enlazada
        HashNode<K, Object> prev = null; 
        while (head != null) 
        { 
            // Llave encontrada
            if (head.getKey().equals(key)){
                break; 
            }
            prev = head; 
            head = head.getNext(); 
        } 
  
        // Si la llave no se encuentra 
        if (head == null) 
            return null; 
  
        // Reducir size
        size--; 
  
        // Remover Key
        if (prev != null) {
            prev.setNext(head.getNext());
        }else {
            hashTable.set(index, head.getNext()); 
        }
        return head.getValue(); 
    } 
  
    //Metodo actualizar
    public void update(K key, Object o){
        // Aplique la función hash para encontrar el índice para la clave dada 
        int index = getHashFunction(key); 
  
        // Get head of chain 
        HashNode<K, Object> head = hashTable.get(index); 
  
        //Buscar el key en la lista enlazada
        HashNode<K, Object> prev = null; 
        while (head != null) 
        { 
            // Llave encontrada
            if (head.getKey().equals(key)){
                break; 
            }
            prev = head; 
            head = head.getNext(); 
        } 
  
        if (head != null) {
            head.setValue(o);
        }
        
        
        
    }
    
    // Returns value for a key 
    public Object get(K key) 
    { 
        // Find head of chain for given key 
        int bucketIndex = getHashFunction(key); 
        HashNode<K, Object> head = hashTable.get(bucketIndex); 
  
        // Search key in chain 
        while (head != null) 
        { 
            if (head.getKey().equals(key)) 
                return head.getValue(); 
            head = head.getNext(); 
        } 
  
        // If key not found 
        return null; 
    }
    public Object get(K key, String pass) 
    { 
        // Find head of chain for given key 
        int bucketIndex = getHashFunction(key); 
        HashNode<K, Object> head = hashTable.get(bucketIndex); 
  
        // Search key in chain 
        while (head != null) 
        { 
            if (head.getKey().equals(key) && ((User)head.getValue()).getPassword().equals(pass) ) 
                return head.getValue(); 
            head = head.getNext(); 
        } 
  
        // If key not found 
        return null; 
    }
  
    // Adds a key value pair to hash 
    public void add(K key, Object value) 
    { 
        // Find head of chain for given key 
        int bucketIndex = getHashFunction(key); 
        HashNode<K, Object> head = hashTable.get(bucketIndex); 
  
        // Check if key is already present 
        while (head != null) 
        { 
            if (head.getKey().equals(key)) 
            { 
                head.setValue(value); 
                return; 
            } 
            head = head.getNext(); 
        } 
  
        // Insert key in chain 
        size++; 
        head = hashTable.get(bucketIndex); 
        HashNode<K, Object> newNode = new HashNode<K, Object>(key, value); 
        newNode.setNext(head); 
        hashTable.set(bucketIndex, newNode); 
 
    }
    
    public ArrayList<HashNode<K, Object>> getHashTable(){
        return this.hashTable;
    }
    
    public void Print() throws IOException {
        String name = "Usuarios";
        String texto = "";
        
        //
        //texto = "digraph grafica{\n" + "rankdir=TB;" + "graph[label=\"" + name + "\", labelloc=t, fontsize=20, compound=true]\n" + "node [shape=plaintext, fontcolor=red, fontsize=18];\n\"Pointers:\" -> \"Values:\" -> \"Indices:\" [color=white];" + this.GetBody() + "\n\n}";
        texto = "digraph grafica{\n" + "graph[label=\"Usuarios\", labelloc=t, fontsize=20, compound=true];\nrankdir = LR;\nnode [shape=record];\nsplines=false; " + GetBody() + "}";

        try {
            File f = new File("HashTable.dot");
            if(f.exists() && !f.isDirectory()) { 
                f.delete();
            }
            FileWriter myObj = new FileWriter("HashTable.dot");
            myObj.write(texto);
            myObj.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Runtime.getRuntime().exec("dot -Tjpg -o src/resources/img/HashTable.png HashTable.dot");

    }
    
    public String GetBody(){
       
        String hasht = "hashTable [label=\"";
        String hashNext = "";
        for (int i = 0; i < hashTable.size(); i++) {
            HashNode<K, Object> hashNode = hashTable.get(i);
            
            
             if (hashNode != null) {
                 User u = ((User)hashNode.getValue());
                if (i+1 != hashTable.size()) {
                    
                    hasht = hasht + "<f" + i + "> Indice: " + i + "\\lNombre: " + u.getName() +"\\lApellido: " + u.getLastName() + "\\lCarnet: " + u.getCarnet() + "\\lCarrera: " + u.getCareer() + "|";
                    
                } else {
                    hasht = hasht + "<f" + i + "> Indice: " + i + "\\lNombre: " + u.getName() +"\\lApellido: " + u.getLastName() + "\\lCarnet: " + u.getCarnet() + "\\lCarrera: " + u.getCareer() ;
                }
                
                HashNode<K, Object> temp = hashNode.getNext();
                
                 if (temp != null) {
                     hashNext = hashNext + "node_" + i +"_" + temp.getKey() + " [label=\"<f0> Indice: " + i + "\\lNombre: " +  u.getName() +"\\lApellido: " + u.getLastName()+ "\\lCarnet: " + u.getCarnet() + "\\lCarrera: " + u.getCareer() + "\" ];\n";
                     hashNext = hashNext+ "hashTable:f"+ i + "-> node_" + i + "_" + temp.getKey()+":f0;\n";
                    
                     while (temp != null) {
                         HashNode<K, Object> temp2 = temp;
                         User u2 = (User)temp2.getValue();
                         hashNext = hashNext + "node_" + i +"_" + temp.getKey() + " [label=\"<f0> Indice: " + i + "\\lNombre: " +   u2.getName() +"\\lApellido: " + u2.getLastName()+ "\\lCarnet: " + u2.getCarnet() + "\\lCarrera: " + u2.getCareer() + "\" ];\n";
                         temp = temp.getNext();
                         
                         if (temp != null) {
                             
                             hashNext = hashNext + "node_" + i + "_" + temp2.getKey() + ":f0 -> node_" + i + "_" + temp.getKey() + ":f0;\n";
                             
                         } 
                     }                   
                 }                
            } else {
                 if (i+1 != hashTable.size()) {
                    hasht = hasht + "<f" + i + ">" + "NULL" + "|";
                } else {
                    hasht = hasht + "<f" + i + ">" + "NULL";
                 }
            }
        }
        hasht = hasht + "\"];\n";
        
        return hasht + hashNext;
       
        
    }

   
}
