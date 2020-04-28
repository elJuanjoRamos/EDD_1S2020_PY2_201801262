/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edd_proyecto2;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import Controller.StructureController;
import Structures.HashTable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import view.account.Account;
/**
 *
 * @author Juan José Ramos
 */
public class EDD_Proyecto2 extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Account.getInstance().start(primaryStage);

        /*Button btn = new Button();
        btn.setText("Say 'Hello World'");
        
        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
         */       
                
               /* StructureController.getInstancia().Insert("m");
                StructureController.getInstancia().Insert("a");
                StructureController.getInstancia().Insert("b");
                StructureController.getInstancia().Insert("i");
                StructureController.getInstancia().Insert("w");
                StructureController.getInstancia().Insert("z");
                StructureController.getInstancia().Insert("x");
                
                StructureController.getInstancia().PrintAvl();
                
                
                StructureController.getInstancia().InsertB("a", 0, "a", "a", "a", 0,new ArrayList() ,new ArrayList(), "b", 0  );
                StructureController.getInstancia().InsertB("a", 1, "b", "a", "a", 0,new ArrayList() ,new ArrayList(), "b", 0  );
                StructureController.getInstancia().InsertB("a", 15, "c", "a", "a", 0,new ArrayList() ,new ArrayList(), "b", 0  );
                StructureController.getInstancia().InsertB("a", 9, "d", "a", "a", 0,new ArrayList() ,new ArrayList(), "b", 0  );
                StructureController.getInstancia().InsertB("a", 450, "h", "a", "a", 0,new ArrayList() ,new ArrayList(), "b", 0  );
                StructureController.getInstancia().InsertB("a", 8, "r", "a", "a", 0,new ArrayList() ,new ArrayList(), "b", 0  );
                StructureController.getInstancia().InsertB("a", 750, "y", "a", "a", 0,new ArrayList() ,new ArrayList(), "b", 0  );
                
                StructureController.getInstancia().PrintB("a");
                        
                
                StructureController.getInstancia().DeleteB(750, "a");
                
                StructureController.getInstancia().PrintB("a");
                
                StructureController.getInstancia().DeleteB(9, "a");
                
                StructureController.getInstancia().PrintB("a");*/
                
                
               /* StructureController.getInstancia().InsertTable(15, "Didier", "Dominguez", "Sistemas", "123456");
                StructureController.getInstancia().InsertTable(11, "Jeni", "Orellana", "Agronomia", "123456");
                StructureController.getInstancia().InsertTable(221, "Jacqueline", "Mendez", "Sistemas", "123456");
                StructureController.getInstancia().InsertTable(27, "Rafa", "Morente", "Sistemas", "456465");
                StructureController.getInstancia().InsertTable(8, "Kembly", "Galindo", "Quimica", "456465");
                StructureController.getInstancia().InsertTable(29, "Gerson", "Belteton", "Sistemas", "456465");
                StructureController.getInstancia().InsertTable(12, "Andrea", "Abrego", "Industrial", "456123546");
                StructureController.getInstancia().InsertTable(23, "Luis", "Velasquez", "Industrial", "456123546");
                StructureController.getInstancia().InsertTable(46, "Luis", "Garcia", "Industrial", "456123546");
                StructureController.getInstancia().PrintTable();
               
                StructureController.getInstancia().UpdateTable(29, "Ana", "Ramos", "Medicina", "456465");
                StructureController.getInstancia().PrintTable();
               */
               
                
                
            /*    System.out.println("Hello World!");
            }*/
        /*});
        
        StackPane root = new StackPane();
        root.getChildren().add(btn);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
        */
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
