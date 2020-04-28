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
    
    
    private BTreeNode mRaiz = null;
    private int mK = 2;
    private int mAltura = 0;
    private ArrayList listaIngresados = new ArrayList();
    private ArrayList listaDatos = new ArrayList();

    
    
    public BTreeNode getRoot(){
        return mRaiz;
    }
    public String toDot(String x) {
        StringBuilder b = new StringBuilder();
        b.append("digraph g { \n graph[label=\"" + "Arbol B Categoria: " + x  + "\", labelloc=t, fontsize=20, compound=true]; node [shape=record];\n");
        b.append(mRaiz.toDot());
        b.append("}");
        return b.toString();
    }

    public BTree() {
    }

    public BTree(int k) {
        this.mK = k;
    }

    public BTree(BTreeNode pRaiz) {
        mK = pRaiz.getK();
        this.mRaiz = pRaiz;
        mAltura = 1;
    }

    public void insert(int i, Object obj) {
        Ordenable key = new LlaveEntero(i);
        listaDatos.add(obj);
        listaIngresados.add(i);
        if (this.mAltura == 0) {
            this.mRaiz = new BTreeNode(this.mK, key, obj);
            this.mAltura = 1;
            return;
        }

        Split splitted = insert(this.mRaiz, key, obj, 1);

        if (splitted != null) {
            BTreeNode ptr = this.mRaiz;
            this.mRaiz = new BTreeNode(this.mK, splitted.getLlave(), splitted.getDato());
            this.mRaiz.mPunteros[0] = ptr;
            this.mRaiz.mPunteros[1] = splitted.getPuntero();
            this.mAltura++;
        }
    }
    
    
    
    ArrayList temp = new ArrayList();
        
    public void Eliminar(int x) {
        Ordenable key = new LlaveEntero(x);
        if (listaIngresados.contains(x)) {
            ArrayList temp = listaDatos;
            mRaiz = null;
            mK = 2;
            mAltura = 0;
            listaIngresados = new ArrayList();
            listaDatos = new ArrayList();
            
            for (Object listaDato : temp) {
                Book b = (Book)listaDato;
                if (b.getIsbn() != x) {
                    insert(b.getIsbn(), b);
                }
            }    
        }
    }
    
    

    protected Split insert(BTreeNode node, Ordenable key, Object obj, int level) {

        Split splitted = null;
        BTreeNode ptr = null;

        int i = 0;
        while ((i < node.mB) && (key.mayorQue(node.mLlaves[i])))
            i++;

        if ((i < node.mB) && key.igualA(node.mLlaves[i])) {
            node.mDatos[i] = obj;
            return null;
        }

        if (level < this.mAltura) {

            splitted = insert(node.mPunteros[i], key, obj, level + 1);

            if (splitted == null)
                return null;
            else {
                key = splitted.mLlave;
                obj = splitted.mDato;
                ptr = splitted.mPuntero;
            }
        }

        i = node.mB - 1;
        while ((i >= 0) &&
               (node.mLlaves[i] == null || key.menorQue(node.mLlaves[i]))) {
            node.mLlaves[i + 1] = node.mLlaves[i];
            node.mDatos[i + 1] = node.mDatos[i];
            node.mPunteros[i + 2] = node.mPunteros[i + 1];
            i--;
        }

        node.mLlaves[i + 1] = key;
        node.mDatos[i + 1] = obj;
        if (splitted != null)
            node.mPunteros[i + 2] = splitted.mPuntero;
        node.mB++;

        if (node.mB > 2 * mK) {

            BTreeNode newnode = new BTreeNode(this.mK);
            newnode.mPunteros[this.mK] = node.mPunteros[node.mB];
            node.mPunteros[node.mB] = null;
            node.mB = this.mK + 1;
            for (i = 0; i < this.mK; i++) {
                newnode.mLlaves[i] = node.mLlaves[i + node.mB];
                node.mLlaves[i + node.mB] = null;
                newnode.mDatos[i] = node.mDatos[i + node.mB];
                node.mDatos[i + node.mB] = null;
                newnode.mPunteros[i] = node.mPunteros[i + node.mB];
                node.mPunteros[i + node.mB] = null;
            }
            node.mB--;

            splitted =
                    new Split(newnode, node.mLlaves[node.mB], node.mDatos[node.mB]);
            node.mLlaves[node.mB] = null;
            node.mDatos[node.mB] = null;
            newnode.mB = this.mK;
            node.mB = this.mK;

            return splitted;
        }

        return null;
    }

    public Object search(Ordenable key) {
        return search(key, mRaiz);
    }

    public Object search(Ordenable key, BTreeNode node) {

        if ((node == null) || (node.mB < 1)) {
            System.err.println("error");
            return null;
        }

        if (key.menorQue(node.mLlaves[0]))
            return search(key, node.mPunteros[0]);

        if (key.mayorQue(node.mLlaves[node.mB - 1]))
            return search(key, node.mPunteros[node.mB]);

        int i = 0;
        while ((i < node.mB - 1) && (key.mayorQue(node.mLlaves[i])))
            i++;

        if (key.igualA(node.mLlaves[i]))
            return node.mDatos[i];

        return search(key, node.mPunteros[i]);

    }


    public int getAltura() {
        return mAltura;
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
        
        Runtime.getRuntime().exec("dot -Tjpg -o BTree.png BTree.dot");

    }
    
}


class Split {
    BTreeNode mPuntero;
    Ordenable mLlave;
    Object mDato;

    public Split(BTreeNode pPuntero, Ordenable pLlave, Object pDato) {
        this.mPuntero = pPuntero;
        this.mLlave = pLlave;
        this.mDato = pDato;
    }

    public void setPuntero(BTreeNode mPuntero) {
        this.mPuntero = mPuntero;
    }

    public BTreeNode getPuntero() {
        return mPuntero;
    }

    public void setLlave(Ordenable mLlave) {
        this.mLlave = mLlave;
    }

    public Ordenable getLlave() {
        return mLlave;
    }

    public void setDato(Object mDato) {
        this.mDato = mDato;
    }

    public Object getDato() {
        return mDato;
    }
}


class LlaveEntero extends Ordenable {

    private Integer mLlave = null;

    public LlaveEntero(int pValor) {
        mLlave = new Integer(pValor);
    }

    public LlaveEntero(Integer pValor) {
        mLlave = pValor;
    }

    public Object getKey() {
        return mLlave;
    }
    
    public boolean igualA(Ordenable pObjeto) {
        return mLlave.equals(pObjeto.getKey());
    }

    public boolean menorQue(Ordenable pObjeto) {
        return mLlave.compareTo((Integer)pObjeto.getKey()) < 0;
    }
    
    public boolean mayorQue(Ordenable pObjeto) {
        return mLlave.compareTo((Integer)pObjeto.getKey()) > 0;
    }
    
    public boolean menorOIgualQue(Ordenable pObjeto) {
        return mLlave.compareTo((Integer)pObjeto.getKey()) <= 0;
    }
  
    public boolean mayorOIgualQue(Ordenable pObjeto) {
        return mLlave.compareTo((Integer)pObjeto.getKey()) >= 0;
    }
    
    public Ordenable minKey() {
        return new LlaveEntero(Integer.MIN_VALUE);
    }

}