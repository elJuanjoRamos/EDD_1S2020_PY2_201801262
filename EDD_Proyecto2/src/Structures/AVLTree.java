/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Structures;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import models.Book;
import models.Category;
import nodes.AVLNode;

/**
 *
 * @author Juan José Ramos
 */

public class AVLTree {

    public AVLNode root;
    public static int idCategory = 1;
    
    
    SimpleListTree inorden = new SimpleListTree();
    SimpleListTree posorden = new SimpleListTree();
    SimpleListTree preorden = new SimpleListTree();

        
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
    
    public AVLNode getNode(AVLNode N, String x) {
        if (N == null) {
            return null;
        } else {
            if (N.getCategory().getName().equals(x)) {
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

    public void Insert(String key, int id) {
        root = Insert(root, key, id);
    }
    public void Update(int key) {
        //root = Update(root, key);
    }
    public void Delete(String key) {
        root = Delete(root, key);
    }
    /*INSERTAR*/
    public AVLNode Insert(AVLNode node, String key, int id) {

        /*1. Perform the normal bst rotation*/
        if (node == null) {
            node = new AVLNode(new Category(idCategory, id, 0, key, false));
            idCategory++;
        } else {
            int compare = key.compareTo(node.getCategory().getName());  
            System.out.println(compare);
            if (compare < 0) {
                
                AVLNode a = Insert(node.getLeft(), key, id);
                node.setLeft(a);
            
            } else if (compare > 0) {
                
                AVLNode a = Insert(node.getRight(), key, id);
                
                node.setRight(a);
            } else {
                return node;
            }
            /*2. Update height of this ancestor node*/
            node.setHeight(1 + max(heigth(node.getLeft()), heigth(node.getRight())));

            /*3 get balance*/
            int balance = getBalance(node);

            //left left node
            if (balance > 1 && (key.compareTo(node.getLeft().getCategory().getName())) < 0) {
                return rightRotate(node);
            }
            //right right case
            if (balance < -1 && (key.compareTo(node.getRight().getCategory().getName())) > 0) {
                return leftRotate(node);
            }

            //left right case
            if (balance > 1 && (key.compareTo(node.getLeft().getCategory().getName())) > 0) {
                node.setLeft(leftRotate(node.getLeft()));
                return rightRotate(node);
            }

            //Right left case
            if (balance < -1 && (key.compareTo(node.getRight().getCategory().getName())) < 0) {
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
            int compare = key.compareTo(node.getCategory().getName());  
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
                    node.setObject(temp.getCategory());
                    node.setRight(Delete(node.getRight(), temp.getCategory().getName()));
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

    
    
       
       
    public void InOrder(AVLNode n)
    {
        if (n != null)
        {
            InOrder(n.getLeft());
            
            AVLNode node = n;
            
            int index = node.getIndex();
            String name = node.getCategory().getName();
            
    
            inorden.Insert(index, name);
            InOrder(n.getRight());
        }
    }   
    
    public void PreOrder(AVLNode n)
    {
        if (n != null) {
            preorden.Insert(n.getIndex(), n.getCategory().getName());
            PreOrder(n.getLeft());
            PreOrder(n.getRight());
        }
    }
    public void PostOrder(AVLNode n)
    {
        if (n != null) {
                PostOrder(n.getLeft());
                PostOrder(n.getRight());
                posorden.Insert(n.getIndex(), n.getCategory().getName());
        }
    }
    
    
    public void Print() throws IOException {
        root.Print();
    
        
        AVLNode temp = root;
        InOrder(temp);
        inorden.Print("InOrden");
        inorden.Clear();

        PreOrder(temp);
        preorden.Print("PreOren");
        preorden.Clear();
        
        PostOrder(temp);
        posorden.Print("PosOrden");
        posorden.Clear();


    
    }
    
    
    
    
    public void InsertB(String x, Book value){
        InsertB(value, x, root);
    }
    public void InsertB(Book value, String x, AVLNode node){
        if (node != null)
	{
            if (node.getCategory().getName().equals(x)) {
                node.getBtree().insert(value.getIsbn(), value);
                node.getCategory().setBooksQuantiy(node.getCategory().getBooksQuantiy() + 1);
                node.getCategory().setHaveBooks(true);
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
            if (node.getCategory().getName().equals(x)) {
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
            if (node.getCategory().getName().equals(y)) {
                try {
                    if (node.getBtree().getRoot() != null) {
                        if (node.getBtree().Delete(x)) {
                            node.getCategory().setBooksQuantiy(node.getCategory().getBooksQuantiy() - 1);
                            if (node.getBtree().getRoot() == null) {
                                node.getCategory().setHaveBooks(false);
                            }
                        }
                    }
                } catch (Exception e) {
                    
                }
            } else {
                DeleteB( x, y, node.getLeft());
                DeleteB( x, y, node.getRight());
            }
		
	}
    }
    
    
    ArrayList tempBook = new ArrayList();
    ArrayList tempCategory = new ArrayList();
    public ArrayList getBObjects(int x){
        tempBook.clear();
        if (root != null) {
            if (root.getBtree().getRoot() != null) {
                root.getBtree().getRoot().clearArray();        
            }
            getBObjects(x, root);

        }
        return tempBook;
        
    }
    
    public ArrayList getBGeneralObjects(){
        tempBook.clear();
        if (root != null) {
            if (root.getBtree().getRoot() != null) {
                root.getBtree().getRoot().clearArray();        
            }
            getBGeneralObjects(root);

        }
        return tempBook;
        
    }
    
    
    public ArrayList getCObjects(int x){
        tempCategory.clear();
        if (root != null) {
            getCObjects(x, root);
        }
        return tempCategory;
        
    }
    public ArrayList getCObjects(){
        tempCategory.clear();
        if (root != null) {
            getCObjects(root);
        }
        return tempCategory;
        
    }
    public boolean SearchKey(int k){
        boolean b =SearchKey(k, root); 
        return b;
    }
    
    public boolean SearchKey(int k, AVLNode node){
        if (node != null) {
            if (node.getBtree().getRoot() != null) {
                if (node.getBtree().getRoot().searchKey(k)) {
                    return true;
                }
            } else {
                return false;
            } 
            if (SearchKey(k, node.getLeft())) {
                return true;
            }
            if (SearchKey(k, node.getRight())) {
                return true;
            }
        } else {
            return false;
        }
        return false;
        
    }
    
    
    public void getBObjects(int x, AVLNode node){
        
        if (node != null) {
            if (node.getBtree().getRoot() != null) {
                
                node.getBtree().traverse(x);
                ArrayList ar = node.getBtree().getRoot().getArray();
                if (ar != null) {
                    for (Object object : ar) {
                        
                        if (!this.tempBook.contains(object)) {
                            this.tempBook.add((Book)object);    
                        }
                    }
                }
            }
            getBObjects(x, node.getLeft());
            getBObjects(x, node.getRight());

        } 
    }
    
    public void getBGeneralObjects(AVLNode node){
        
        if (node != null) {
            if (node.getBtree().getRoot() != null) {
                
                node.getBtree().traverse();
                ArrayList ar = node.getBtree().getRoot().getArray();
                if (ar != null) {
                    for (Object object : ar) {
                        
                        if (!this.tempBook.contains(object)) {
                            this.tempBook.add((Book)object);    
                        }
                    }
                }
            }
            getBGeneralObjects(node.getLeft());
            getBGeneralObjects(node.getRight());

        } 
    }
    public void getCObjects(int x, AVLNode node){
        
        if (node != null) {
            if (node.getCategory().getIdUser() == x) {
                tempCategory.add(node.getCategory());
            }
            getCObjects(x, node.getLeft());
            getCObjects(x, node.getRight());

        } 
    }
    public void getCObjects(AVLNode node){
        
        if (node != null) {
            tempCategory.add(node.getCategory().getName());
            getCObjects(node.getLeft());
            getCObjects(node.getRight());

        } 
    }
}
