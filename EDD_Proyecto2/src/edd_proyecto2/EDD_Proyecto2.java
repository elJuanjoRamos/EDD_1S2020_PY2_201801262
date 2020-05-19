/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edd_proyecto2;

import Controller.StructureController;
import Structures.DoubleList;
import javafx.application.Application;
import javafx.stage.Stage;
import view.Server.SocketServer;

import view.account.init.Server;
/**
 *
 * @author Juan José Ramos
 */
public class EDD_Proyecto2 extends Application {
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        Server.getInstance().start(primaryStage);
        //SocketServer.getInstance().start(primaryStage);
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
