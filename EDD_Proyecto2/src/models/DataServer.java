
package models;

import java.io.Serializable;

public class DataServer  implements Serializable {
    private User user;
    private String state;
    private String ip;
    private Book book;
    private Category category;
    private Computer computer;
    private Blockchain blockchain;

    public DataServer() {
    }
  
    public String getIp() {
        return ip;
    }

    public String getState() {
        return state;
    }

    public User getUser() {
        return user;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public Category getCategory() {
        return category;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Blockchain getBlockchain() {
        return blockchain;
    }

    public Computer getComputer() {
        return computer;
    }

    public void setBlockchain(Blockchain blockchain) {
        this.blockchain = blockchain;
    }

    public void setComputer(Computer computer) {
        this.computer = computer;
    }
    
}
