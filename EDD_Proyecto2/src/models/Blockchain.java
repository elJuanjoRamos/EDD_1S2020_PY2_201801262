/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author josem
 */
public class Blockchain {
    private int id;
    private String dateTime;
    private ArrayList data;
    private String nonce;
    private String precedingsHash;
    private String hash;
    private String ip;

    public Blockchain() {
    }

    public Blockchain(int id, String dateTime, ArrayList data, String nonce, String precedingsHash, String hash) {
        this.id = id;
        this.dateTime = dateTime;
        this.data = data;
        this.nonce = nonce;
        this.precedingsHash = precedingsHash;
        this.hash = hash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public ArrayList getData() {
        return data;
    }

    public void setData(ArrayList data) {
        this.data = data;
    }

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getPrecedingsHash() {
        return precedingsHash;
    }

    public void setPrecedingsHash(String precedingsHash) {
        this.precedingsHash = precedingsHash;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public void setDateTime(SimpleDateFormat dt1) {
        this.dateTime = dt1.toString();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
    
}
