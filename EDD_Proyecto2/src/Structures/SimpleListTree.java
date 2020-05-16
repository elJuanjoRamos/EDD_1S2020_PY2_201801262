/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Structures;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import nodes.SimpleListNodeTree;

/**
 *
 * @author Juan José Ramos
 */
public class SimpleListTree {
    SimpleListNodeTree first;

    public SimpleListTree() {
        first = null;
    }
    
    public void Insert(int index, String name){
        
        SimpleListNodeTree nuevo = new SimpleListNodeTree(index, name);

        if (first == null) {
            first = nuevo;
        }
        else {
            SimpleListNodeTree temp = first;

            while (temp.getNext() != null) {
                temp = temp.getNext();
            }
            temp.setNext(nuevo);

        }
    }
    
    public void Print(String name) throws IOException{
        
        String name1 = "";
        
        if(name.equals("InOrden")){
            name1 = "InOrden: Iqz, Raiz, Der";
        } else if(name.equals("PreOren")){
            name1 = "PreOren: Raiz, Izq, Der";
        } else {
            name1 = "PosOrden: Izq, Der, Raiz";
        }
        
        String archivoCabeza = "digraph G {rankdir=LR;"+
		"label=\"" + name1 + "\";node[shape=box];\n";

	String nodes = "";
	String assembleNodos = "";
        
        
        SimpleListNodeTree aux = first;
	SimpleListNodeTree ant = null;
	String texto = "";
	int contador = 1;
       
        while (aux != null)
	{
		nodes = nodes + "nodo" + (aux.getIndex()) + " [ label =\"" + (aux.getIndex()) + " - " + aux.getName() + "\"];\n";
		contador++;
		ant = aux;
		aux = aux.getNext();
		if (aux != null)
		{
                    assembleNodos = assembleNodos + "nodo" + (ant.getIndex()) + "-> nodo" + (aux.getIndex()) + "\n";
		}
	}
	texto = archivoCabeza + nodes + assembleNodos + "}";
        
        try {
            String nameF = "AVLTree" + name +".dot";
            File f = new File(nameF);
            if(f.exists() && !f.isDirectory()) { 
                f.delete();
            }
            FileWriter myObj = new FileWriter(nameF);
            myObj.write(texto);
            myObj.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
        String newText = "dot -Tjpg -o src/resources/img/AVLTree" + name +".png AVLTree" + name +".dot";
        Runtime.getRuntime().exec(newText);
        
        

    }
    
    public void Clear(){
        first = null;
    }
    
}
