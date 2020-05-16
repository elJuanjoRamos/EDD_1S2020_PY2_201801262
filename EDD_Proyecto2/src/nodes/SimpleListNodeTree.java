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
public class SimpleListNodeTree {

    String name;
    int index;
    SimpleListNodeTree next ;

    public SimpleListNodeTree(int i, String s) {
        name = s;
        index = i;
        next = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public SimpleListNodeTree getNext() {
        return next;
    }

    public void setNext(SimpleListNodeTree next) {
        this.next = next;
    }
    
    
}
