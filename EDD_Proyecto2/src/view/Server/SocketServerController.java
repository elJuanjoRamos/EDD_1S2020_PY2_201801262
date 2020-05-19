
package view.Server;

import Controller.StructureController;
import Structures.DoubleList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;
import models.Blockchain;
import models.Book;
import models.Category;
import models.Computer;
import models.DataServer;
import models.User;
import nodes.SimpleListNode;
import org.json.simple.JSONObject;

public class SocketServerController implements Initializable, Runnable {
    
    DoubleList dbList = new DoubleList();
    
    @FXML
    private TextArea server;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        Thread thread = new Thread(this);
        thread.start();
    }    
    
    @Override
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            DataServer dataServer = null;
            server.setText("Listen 8000");
            
            while(true) {
                
                Socket socket = serverSocket.accept();
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                    
                dataServer = (DataServer) objectInputStream.readObject();
                
                String ip = dataServer.getIp();
                
                if(dataServer.getUser() != null) {
                    User u = dataServer.getUser();
                    switch(dataServer.getState()) {
                        case "Add":
                            StructureController.getInstancia().InsertTable(u.getCarnet(), u.getName(), u.getLastName(), u.getCareer(), u.getPassword());
                            UpdateBlockChain("ADD", "User", ip, u);
                            server.setText("Add user");
                            break;
                        case "Edit":
                            StructureController.getInstancia().UpdateTable(u.getCarnet(), u.getName(), u.getLastName(), u.getCareer(), u.getPassword());
                            UpdateBlockChain("UPDATE", "User", ip, u);
                            server.setText("Edit user");
                            break;
                        case "Del":
                            StructureController.getInstancia().DeleteTable(u.getCarnet());
                            server.setText("Delete user");
                            UpdateBlockChain("DELETE", "User", ip, u);
                            break;
                    }
                } else if(dataServer.getBook() != null) {
                    Book b = dataServer.getBook();
                    switch(dataServer.getState()) {
                        case "Add":
                            StructureController.getInstancia().InsertB(b.getCategory(), b.getIsbn(), b.getTittle(), b.getAutor(), b.getYear(), b.getEdition(), b.getEditorial(), b.getLanguaje(), b.getCarnet());
                            UpdateBlockChain("ADD", "Book", ip, b);
                            server.setText("Add book");
                            break;
                        case "Edit":
                            UpdateBlockChain("UPDATE", "Book", ip, b);
                            server.setText("Edit book");
                            break;
                        case "Del":
                            StructureController.getInstancia().DeleteB(b.getIsbn(), b.getCategory());
                            UpdateBlockChain("DELETE", "Book", ip, b);
                            server.setText("Delete book");
                            break;
                    }
                } else if(dataServer.getCategory() != null) {
                    Category c = dataServer.getCategory();
                    switch(dataServer.getState()) {
                        case "Add":
                            StructureController.getInstancia().InsertAVL(c.getName(), c.getIdUser());
                            UpdateBlockChain("ADD", "Category", ip, c);
                            server.setText("Add category" + "\nIP: " + ip);            
                            break;
                        case "Edit":
                            server.setText("Edit category"+ "\nIP: " + ip);
                            break;
                        case "Del":
                            StructureController.getInstancia().DeleteAVL(c.getName());
                            UpdateBlockChain("DELETE", "Category", ip, c);
                            server.setText("Delete category"+ "\nIP: " + ip);
                            break;
                    }
                } else if(dataServer.getComputer() != null) {
                    Computer c = dataServer.getComputer();
                    switch(dataServer.getState()) {
                        case "Add":
                            StructureController.getInstancia().InsertSimple(c.getIp(), c.getPort());
                            UpdateBlockChain("ADD", "Computer", dataServer.getIp(), c);
                            server.setText("Add computer" + "\nIP: " + ip);
                            break;
                        case "Edit":
                            server.setText("Edit computer" + "\nIP: " + ip);
                            UpdateBlockChain("EDIT", "Computer", dataServer.getIp(), c);
                            break;
                        case "Del":
                            StructureController.getInstancia().DeleteSimple(c.getIp());
                            UpdateBlockChain("DELETE", "Computer", dataServer.getIp(), c);
                            server.setText("Delete computer" + "\nIP: " + ip);
                            break;
                    }
                } else if(dataServer.getBlockchain()!= null) {
                    Blockchain c = dataServer.getBlockchain();
                    switch(dataServer.getState()) {
                        case "Add":
                            StructureController.getInstancia().InsertDoubleList(c.getIp(), new Object());
                            //UpdateBlockChain("ADD", "Computer", dataServer.getIp(), c);
                            server.setText("Add block" + "\nIP: " + ip);
                            break;
                        case "Edit":
                            server.setText("Edit block" + "\nIP: " + ip);
                            //UpdateBlockChain("EDIT", "Computer", dataServer.getIp(), c);
                            break;
                        case "Del":
                            //StructureController.getInstancia().D(c.getIp());
                            
                            ///UpdateBlockChain("DELETE", "Computer", dataServer.getIp(), c);
                            server.setText("Delete block" + "\nIP: " + ip);
                            break;
                    }
                } 
                else if(dataServer.getCategoryNames().size()> 0){
                    ArrayList<Category> c = dataServer.getCategoryNames();
                    switch(dataServer.getState()) {
                        case "Array":
                            for (Category u : c) {
                                StructureController.getInstancia().InsertAVL(u.getName(), u.getIdUser());
                                UpdateBlockChain("ADD", "Category", ip, u);
                            }                           
                            server.setText("Add array" + "\nIP: " + ip);
                            break;
                    }
                }
                else if(dataServer.getUsers().size() > 0) {
                    ArrayList<User> c = dataServer.getUsers();
                    switch(dataServer.getState()) {
                        case "Array":
                            for (User u : c) {
                                StructureController.getInstancia().InsertTable(u.getCarnet(), u.getName(), u.getLastName(), u.getCareer(), u.getPassword());
                                UpdateBlockChain("ADD", "User", ip, u);
                            }                           
                            server.setText("Add array" + "\nIP: " + ip);
                            break;
                    }
                } else if(dataServer.getBooks().size() > 0){
                    
                    ArrayList<Book> c = dataServer.getBooks();
                    switch(dataServer.getState()){            
                        case "Array" :
                            for (Book book : c) {
                                System.out.println(book.getCategory()+ " " +book.getIsbn()+ " " + book.getTittle()+ " " + book.getAutor()+ " " + book.getYear()+ " " + book.getEdition()+ " " + book.getEditorial()+ " " + book.getLanguaje()+ " " +book.getCarnet());
                                StructureController.getInstancia().InsertB(book.getCategory(), book.getIsbn(), book.getTittle(), book.getAutor(), book.getYear(), book.getEdition(), book.getEditorial(), book.getLanguaje(), book.getCarnet());
                                UpdateBlockChain("ADD", "Book", ip, book);
                            }
                            server.setText("Add Book"+ "\nIP: " + ip);
                        break;
                    }
                }
                
                System.err.println(dataServer.getIp());
                
                
                SimpleListNode computer = StructureController.getInstancia().getSimpleList().getListNode(); 
                StructureController.getInstancia().PrintSimple();
                while(computer != null) {
                    System.out.println("ENVIAR: " +computer.getComputer().getIp() );
                    Socket socketEnviar = new Socket(computer.getComputer().getIp(), 8200);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socketEnviar.getOutputStream());
                    objectOutputStream.writeObject(dataServer);
                    objectOutputStream.close();
                    socketEnviar.close();
                    computer = computer.getNext();
                }
                
                socket.close();
            }
        } catch (IOException ex) {
            System.out.println(ex);
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        }
    }
    
    
    
    public void UpdateBlockChain(String action, String type, String ip, Object o){
        
        JSONObject jsonBody = new JSONObject();
        JSONObject jsonObject = new JSONObject();
        
        
        
        
        
        
        if (type.equals("Category")) {
            Category c = (Category)o;
            jsonBody.put("NAME", c.getName());
            jsonBody.put("ID", c.getId());
            jsonBody.put("USER", c.getIdUser());
            
            if (action.equals("ADD")) {
                jsonObject.put("ADD_CATEGORY", jsonBody);
            } else if(action.equals("DELETE")){
                jsonObject.put("DELETE_CATEGORY", jsonBody);
            } else {
                jsonObject.put("UPDATE_CATEGORY", jsonBody);
            }
        }
        
        if (type.equals("Book")) {
            Book c = (Book)o;
            jsonBody.put("ISBN", c.getIsbn());
            jsonBody.put("TITTLE", c.getTittle());
            jsonBody.put("AUTOR", c.getAutor());
            jsonBody.put("EDITORIAL", c.getEditorial());
            jsonBody.put("YEAR", c.getYear());
            jsonBody.put("EDITION", c.getEdition());
            jsonBody.put("CATEGORY", c.getCategory());
            jsonBody.put("LANGUAJE", c.getLanguaje());
            jsonBody.put("USER", c.getCarnet());

            if (action.equals("ADD")) {
                jsonObject.put("ADD_BOOK", jsonBody);
            } else if(action.equals("DELETE")){
                jsonObject.put("DELETE_BOOK", jsonBody);
            } else {
                jsonObject.put("UPDATE_BOOK", jsonBody);
            }
        }
        if (type.equals("User")) {
            User c = (User)o;
            jsonBody.put("CARNET", c.getCarnet());
            jsonBody.put("NAME", c.getName());
            jsonBody.put("LASTNAME", c.getLastName());
            jsonBody.put("CAREER", c.getCareer());
            jsonBody.put("PASSWORD", c.getPassword());

            if (action.equals("ADD")) {
                System.out.println("entro");
                jsonObject.put("ADD_USER", jsonBody);
            }  else {
                jsonObject.put("UPDATE_USER", jsonBody);
            }
        }
        System.out.println("Cosa: " + type  );
        //CUANDO SE INSERTA UN NUEVO NODO, SIMPLEMENTE CREA EL BLOQUE
        if (type.equals("Computer")) {
            if(!dbList.Exist(ip)){
                dbList.addLastNode(ip, null);
            } else {
                System.out.println("ya existe");
            }
            try {
                dbList.Print();
            } catch (Exception e) {
            }
        } else {
            //SI EL NODO YA ESTA CREADO, SE VA A BUSCAR
            if (dbList.getRoot() == null) {
                dbList.addLastNode(ip, jsonObject);
                dbList.show();
            } else {
                dbList.findBlock(ip, jsonObject);
                dbList.show();
            }
        } 
        
 

    }
    
}
