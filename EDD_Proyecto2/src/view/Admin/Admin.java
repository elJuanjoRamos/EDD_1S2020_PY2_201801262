/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Admin;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Juan José Ramos
 */
public class Admin {
    /*SINGLETON*/
    private static Admin instance;
    public static Admin getInstance(){
        if(instance == null){
            instance = new Admin();
        }
        return instance;
    }
    /*---------------*/

    public Admin() {
    }
    
    /*VARIABLES*/
    public static Stage s;
    public void start(Stage stage) throws Exception {
        s = stage;
        
        Parent root = FXMLLoader.load(getClass().getResource("Admin.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
        /*try {
            Parent root = (Parent)FXMLLoader.load(getClass().getResource("Admin.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            System.out.println("la expcepcion es");
            System.out.println(e);
        }*/
    }
    
    public Stage getStage(){
        return this.s;
    }
}
