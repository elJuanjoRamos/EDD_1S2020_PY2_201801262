/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Student;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.User;

/**
 *
 * @author Juan José Ramos
 */
public class Student {
 
    public static User user;
    
     /*SINGLETON*/
    private static Student instance;
    public static Student getInstance(){
        if(instance == null){
            instance = new Student();
        }
        return instance;
    }
    /*---------------*/

    /*VARIABLES*/
    Stage stageView;
    
    public Student() {
    }
    

    public void start(Stage stage, User cln) {
        
        stageView = stage;
        user = cln;
        
        
        
        try {
            Parent root = FXMLLoader.load(getClass().getResource("User.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch(Exception e) {
            System.out.println(e.getCause());
        }
    }
    
    public Stage getStage(){
        return this.stageView;
    }
        
}
