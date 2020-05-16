/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Student;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import models.User;

/**
 * FXML Controller class
 *
 * @author Juan José Ramos
 */
public class DashboardController implements Initializable {

    @FXML Text name, lastname, passwor, career, carnet;
    User user;

    public DashboardController() {
        user = Student.user;
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        name.setText(user.getName() + " " + user.getLastName());
        //lastname.setText(user.getLastName());
        career.setText(user.getCareer());
        carnet.setText(String.valueOf(user.getCarnet()));
        
        
    }    
    
}
