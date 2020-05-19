/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Student;

import Controller.ServerClientController;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import view.account.Account;
import view.account.AccountController;

/**
 * FXML Controller class
 *
 * @author Juan José Ramos
 */
public class UserController implements Initializable {


    @FXML 
    private VBox vbox;
    @FXML
    private Parent fxml;
    
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        changeView("/view/Student/Dashboard.fxml");
    }  
    
    @FXML
    public void open_Dash(){
        changeView("/view/Student/Dashboard.fxml");
    
    }
    
    @FXML 
    public void my_Books( ActionEvent event ) {
        changeView("/view/Student/MyBooks/MyBooks.fxml");
    }
    @FXML 
    public void my_Category( ActionEvent event ) {
        changeView("/view/Student/Category/MyCategory.fxml");
    }
    
    @FXML 
    public void general_library( ActionEvent event ) {
        changeView("/view/Student/GeneralBooks/GeneralBooks.fxml");
    }

    
    @FXML 
    public void logOut( ActionEvent event ) throws Exception {
        ServerClientController.getInstancia().stopTHread();
        Account.getInstance().start(Student.getInstance().getStage());
        Student.user = null;
    }
    
    
    
    public void changeView(String component){
        
        try {
            fxml = (Parent) FXMLLoader.load(getClass().getResource(component));
            vbox.getChildren().removeAll();
            vbox.getChildren().setAll(fxml);
        } catch (IOException ex) {
            System.out.println(ex);
            Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        /*URL url;
        try {
            url = new File("src/view/Student/" + component).toURI().toURL();
            try {
                fxml = (Parent)FXMLLoader.load(url);
                vbox.getChildren().removeAll();
                vbox.getChildren().setAll(fxml);
            } catch (IOException ex) {
                System.out.println(ex);
                Logger.getLogger(AccountController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (Exception e) {
            System.out.println(e);
        }*/

    }    
}
