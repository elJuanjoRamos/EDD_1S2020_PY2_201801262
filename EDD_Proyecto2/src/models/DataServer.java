
package models;

import java.io.Serializable;
import java.util.ArrayList;

public class DataServer  implements Serializable {
    private User user;
    private String state;
    private String ip;
    private Book book;
    private Category category;
    private Computer computer;
    private Blockchain blockchain;
    private ArrayList<Book> books = new ArrayList();    
    private ArrayList<Category> categorys = new ArrayList();    
    private ArrayList<User> users = new ArrayList();
    private ArrayList<Category> categoryNames = new ArrayList();



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

    public ArrayList<Book> getBooks() {
        return books;
    }

    public ArrayList<Category> getCategorys() {
        return categorys;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setBooks(ArrayList<Book> books) {
        System.out.println("llego");
        this.books = books;
    }

    public void setCategorys(ArrayList<Category> categorys) {
        this.categorys = categorys;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<Category> getCategoryNames() {
        return categoryNames;
    }

    public void setCategoryNames(ArrayList<Category> categoryNames) {
        this.categoryNames = categoryNames;
    }
  
    
        
}
