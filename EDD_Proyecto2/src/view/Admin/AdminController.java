/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Admin;

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
public class AdminController implements Initializable {

    /*SINGLETON*/
    private static AdminController instance;
    public static AdminController getInstance(){
        if(instance == null){
            instance = new AdminController();
        }
        return instance;
    }
    /*---------------*/
    
    
    @FXML 
    private VBox vbox;
    @FXML
    private Parent fxml;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        changeView("/view/Admin/Home.fxml");
    }    
    @FXML
    private void open_user(ActionEvent event) {
      changeView("/view/Admin/User/FXMLUser.fxml");
        
    }
    @FXML
    private void open_home(ActionEvent event) {
        changeView("/view/Admin/Home.fxml");
        
    }
    @FXML
    private void open_reports(ActionEvent event) {
        changeView("/view/Admin/Reports/Reports.fxml");
    }

    
    @FXML 
    public void logOut( ActionEvent event ) throws Exception {
        //ServerClientController.getInstancia().stopTHread();
        Account.getInstance().start(Admin.s);
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
            url = new File("src/view/Admin/" + component).toURI().toURL();
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
