package nodes;

import java.io.Serializable;
import models.Computer;


public class SimpleListNode implements Serializable{
    private Computer computer;
    private SimpleListNode next;

    public SimpleListNode() {
    }

    public SimpleListNode(Computer computer) {
        this.computer = computer;
        this.next = null;
    }

    public Computer getComputer() {
        return computer;
    }

    public SimpleListNode getNext() {
        return next;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }

    public void setNext(SimpleListNode next) {
        this.next = next;
    }
    
    
}
