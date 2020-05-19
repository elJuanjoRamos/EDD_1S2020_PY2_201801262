/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import models.Blockchain;
import models.Book;
import models.Category;
import models.Computer;
import models.Configuration;
import models.DataServer;
import models.User;

public class ServerClientController implements Runnable{
    public static ServerClientController instancia;
    public boolean stop = false;
    
    ServerSocket serverSocket;
    public static ServerClientController getInstancia() throws IOException {
        if (instancia == null) {
            instancia = new ServerClientController();
        }
        return instancia;
    }
    Thread thread;

    public ServerClientController() throws IOException {
        serverSocket = new ServerSocket(8200);
        
    }

    public ServerSocket getServerSocket() {
        return serverSocket;
    }
    
    public void iniciarSocket() {
        thread = new Thread(this);
        thread.start();
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public Thread getThread() {
        return thread;
    }

    public boolean isStop() {
        return stop;
    }
    
    public void stopTHread() throws IOException, InterruptedException{
        try {
            Computer c = new Computer(0, Configuration.ip, Integer.parseInt(Configuration.port));
            Socket socket = new Socket(Configuration.ipServer, Integer.parseInt("8000"));
            DataServer servidorEDD = new DataServer();
            servidorEDD.setState("Del");
            servidorEDD.setIp(Configuration.ip);
            servidorEDD.setComputer(c);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(servidorEDD);
            socket.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        Thread.sleep(2000);
        stop = true;
        thread.stop();
        serverSocket.close();
    }
    
    @Override
    public void run() {
        try {
            serverSocket = getServerSocket();
            DataServer dataServer = null;
            while(!stop) {
                Socket cliente = serverSocket.accept();
                ObjectInputStream flujoEntrada = new ObjectInputStream(cliente.getInputStream());
                
                dataServer = (DataServer) flujoEntrada.readObject();
                if(dataServer.getUser() != null) {
                    User u = dataServer.getUser();

                    switch(dataServer.getState()) {
                        case "Add":
                            StructureController.getInstancia().InsertTable(u.getCarnet(), u.getName(), u.getLastName(), u.getCareer(), u.getPassword());
                            StructureController.getInstancia().PrintTable();
                            break;
                        case "Edit":
                            StructureController.getInstancia().UpdateTable(u.getCarnet(), u.getName(), u.getLastName(), u.getCareer(), u.getPassword());
                            StructureController.getInstancia().PrintTable();
                            break;
                        case "Del":
                            StructureController.getInstancia().DeleteTable(u.getCarnet());
                            StructureController.getInstancia().PrintTable();
                            break;
                    }
                    
                    
                } else if(dataServer.getCategory() != null) {
                    Category c = dataServer.getCategory();
                    switch(dataServer.getState()) {
                        case "Add":
                            StructureController.getInstancia().InsertAVL(c.getName(), c.getIdUser());
                            StructureController.getInstancia().PrintAvl();
                            //initTableView();
                            //clearFields();
                            //server.setText("Add category");
                            break;
                        case "Edit":
                            //server.setText("Edit category");
                            break;
                        case "Del":
                            StructureController.getInstancia().DeleteAVL(c.getName());
                            //initTableView();
                            //server.setText("Delete category");
                            break;
                    }
                    
                    
                } else if(dataServer.getBook()!= null) {
                    Book b = dataServer.getBook();
                    switch(dataServer.getState()) {
                        case "Add":
                            StructureController.getInstancia().InsertB(b.getCategory(), b.getIsbn(), b.getTittle(), b.getAutor(), b.getYear(), b.getEdition(), b.getEditorial(), b.getLanguaje(), b.getCarnet());
                            //server.setText("Add book");
                            //initTableView();
                            //clearFields();
                            break;
                        case "Edit":
                            //server.setText("Edit book");
                            break;
                        case "Del":
                            StructureController.getInstancia().DeleteB(b.getIsbn(), b.getCategory());
                            //server.setText("Delete book");
                            //initTableView();
                            //dialog.close();
                            break;
                    }
                    
                    
                } else if(dataServer.getComputer() != null) {
                    Computer c = dataServer.getComputer();
                    switch(dataServer.getState()) {
                        case "Add":
                            StructureController.getInstancia().InsertSimple(c.getIp(), c.getPort());
                            //server.setText("Add computer");
                            break;
                        case "Edit":
                            //server.setText("Edit computer");
                            break;
                        case "Del":
                            System.out.println("ELIMINAR NODO" + c.getIp());
                            StructureController.getInstancia().DeleteSimple(c.getIp());
                                                        //server.setText("Delete computer");
                            break;
                    }
                } else if(dataServer.getBlockchain()!= null) {
                    Blockchain c = dataServer.getBlockchain();
                    switch(dataServer.getState()) {
                        case "Add":
                            StructureController.getInstancia().InsertDoubleList(c.getIp(), new Object());
                            //UpdateBlockChain("ADD", "Computer", dataServer.getIp(), c);
                            //server.setText("Add block" + "\nIP: " + ip);
                            break;
                        case "Edit":
                            //server.setText("Edit block" + "\nIP: " + ip);
                            //UpdateBlockChain("EDIT", "Computer", dataServer.getIp(), c);
                            break;
                        case "Del":
                            //StructureController.getInstancia().D(c.getIp());
                            
                            ///UpdateBlockChain("DELETE", "Computer", dataServer.getIp(), c);
                            //server.setText("Delete block" + "\nIP: " + ip);
                            break;
                    }
                } else if(dataServer.getUsers().size() > 0) {
                    ArrayList<User> c = dataServer.getUsers();
                    switch(dataServer.getState()) {
                        case "Array":
                            for (User u : c) {
                                StructureController.getInstancia().InsertTable(u.getCarnet(), u.getName(), u.getLastName(), u.getCareer(), u.getPassword());
                                //UpdateBlockChain("ADD", "User", dataServer.getIp(), u);
                            }
                            
                            //UpdateBlockChain("ADD", "Computer", dataServer.getIp(), c);
                            //server.setText("Add array" + "\nIP: " + ip);
                            break;
                    }
                }
                else if(dataServer.getCategoryNames().size()> 0){
                    ArrayList<Category> c = dataServer.getCategoryNames();
                    switch(dataServer.getState()) {
                        case "Array":
                            for (Category u : c) {
                                StructureController.getInstancia().InsertAVL(u.getName(), u.getIdUser());
                            }                           
                            break;
                    }
                }
                else if(dataServer.getBooks().size() > 0) {
                    ArrayList<Book> c = dataServer.getBooks();
                    switch(dataServer.getState()){            
                        case "Array" :
                            for (Book book : c) {
                                System.out.println(book.getCategory()+ " " +book.getIsbn()+ " " + book.getTittle()+ " " + book.getAutor()+ " " + book.getYear()+ " " + book.getEdition()+ " " + book.getEditorial()+ " " + book.getLanguaje()+ " " +book.getCarnet());
                                StructureController.getInstancia().InsertB(book.getCategory(), book.getIsbn(), book.getTittle(), book.getAutor(), book.getYear(), book.getEdition(), book.getEditorial(), book.getLanguaje(), book.getCarnet());
                            }
                        break;
                    }
                }
                cliente.close();
                System.out.println("i am listening");
            }
            
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException ex) {
            System.err.println(ex.getMessage());
        }
    }
    
}
