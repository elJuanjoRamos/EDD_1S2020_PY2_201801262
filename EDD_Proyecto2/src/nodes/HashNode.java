/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodes;

/**
 *
 * @author Juan José Ramos
 */
public class HashNode<K, Object> 
{ 
    K key; 
    Object value; 
  
    // Reference to next node 
    HashNode<K, Object> next; 
  
    // Constructor 
    public HashNode(K key, Object value) 
    { 
        this.key = key; 
        this.value = value;
        next = null;
    }

    public HashNode() {
    }
   
    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public HashNode<K, Object> getNext() {
        return next;
    }

    public void setNext(HashNode<K, Object> next) {
        this.next = next;
    }
    
    
    
} 
  
