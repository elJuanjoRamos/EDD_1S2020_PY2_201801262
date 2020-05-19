/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nodes;

import models.Blockchain;


public class DoubleListNode {
    private Blockchain blockchain;
    private DoubleListNode next;
    private DoubleListNode previous;

    public DoubleListNode() {
    }

    public DoubleListNode(Blockchain blockchain) {
        this.blockchain = blockchain;
        this.next = null;
        this.previous = null;
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public DoubleListNode getNext() {
        return next;
    }

    public DoubleListNode getPrevious() {
        return previous;
    }

    public void setBlockchain(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public void setNext(DoubleListNode next) {
        this.next = next;
    }

    public void setPrevious(DoubleListNode previous) {
        this.previous = previous;
    }

}
