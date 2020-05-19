/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Admin.User;

import Controller.StructureController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Configuration;
import models.DataServer;
import models.User;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;


/**
 * FXML Controller class
 *
 * @author Juan José Ramos
 */
public class FXMLUserController implements Initializable {

    
 private static FXMLUserController instance;
    @FXML
    TableView<User> tableView;
    @FXML
    TableColumn<User, Long> carnet;
    @FXML
    TableColumn<User, String> name;
    @FXML
    TableColumn<User, String> apellido;
    @FXML
    TableColumn<User, String> career;
    @FXML
    TableColumn<User, String> password;

    @FXML
    TextField eName, eLastname, eCarnet, eCarreer, ePassword;
    @FXML
    Button aceptar, editar, eliminar, cancelar, subir;
    @FXML
    Text texto;
    @FXML
    StackPane stackPane;
    Thread thread;
    public FXMLUserController() {
    }

    /**
     * @return the instance
    *
     */
    public static FXMLUserController getInstance() {
        if (instance == null) {
            instance = new FXMLUserController();
        }
        return instance;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editar.setVisible(false);
        cancelar.setVisible(false);
        texto.setText("Add a new User");
        carnet.setCellValueFactory(new PropertyValueFactory<>("carnet"));
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        apellido.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        career.setCellValueFactory(new PropertyValueFactory<>("career"));
        initTableView();
        //thread = new Thread(this);
        //thread.start();
    }
    /**
     * INICIALIZAR DATOS EN TABLA
     */
    public void initTableView() {
        try{
            ObservableList<User> observableList = StructureController.getInstancia().getUsers();
            tableView.setItems(observableList);
        } catch(Exception e){
            System.out.println(e);
        }
    }
    @FXML
    public void bulkLoad(ActionEvent event) throws FileNotFoundException, IOException, ParseException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON Files", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            
            
            Object obj = new JSONParser().parse(new FileReader(selectedFile)); 

            // typecasting obj to JSONObject 
            JSONObject jo = (JSONObject) obj; 

            Iterator<Map.Entry> itr1 = null; 
            
            
            // getting phoneNumbers 
            JSONArray ja = (JSONArray) jo.get("Usuarios"); 

            // iterating phoneNumbers 
            Iterator itr2 = ja.iterator(); 
            ArrayList<User> users = new ArrayList<>();
            while (itr2.hasNext())  
            { 
                itr1 = ((Map) itr2.next()).entrySet().iterator(); 
                String name = "";
                String lastname = "";
                String career = "";
                String password = "";
                int carnet = 0;
                
                while (itr1.hasNext()) { 
                    Map.Entry pair = itr1.next(); 
                    if (pair.getKey().equals("Nombre")) {
                        name = (String)pair.getValue();
                    }
                    if (pair.getKey().equals("Apellido")) {
                        lastname = (String)pair.getValue();
                    }
                    if (pair.getKey().equals("Carrera")) {
                        career = (String)pair.getValue();
                    }
                    if (pair.getKey().equals("Password")) {
                        password = (String)pair.getValue();
                    }
                    if (pair.getKey().equals("Carnet")) {
                        carnet = Integer.parseInt(pair.getValue().toString());
                    }
                }
                User u = new User(carnet, name, lastname, career, password);
                users.add(u);
            }
            try {
                
                Socket socket = new Socket(Configuration.ipServer, Integer.parseInt("8000"));
                DataServer servidorEDD = new DataServer();
                servidorEDD.setState("Array");
                servidorEDD.setIp(Configuration.ip);
                servidorEDD.setUsers(users);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(servidorEDD);
                socket.close();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
            
        }
        try {
            Thread.sleep(2000);
            initTableView();
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        StructureController.getInstancia().PrintTable();
    }
    /**
     * AGREGAR DATOS
     */
    
    @FXML
    private void add(ActionEvent event) {
        if (getValidations() == true) {
            if (isNumber(eCarnet.getText())) {
                try {
                    User u = new User(Integer.parseInt(eCarnet.getText()), eName.getText(), eLastname.getText(), eCarreer.getText(), ePassword.getText());
                    Socket socket = new Socket(Configuration.ipServer, Integer.parseInt("8000"));
                    DataServer servidorEDD = new DataServer();
                    servidorEDD.setState("Add");
                    servidorEDD.setIp(Configuration.ip);
                    servidorEDD.setUser(u);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(servidorEDD);
                    socket.close();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
                clearFields();
                try {
            Thread.sleep(2000);
            initTableView();
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
                StructureController.getInstancia().PrintTable();
            } else {
                getAlert("You can not enter text in numeric fields");
            }
        }
    }
    
    
    
    /**
     * ELIMINAR DATOS
     */
    @FXML
    private void delete(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            try {
                    User u = new User();
                    u.setCarnet(tableView.getSelectionModel().getSelectedItem().getCarnet());
                    Socket socket = new Socket(Configuration.ipServer, Integer.parseInt("8000"));
                    DataServer servidorEDD = new DataServer();
                    servidorEDD.setState("Del");
                    servidorEDD.setIp(Configuration.ip);
                    servidorEDD.setUser(u);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(servidorEDD);
                    socket.close();
                            initTableView();
                            StructureController.getInstancia().PrintTable();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
                try {
            Thread.sleep(2000);
            initTableView();
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
                StructureController.getInstancia().PrintTable();
        } else {
            getAlert(" No items have been selected.");
        }
    }
    
    
    /**
     * ACTUALIZAR DATOS
     */
    @FXML
    private void update(ActionEvent event) {
        if (getValidations() == true) {
            if (isNumber(eCarnet.getText())) {
                try {
                    User u = new User(Integer.parseInt(eCarnet.getText()), eName.getText(), eLastname.getText(), eCarreer.getText(), ePassword.getText());
                    Socket socket = new Socket(Configuration.ipServer, Integer.parseInt("8000"));
                    DataServer servidorEDD = new DataServer();
                    servidorEDD.setState("Edit");
                    servidorEDD.setIp(Configuration.ip);
                    servidorEDD.setUser(u);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(servidorEDD);
                    socket.close();
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
                aceptar.setVisible(true);
                editar.setVisible(false);
                cancelar.setVisible(false);
                texto.setText("Add a new User");
                clearFields();
                
            } else {
                getAlert("You can not enter text in numeric fields");
            }
        } 
        try {
            Thread.sleep(2000);
            initTableView();
        } catch (InterruptedException ex) {
            Logger.getLogger(FXMLUserController.class.getName()).log(Level.SEVERE, null, ex);
        }
        StructureController.getInstancia().PrintTable();
    }
    /**
     * OBTENER DATOS
     */
    @FXML
    private void getData(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            User c = StructureController.getInstancia().searchUser(tableView.getSelectionModel().getSelectedItem().getCarnet());
            if (c != null) {
                aceptar.setVisible(false);
                editar.setVisible(true);
                cancelar.setVisible(true);
                texto.setText("Edit the Client");
                eCarnet.setText(String.valueOf(c.getCarnet()));
                eName.setText(c.getName());
                eLastname.setText(c.getLastName());
                eCarreer.setText(c.getCareer());
                
            }
        } else {
            getAlert(" No items have been selected.");
        }
    }

    /**
     * VER DATOS
     */
    @FXML
    private void openTable(ActionEvent event) {
         JFXDialogLayout c = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(stackPane, c, JFXDialog.DialogTransition.CENTER);
        c.setHeading(new Text("Error!"));
        c.setBody(new Text("Hola"));
        javafx.scene.image.Image image = new javafx.scene.image.Image("resources/img/car1.png");
    
//        c.setBody(image);
        
        JFXButton b = new JFXButton("Close");
        b.setOnAction((ActionEvent e) -> {
            dialog.close();
        });;
        c.setActions(b);
        dialog.show();
    }
    
    @FXML
    private void cancel(ActionEvent event) {
        clearFields();
        initTableView();
        aceptar.setVisible(true);
        editar.setVisible(false);
        cancelar.setVisible(false);
        texto.setText("Add a new User");
    }
    
    public boolean getValidations() {
        if (!eName.getText().isEmpty() && !eLastname.getText().isEmpty()
                && !ePassword.getText().isEmpty() && !eCarreer.getText().isEmpty() && !eCarnet.getText().isEmpty()) {
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
        eCarnet.clear();
        eName.clear();
        ePassword.clear();
        eCarreer.clear();
        eLastname.clear();
    }
    public boolean isNumber(String option) {
        try {
            Integer.parseInt(option);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }    
}
