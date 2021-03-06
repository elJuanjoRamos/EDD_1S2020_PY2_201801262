/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.account;

import Controller.StructureController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import models.User;
import view.Admin.Admin;
import view.Student.Student;
import view.account.init.Server;

/**
 * FXML Controller class
 *
 * @author Juan José Ramos
 */
public class AuthController implements Initializable {
  /*VARIABLES*/
    @FXML
    public TextField lastname, pass, name, carnet, career, user, pass2;
    @FXML private Text text, textLogin;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }
    
        
    @FXML
    public void authenticate(ActionEvent event) throws Exception {
        
        //User u = StructureController.getInstancia().searchUser(1, "123");
                            
        //Admin.getInstance().start(Server.s);
        //Student.getInstance().start(Account.s, u);
        try {
            
            
            if (!user.getText().isEmpty() && !pass2.getText().isEmpty() ) {
            
                if (user.getText().equals("admin") && pass2.getText().equals("admin")) {
                    Admin.getInstance().start(Account.s);
                } else {
                     try{
                         
                            //Integer.parseInt(user.getText());
                            User u = StructureController.getInstancia().searchUser(Integer.parseInt(user.getText()), pass2.getText());
                            if (u != null) {
                                 Student.getInstance().start(Account.s, u);
                            } else {
                                text.setText("User not found");
                            }
  
                            
                        } catch(Exception e){
                            text.setText("The CARNET value can only be numeric.");
                        }
                }
            
            } else {
                textLogin.setText("You can not leave fields blank.");
            }
            
            
            
        } catch (Exception e) {
            System.out.println("la excepcion esta en authcontroller");
            System.out.println(e);
        }
 
      
        
    }
    
    @FXML
    private void getAccess(ActionEvent event) {
        
        if (!name.getText().isEmpty()  && !lastname.getText().isEmpty() && !pass.getText().isEmpty() &&
             !carnet.getText().isEmpty() && !career.getText().isEmpty()) {
            
            try{
                Integer.parseInt(carnet.getText());
                
                User u = StructureController.getInstancia().searchUser(Integer.parseInt(carnet.getText()));
                
                if (u != null) {
                    text.setText("The user has already been added.");
                } else {
                    
                    StructureController.getInstancia().InsertTable(Integer.parseInt(carnet.getText()), name.getText(), 
                            lastname.getText(), career.getText(), pass.getText());
                    
                    User user = StructureController.getInstancia().searchUser(Integer.parseInt(carnet.getText()));
                    Student.getInstance().start(Account.s, user);
                    
                }
                            
            } catch(Exception e){
                text.setText("The DPI value can only be numeric.");
            }
            
            
        } else {
            text.setText("You can not leave fields blank.");
        }
           
    }
    
}
