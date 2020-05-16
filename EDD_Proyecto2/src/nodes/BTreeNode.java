/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodes;

import java.util.ArrayList;
import models.Book;

/**
 *
 * @author Juan José Ramos
 */

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Juan José Ramos
 */
public class BTreeNode {

    
    public int mK;
    public int mB;
    public int[] keys;
    public Object[] datas;
    public BTreeNode[] pointers;
    
    private static int NodeNumber = 1;
    
    public String getDotName() {
        return "Nodo" + this.hashCode();
    }
    public BTreeNode(int pK) {
        this.mK = pK;
        mB = 0;
        keys = new int[2 * pK + 1];
        datas = new Object[2 * pK + 1];
        pointers = new BTreeNode[(2 * pK) + 2];
    }

    public BTreeNode(int pK, int pLlave, Object pDato) {
        this(pK);
        mB = 1;
        keys[0] = pLlave;
        datas[0] = pDato;
    }

    public void setK(int mK) {
        this.mK = mK;
    }

    public int getK() {
        return mK;
    }


    public int getmB() {
        return mB;
    }

    public void setmB(int mB) {
        this.mB = mB;
    }

    public int[] getKeys() {
        return keys;
    }

    public void setKeys(int[] keys) {
        this.keys = keys;
    }

    public Object[] getDatas() {
        return datas;
    }

    public void setDatas(Object[] datas) {
        this.datas = datas;
    }

    public BTreeNode[] getPointers() {
        return pointers;
    }

    public void setPointers(BTreeNode[] pointers) {
        this.pointers = pointers;
    }

    public static int getNodeNumber() {
        return NodeNumber;
    }

    public static void setNodeNumber(int NodeNumber) {
        BTreeNode.NodeNumber = NodeNumber;
    }
    public String toDot(  )  {
        
        StringBuilder b = new StringBuilder();
        
        b.append( getDotName() );
        b.append("[label=\"<P0>");
        for( int i = 0; i < mB; i++ ) {
            Book book = (Book)datas[i];
            b.append( "| ISBN:" +  keys[i] + "\\lTittle: " + book.getTittle());
            b.append( "|<P" + (i+1) + ">" );                
        }
        
        b.append("\"];\n");
        
        for( int i = 0; i <= mB ; i++ ) {
            if( pointers[i] != null )   {
                b.append( pointers[i].toDot() );
                b.append( getDotName() + ":P" + i + " -> " + pointers[i].getDotName() + ";\n" );
            }
        }
        
        return b.toString();
        
    }
    
    static ArrayList ar = new ArrayList();
    public void traverse(int x) { 
 
        int i = 0; 
        int j = 0;
        for (int k = 0; k < this.keys.length; k++) {
            
            if (this.keys[k] != 0) {
                j++;
            }
        }
        for (i = 0; i < j; i++) { 
  
            if (this.pointers[0] != null) { 
                pointers[i].traverse(x); 
            } 
            //System.out.print(keys[i] + " "); 
            if (((Book)datas[i]).getCarnet() == x) {
                ar.add((Book)datas[i]);
            }
        } 
  
        if (this.pointers[0] != null) { 
            pointers[i].traverse(x); 
        }
            
    }
    public boolean searchKey(int x) { 
  
        int i = 0; 
        int j = 0;
        for (int k = 0; k < this.keys.length; k++) {
            
            if (this.keys[k] != 0) {
                j++;
            }
        }
        for (i = 0; i < j; i++) { 
  
            if (this.pointers[0] != null) { 
                pointers[i].traverse(x); 
            } 
            if ((int)keys[i] == x) {
                return true;
            }
        } 
        if (this.pointers[0] != null) { 
            pointers[i].traverse(x); 
        }
        return false;
    }
    
    public void clearArray(){
        this.ar.clear();
    }
    public ArrayList getArray(){
        return this.ar;
    }
    
}


