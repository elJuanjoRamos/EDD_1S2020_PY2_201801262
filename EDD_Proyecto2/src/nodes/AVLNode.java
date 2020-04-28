/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodes;

import Structures.BTree;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Juan José Ramos
 */
public class AVLNode {

    static int correlative = 1;
    BTree btree;
    int height, index;
    String key;
    AVLNode left, right;

    public AVLNode() {

    }

    public AVLNode(String d) {
        this.key = d;
        this.height = 1;
        this.index = correlative++;
        this.left = null;
        this.right = null;
        this.btree = new BTree();
    }

    public BTree getBtree() {
        return btree;
    }

    public void setBtree(BTree btree) {
        this.btree = btree;
    }

    public void setKey(String k) {
        this.key = k;
    }

    public void setHeight(int h) {
        this.height = h;
    }

    public void setLeft(AVLNode l) {
        this.left = l;
    }

    public void setRight(AVLNode r) {
        this.right = r;
    }

    public String getKey() {
        return this.key;
    }

    public int getHeight() {
        return this.height;
    }

    public int getIndex() {
        return this.index;
    }

    public AVLNode getLeft() {
        return this.left;
    }

    public AVLNode getRight() {
        return this.right;
    }

    public void Print() throws IOException {
        String name = "Arbol AVL";
        String texto = "";
        texto = "digraph grafica{\n" + "rankdir=TB;" + "graph[label=\"" + name + "\", labelloc=t, fontsize=20, compound=true]\n" + "node [shape = record, style=filled, fillcolor=white];\n" + this.GetBody() + "}";

        try {
            File f = new File("AVLTree.dot");
            if(f.exists() && !f.isDirectory()) { 
                f.delete();
            }
            FileWriter myObj = new FileWriter("AVLTree.dot");
            myObj.write(texto);
            myObj.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Runtime.getRuntime().exec("dot -Tjpg -o AVLTree.png AVLTree.dot");

    }

    public String GetBody() {
        String etiqueta;
        if (left == null && right == null) {
            etiqueta = "nodo" + (index) + " [ label =\"" +   key + "\"];\n";
        } else {
           etiqueta = "nodo" + (index) + " [ label =\"" +   key + "\"];\n";
            
            //etiqueta = "nodo" + (index) + "[ label =\"" + key + "\"];\n";
        }
        if (left != null) {
            etiqueta = etiqueta + left.GetBody()
                    + "nodo" + (index) + "->nodo" + (left.getIndex()) + "\n";      }
        if (right != null) {
            etiqueta = etiqueta + right.GetBody()
                    + "nodo" + (index) + "->nodo" + (right.getIndex()) + "\n";
        }
        return etiqueta;
    }

    
}
