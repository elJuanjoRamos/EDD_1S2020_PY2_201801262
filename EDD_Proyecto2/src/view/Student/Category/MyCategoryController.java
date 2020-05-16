/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Student.Category;

import Controller.ServerClientController;
import Controller.StructureController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import models.Category;
import models.Configuration;
import models.DataServer;
import models.User;
import view.Student.Student;

/**
 * FXML Controller class
 *
 * @author Juan José Ramos
 */
public class MyCategoryController implements Initializable, Runnable {

    @FXML
    TableView<Category> tableView;
    @FXML
    TableColumn<Category, String> tableColumnId;
    @FXML
    TableColumn<Category, String> tableColumnName;
    @FXML
    TableColumn<Category, String> tableColumnBooks;
    @FXML
    TableColumn<Category, String> tableColumnHaveBooks;
    @FXML
    TableColumn<Category, String> tableColumnIdUser;

    @FXML
    TextField eName;
    @FXML
    Button aceptar, eliminar;
    @FXML
    Text texto;
    @FXML
    StackPane stackPane;
    
    /*VARIABLES */
    User client;
    ObservableList observableList;
    Thread thread;
    ServerSocket serverSocket;
    
    public MyCategoryController() {
        client = Student.user;
        observableList = FXCollections.observableArrayList();
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        texto.setText("Add a new Category");
        tableColumnId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumnName.setCellValueFactory(new PropertyValueFactory<>("name"));
        tableColumnBooks.setCellValueFactory(new PropertyValueFactory<>("booksQuantiy"));
        tableColumnHaveBooks.setCellValueFactory(new PropertyValueFactory<>("haveBooks"));
        tableColumnIdUser.setCellValueFactory(new PropertyValueFactory<>("idUser"));
        initTableView();
        //thread = new Thread(this);
        //thread.start();
    }
    
    /**
     * INICIALIZAR DATOS EN TABLA
     */
    public void initTableView() {
        observableList.clear();
        observableList = StructureController.getInstancia().getMyCategory(client.getCarnet());
        tableView.setItems(observableList);
    }
    
    /**
     * AGREGAR DATOS
     */
    @FXML
    private void add(ActionEvent event) {
        if (getValidations() == true) {
            try {
                Category c = new Category(0, client.getCarnet(), 0, eName.getText(), true);
                Socket socket = new Socket(Configuration.ipServer, Integer.parseInt("8000"));
                DataServer servidorEDD = new DataServer();
                servidorEDD.setState("Add");
                servidorEDD.setIp(Configuration.ip);
                servidorEDD.setCategory(c);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(servidorEDD);
                socket.close();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
            StructureController.getInstancia().PrintAvl();
            try {
                Thread.sleep(2000);
                initTableView();
            } catch (InterruptedException ex) {
                Logger.getLogger(MyCategoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
            clearFields();
        }
    }
    
    @FXML
    private void delete(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            String id = tableView.getSelectionModel().getSelectedItem().getName();
            try {
                Category c = new Category(0, 0, 0, id, true);
                Socket socket = new Socket(Configuration.ipServer, Integer.parseInt("8000"));
                DataServer servidorEDD = new DataServer();
                servidorEDD.setState("Del");
                servidorEDD.setIp(Configuration.ip);
                servidorEDD.setCategory(c);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(servidorEDD);
                socket.close();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
            StructureController.getInstancia().PrintAvl();
            try {
                Thread.sleep(2000);
                initTableView();
            } catch (InterruptedException ex) {
                Logger.getLogger(MyCategoryController.class.getName()).log(Level.SEVERE, null, ex);
            }
            //clearFields();
            
        } else {
            getAlert(" No items have been selected.");
        }
    }
     /*VALIDA SI DEJA CAMPOS EN BLANCO*/
    public boolean getValidations() {
        String s = eName.getText();
        if (!s.isEmpty()) {
            return true;
        }
        getAlert("You can not leave fields blank.");
        return false;
    }

    public void getAlert(String content) {
        JFXDialogLayout c = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(stackPane, c, JFXDialog.DialogTransition.CENTER);
        c.setHeading(new Text("Error!"));
        c.setBody(new Text(content));
        JFXButton button = new JFXButton("Close");
        button.setOnAction((ActionEvent event) -> {
            dialog.close();
        });;
        c.setActions(button);
        dialog.show();
    }

    /**
     * LIMPIAR CAMPOS DE TEXTO
     */
    public void clearFields() {
        eName.clear();
    }

    @Override
    public void run() {
        try {
            serverSocket = ServerClientController.getInstancia().getServerSocket();
            DataServer dataServer = null;
            while(true) {
                Socket cliente = serverSocket.accept();
                ObjectInputStream flujoEntrada = new ObjectInputStream(cliente.getInputStream());
                
                dataServer = (DataServer) flujoEntrada.readObject();
                if(dataServer.getCategory() != null) {
                    Category c = dataServer.getCategory();
                    switch(dataServer.getState()) {
                        case "Add":
                            StructureController.getInstancia().InsertAVL(c.getName(), c.getIdUser());
                            StructureController.getInstancia().PrintAvl();
                            initTableView();
                            clearFields();
                            //server.setText("Add category");
                            break;
                        case "Edit":
                            //server.setText("Edit category");
                            break;
                        case "Del":
                            StructureController.getInstancia().DeleteAVL(c.getName());
                            initTableView();
                            //server.setText("Delete category");
                            break;
                    } 
                }
                initTableView();
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
