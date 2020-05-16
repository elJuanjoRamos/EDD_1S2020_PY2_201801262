/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Structures;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import models.Book;
import nodes.*;
/**
 *
 * @author Juan José Ramos
 */
public class BTree {
    
    private BTreeNode root = null;
    private int mK = 2;
    private int rootHeight = 0;
    private ArrayList keysList = new ArrayList();
    private ArrayList dataList = new ArrayList();
    
    
    
    public BTreeNode getRoot(){
        return root;
    }
    public int getQuantityKeys(){
        return keysList.size();
    }
    public String toDot(String x) {
        StringBuilder b = new StringBuilder();
        b.append("digraph g { \n graph[label=\"" + "Arbol B Categoria: " + x  + "\", labelloc=t, fontsize=20, compound=true]; node [shape=record];\n");
        b.append(root.toDot());
        b.append("}");
        return b.toString();
    }

    public BTree() {
    }

    public BTree(int k) {
        this.mK = k;
    }
    public BTree(BTreeNode r) {
        mK = r.getK();
        this.root = r;
        rootHeight = 1;
    }
    
    //Recorrido del arbol b
    public void traverse(int x){
        this.root.traverse(x);
    }
    //Busca una llave en el arbol b
    public boolean searchB(int x){
        return this.root.searchKey(x);
    }
    
    

    public void insert(int i, Object obj) {
        dataList.add(obj);
        keysList.add(i);
        if (this.rootHeight == 0) {
            this.root = new BTreeNode(this.mK, i, obj);
            this.rootHeight = 1;
            return;
        }

        Split splitted = insert(this.root, i, obj, 1);

        if (splitted != null) {
            BTreeNode ptr = this.root;
            this.root = new BTreeNode(this.mK, splitted.getKey(), splitted.getData());
            this.root.pointers[0] = ptr;
            this.root.pointers[1] = splitted.getPointer();
            this.rootHeight++;
        }
    }
           
    public boolean Delete(int x) {
        if (keysList.contains(x)) {
            ArrayList temp = dataList;
            root = null;
            mK = 2;
            rootHeight = 0;
            keysList = new ArrayList();
            dataList = new ArrayList();
            
            for (Object data : temp) {
                Book b = (Book)data;
                if (b.getIsbn() != x) {
                    insert(b.getIsbn(), b);
                }
            }
            return true;
        }
        return false;
    }
    
    

    public Split insert(BTreeNode node, int key, Object obj, int level) {

        Split splitted = null;
        BTreeNode ptr = null;

        int i = 0;
        while ((i < node.mB) && (key > node.keys[i]))
            i++;

        if ((i < node.mB) && key == node.keys[i]) {
            node.datas[i] = obj;
            return null;
        }

        if (level < this.rootHeight) {

            splitted = insert(node.pointers[i], key, obj, level + 1);

            if (splitted == null)
                return null;
            else {
                key = splitted.getKey();
                obj = splitted.getData();
                ptr = splitted.getPointer();
            }
        }

        i = node.mB - 1;
        while ((i >= 0) && (node.keys[i] == 0 || key < node.keys[i] )) {
            node.keys[i + 1] = node.keys[i];
            node.datas[i + 1] = node.datas[i];
            node.pointers[i + 2] = node.pointers[i + 1];
            i--;
        }

        node.keys[i + 1] = key;
        node.datas[i + 1] = obj;
        if (splitted != null)
            node.pointers[i + 2] = splitted.getPointer();
        node.mB++;

        if (node.mB > 2 * mK) {

            BTreeNode newnode = new BTreeNode(this.mK);
            newnode.pointers[this.mK] = node.pointers[node.mB];
            node.pointers[node.mB] = null;
            node.mB = this.mK + 1;
            for (i = 0; i < this.mK; i++) {
                newnode.keys[i] = node.keys[i + node.mB];
                node.keys[i + node.mB] = 0;
                newnode.datas[i] = node.datas[i + node.mB];
                node.datas[i + node.mB] = null;
                newnode.pointers[i] = node.pointers[i + node.mB];
                node.pointers[i + node.mB] = null;
            }
            node.mB--;

            splitted = new Split(newnode, node.keys[node.mB], node.datas[node.mB]);
            node.keys[node.mB] = 0;
            node.datas[node.mB] = null;
            newnode.mB = this.mK;
            node.mB = this.mK;

            return splitted;
        }

        return null;
    }

    public Object search(int key) {
        return search(key, root);
    }

    public Object search(int key, BTreeNode node) {

        if ((node == null) || (node.mB < 1)) {
            System.err.println("error");
            return null;
        }

        if (key < node.keys[0])
            return search(key, node.pointers[0]);

        if (key > node.keys[node.mB - 1])
            return search(key, node.pointers[node.mB]);

        int i = 0;
        while ((i < node.mB - 1) && (key > node.keys[i]))
            i++;

        if (key == node.keys[i])
            return node.datas[i];
        return search(key, node.pointers[i]);

    }


    public int getAltura() {
        return rootHeight;
    }
    
    
    public void PrintBTree(String x) throws IOException {
        try {

            FileWriter f = new FileWriter("BTree.dot");

            f.write(toDot(x));

            f.close();

        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
                
        try {
            Thread.sleep(1000);
        } catch (Exception e) {
            // TODO: Add catch code
            e.printStackTrace();
        }
        
        Runtime.getRuntime().exec("dot -Tjpg -o src/resources/img/BTree.png BTree.dot");

    }
    
}


class Split {
    BTreeNode pointer;
    int key;
    Object data;

    public Split(BTreeNode p, int l, Object d) {
        this.pointer = p;
        this.key = l;
        this.data = d;
    }

    public void setPointer(BTreeNode p) {
        this.pointer = p;
    }

    public BTreeNode getPointer() {
        return pointer;
    }

    public void setKey(int k) {
        this.key = k;
    }

    public int getKey() {
        return key;
    }

    public void setData(Object d) {
        this.data = d;
    }

    public Object getData() {
        return data;
    }
}
