/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Admin;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Juan José Ramos
 */
public class HomeController implements Initializable {
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();
    
    @FXML Text dateNow;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        dateNow.setText(dateFormat.format(date));
    }    
    
    @FXML
    public void changeToEmployee(){
        AdminController.getInstance().changeView("/views/Admin/Employee/EmployeeView.fxml");
    }
    
}
