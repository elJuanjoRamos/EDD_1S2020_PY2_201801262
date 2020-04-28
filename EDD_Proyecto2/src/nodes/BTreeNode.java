/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodes;

import models.Book;

/**
 *
 * @author Juan José Ramos
 */
public class BTreeNode {

    
    public int mK;
    public int mB;
    public Ordenable[] mLlaves;
    public Object[] mDatos;
    public BTreeNode[] mPunteros;
    
    private static int numeroDeNodo = 1;
    
    public String getDotName() {
        return "Nodo" + this.hashCode();
    }
    
    public String toDot(  )  {
        
        StringBuilder b = new StringBuilder();
        
        b.append( getDotName() );
        b.append("[label=\"<P0>");
        Book book = (Book)mDatos[0];
        for( int i = 0; i < mB; i++ ) {
             
            b.append( "| ISBN:" +  mLlaves[i].getKey().toString() + "\\lNombre:" + book.getTittle() );
            b.append( "|<P" + (i+1) + ">" );                
        }
        
        b.append("\"];\n");
        
        for( int i = 0; i <= mB ; i++ ) {
            if( mPunteros[i] != null )   {
                b.append( mPunteros[i].toDot() );
                b.append( getDotName() + ":P" + i + " -> " + mPunteros[i].getDotName() + ";\n" );
            }
        }
        
        return b.toString();
        
    }
    
    public BTreeNode(int pK) {
        this.mK = pK;
        mB = 0;
        mLlaves = new Ordenable[2 * pK + 1];
        mDatos = new Object[2 * pK + 1];
        mPunteros = new BTreeNode[(2 * pK) + 2];
    }

    public BTreeNode(int pK, Ordenable pLlave, Object pDato) {
        this(pK);
        mB = 1;
        mLlaves[0] = pLlave;
        mDatos[0] = pDato;
    }

    public void setK(int mK) {
        this.mK = mK;
    }

    public int getK() {
        return mK;
    }

    public int getmK() {
        return mK;
    }

    public void setmK(int mK) {
        this.mK = mK;
    }

    public int getmB() {
        return mB;
    }

    public void setmB(int mB) {
        this.mB = mB;
    }

    public Ordenable[] getmLlaves() {
        return mLlaves;
    }

    public void setmLlaves(Ordenable[] mLlaves) {
        this.mLlaves = mLlaves;
    }

    public Object[] getmDatos() {
        return mDatos;
    }

    public void setmDatos(Object[] mDatos) {
        this.mDatos = mDatos;
    }

    public BTreeNode[] getmPunteros() {
        return mPunteros;
    }

    public void setmPunteros(BTreeNode[] mPunteros) {
        this.mPunteros = mPunteros;
    }

    public static int getNumeroDeNodo() {
        return numeroDeNodo;
    }

    public static void setNumeroDeNodo(int numeroDeNodo) {
        BTreeNode.numeroDeNodo = numeroDeNodo;
    }
    
    
}


