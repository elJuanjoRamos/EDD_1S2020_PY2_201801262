/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Structures;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import models.Book;
import nodes.AVLNode;

/**
 *
 * @author Juan José Ramos
 */

public class AVLTree {

    public AVLNode root;

    public AVLTree() {
        this.root = null;
    }

    int heigth(AVLNode N) {
        if (N == null) {
            return 0;
        } else {
            return N.getHeight();
        }
    }
    
    AVLNode getNode(AVLNode N, String x) {
        if (N == null) {
            return null;
        } else {
            if (N.getKey().equals(x)) {
                return N;
            } else {
                if (N.getLeft() != null) {
                    return getNode(N.getLeft(), x);
                }
                if (N.getRight() != null) {
                    return getNode(N.getRight(), x);
                }
            }
        }
        return null;
    }

    //funcion para determinarl el maximo de dos funciones
    int max(int a, int b) {
        return (a > b) ? a : b;
    }

    //Rotaciones
    AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.getLeft();
        AVLNode T2 = x.getRight();
        //Perform rotation
        x.setRight(y);
        y.setLeft(T2);
        //UPDATE ALTURA
        y.setHeight(max(heigth(y.getLeft()), heigth(y.getRight())) + 1);
        x.setHeight(max(heigth(x.getLeft()), heigth(x.getRight())) + 1);
        return x;
    }

    AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.getRight();
        AVLNode T2 = y.getLeft();
        //Perform rotation
        y.setLeft(x);
        x.setRight(T2);
        //UPDATE ALTURA
        x.setHeight(max(heigth(x.getLeft()), heigth(x.getRight())) + 1);
        y.setHeight(max(heigth(y.getLeft()), heigth(y.getRight())) + 1);
        return y;
    }

    int getBalance(AVLNode N) {
        if (N == null) {
            return 0;
        } else {
            return heigth(N.getLeft()) - heigth(N.getRight());
        }
    }

    public AVLNode getRoot() {
        return this.root;
    }

    public void Insert(String key) {
        root = Insert(root, key);
    }
    public void Update(int key) {
        //root = Update(root, key);
    }
    public void Delete(String key) {
        root = Delete(root, key);
    }
    /*INSERTAR*/
    public AVLNode Insert(AVLNode node, String key) {

        /*1. Perform the normal bst rotation*/
        if (node == null) {
            node = new AVLNode(key);
        } else {
            int compare = key.compareTo(node.getKey());  
            if (compare < 0) {
                
                AVLNode a = Insert(node.getLeft(), key);
                node.setLeft(a);
            
            } else if (compare > 0) {
                
                AVLNode a = Insert(node.getRight(), key);
                
                node.setRight(a);
            } else {
                return node;
            }
            /*2. Update height of this ancestor node*/
            node.setHeight(1 + max(heigth(node.getLeft()), heigth(node.getRight())));

            /*3 get balance*/
            int balance = getBalance(node);

            //left left node
            if (balance > 1 && compare < 0) {
                return rightRotate(node);
            }
            //right right case
            if (balance < -1 && compare > 0) {
                return leftRotate(node);
            }

            //left right case
            if (balance > 1 && compare > 0) {
                node.setLeft(leftRotate(node.getLeft()));
                return rightRotate(node);
            }

            //Right left case
            if (balance < -1 && compare < 0) {
                node.setRight(rightRotate(node.getRight()));
                return leftRotate(node);
            }
        }
        return node;
    }

    
    /*ELIMINAR*/
    //obtiene el valor minimo de un arbol dado
    AVLNode minValue(AVLNode node){
        AVLNode current = node;
        /*loop down to fin de leftmost leeaf*/
        while(current.getLeft() != null){
            current = current.getLeft();
        }
        return current;
    }
       public AVLNode Delete(AVLNode node, String key) {

        /*1. Perform the normal bst rotation*/
        if (node == null) {
            return node;
        } else {
            int compare = key.compareTo(node.getKey());  
            if (compare < 0) {
                AVLNode a = Delete(node.getLeft(), key);
                node.setLeft(a);
            } else if (compare > 0) {
                AVLNode a = Delete(node.getRight(), key);
                node.setRight(a);
            } else {
                if ((node.getLeft() == null) || (node.getRight() == null)) {
                    AVLNode temp = null;
                    if (temp == node.getLeft()) {
                        temp = node.getRight();
                    } else {
                        temp = node.getLeft();
                    }
                    
                    //no cilds case
                    if (temp == null) {
                        temp = node;
                        node = null; 
                    } else {
                        node = temp;
                    }
                }else {
                    AVLNode temp = minValue(node.getRight());
                    node.setKey(temp.getKey());
                    node.setRight(Delete(node.getRight(), temp.getKey()));
                }
            }
            
            if (node == null) {
                return node;
            }
            /*2. Update height of this ancestor node*/
            node.setHeight(1 + max(heigth(node.getLeft()), heigth(node.getRight())));

            /*3 get balance*/
            int balance = getBalance(node);

            //left left node
            if (balance > 1 && getBalance(node.getLeft()) >= 0) {
                return rightRotate(node);
            }
            if (balance > 1 && getBalance(node.getLeft()) < 0) {
                node.setLeft(leftRotate(node.getLeft()));
                return rightRotate(node);
            }

            if (balance < -1 && getBalance(node.getRight()) <= 0) {
                return leftRotate(node);
            }

            //Right left case
            if (balance < -1 && getBalance(node.getRight()) > 0) {
                node.setRight(rightRotate(node.getRight()));
                return leftRotate(node);
            }
        }
        return node;
    }

    
    
    
    public void Print() throws IOException {
        root.Print();
    }
    
    
    public void InsertB(String x, Book value){
        InsertB(value, x, root);
    }
    public void InsertB(Book value, String x, AVLNode node){
        if (node != null)
	{
            if (node.getKey().equals(x)) {
                node.getBtree().insert(value.getIsbn(), value);
            } else {
                InsertB( value, x, node.getLeft());
                InsertB( value, x, node.getRight());
            }
		
	}
    }
    public void PrintB(String x){
        PrintB(x, root);
    }
    
    public void PrintB(String x, AVLNode node){
        if (node != null)
	{
            if (node.getKey().equals(x)) {
                try {
                    if (node.getBtree().getRoot() != null) {
                       node.getBtree().PrintBTree(x);                        
                    }
                } catch (Exception e) {
                    
                }
            } else {
                PrintB( x, node.getLeft());
                PrintB( x, node.getRight());
            }
		
	}
    }
    
    public void DeleteB(int x, String y){
        DeleteB(x, y, root);
    }
    
    public void DeleteB(int x, String y, AVLNode node){
        if (node != null)
	{
            if (node.getKey().equals(y)) {
                try {
                    if (node.getBtree().getRoot() != null) {
                       node.getBtree().Eliminar(x);                        
                    }
                } catch (Exception e) {
                    
                }
            } else {
                DeleteB( x, y, node.getLeft());
                DeleteB( x, y, node.getRight());
            }
		
	}
    }
}
