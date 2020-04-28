/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.account;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import view.Admin.Admin;

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
    public void authenticate(ActionEvent event) {
        try {
            Admin.getInstance().start(Account.s);
            
        } catch (Exception e) {
            System.out.println("la excepcion esta en authcontroller");
            System.out.println(e);
        }
 
        /*Employee emp = EmployeesController.getInstance().authenticate(data);
        Client cln = ClientsController.getInstance().authenticate(user.getText(), pass.getText());
        
        if (emp != null && cln == null) {
            Admin.getInstance().start(Account.s);
        } else if(emp == null && cln != null){
            ClientView.getInstance().start(Account.s, cln);
        } */
        
        /*if (!user.getText().isEmpty() && !pass2.getText().isEmpty() ) {
            
            if (user.getText().equals("admin") && user.getText().equals("admin")) {
                //Admin.getInstance().start(Account.s);
            }
            
        } else {
            textLogin.setText("You can not leave fields blank.");
        }*/
          
        
        
    }
    
    @FXML
    private void getAccess(ActionEvent event) {
        
        if (!name.getText().isEmpty() && !pass.getText().isEmpty() &&
             !carnet.getText().isEmpty() && !career.getText().isEmpty()) {
            
            try{
                Long.parseLong(carnet.getText());
                /*if (ClientsController.getInstance().verifications(user2.getText())) {
                text.setText("This username already exists.");
                    } else {
                        ClientsController.getInstance().addAtEnd(dpi.getText(), name.getText(), user2.getText(), pass2.getText(), "Normal");
                        Client c = ClientsController.getInstance().searchForUserName(user2.getText());
                        if (c != null) {
                            ClientView.getInstance().start(Account.s, c);
                        } else {
                            text.setText("User not found");
                        }
                    }*/
            } catch(Exception e){
                text.setText("The DPI value can only be numeric.");
            }
            
            
        } else {
            text.setText("You can not leave fields blank.");
        }
           
    }
    
}
