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
import models.Computer;
import nodes.SimpleListNode;

/**
 *
 * @author josem
 */
public class SimpleList {
    private static SimpleListNode listNode;
    private int size;

    public SimpleList() {
    }
    
    public boolean esVacia(){
        return listNode == null;
    }

    public SimpleListNode getListNode() {
        return listNode;
    }
    
    public void add(String ip, int port){
        Computer c = new Computer(size, ip, port);
        SimpleListNode nuevo = new SimpleListNode();
        nuevo.setComputer(c);
        if (esVacia()) {
            listNode = nuevo;
        } else{
            SimpleListNode aux = listNode;
            while(aux.getNext() != null){
                aux = aux.getNext();
            }
            aux.setNext(nuevo);
        }
        size++;
    }
    
    public void delete(String referencia){
        if (search(referencia)==true) {
            System.out.println("IP ENCONTRADA");
            if (listNode.getComputer().getIp().equals(referencia)) {
                listNode = listNode.getNext();
            } else{
                SimpleListNode aux = listNode;
                while(!aux.getNext().getComputer().getIp().equals(referencia)){
                    aux = aux.getNext();
                }
                SimpleListNode siguiente = aux.getNext().getNext();
                aux.setNext(siguiente);  
            }
            size--;
        }
    }
    
    public boolean search(String ip){
        SimpleListNode aux = listNode;
            while(aux != null){
                if(aux.getComputer().getIp().equals(ip)) {
                    return true;
                }
                aux = aux.getNext();
            }
        return false;
    }
    
    public void list(){
        if (!esVacia()) {
            SimpleListNode aux = listNode;
            while(aux != null){
                aux = aux.getNext();
            }
        }
    }
    
    ArrayList computers = new ArrayList();
    public ArrayList getBObjects(){
        computers.clear();
        if (!esVacia()) {
            SimpleListNode aux = listNode;
            while(aux != null){
                computers.add(aux);
                aux = aux.getNext();
            }
        }
        return computers;
    }
    
     public void Print() throws IOException {
        String texto = "";
        
        //
        //texto = "digraph grafica{\n" + "rankdir=TB;" + "graph[label=\"" + name + "\", labelloc=t, fontsize=20, compound=true]\n" + "node [shape=plaintext, fontcolor=red, fontsize=18];\n\"Pointers:\" -> \"Values:\" -> \"Indices:\" [color=white];" + this.GetBody() + "\n\n}";
        texto = "digraph grafica{\n" + "graph[label=\"Lista de nodos en red\", labelloc=t, fontsize=20, compound=true];" + "\nrankdir = LR;\nnode [shape=record];\nsplines=false; " + GetBody() + "}";

        try {
            File f = new File("SimpleList.dot");
            if(f.exists() && !f.isDirectory()) { 
                f.delete();
            }
            FileWriter myObj = new FileWriter("SimpleList.dot");
            myObj.write(texto);
            myObj.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        Runtime.getRuntime().exec("dot -Tjpg -o src/resources/img/SimpleList.png SimpleList.dot");

    }
     
     public String GetBody() {
        String body = "";
        int counter = 0;
        SimpleListNode aux = listNode;
         System.out.println("=============================HOLA?=============================");
        while(aux != null){
            System.out.println(aux.getComputer().getIp());
            body = body + "NodeLogChange" + counter + " [label =\"" + "IP:  " + aux.getComputer().getIp() + "\"]\n";
            aux = aux.getNext();
            counter++;
        }
        for (int i = 0; i < counter - 1; i++)
	{
		body = body + "NodeLogChange" + i + "->NodeLogChange" + (i+1);
	}         
        return body;
     }
}
