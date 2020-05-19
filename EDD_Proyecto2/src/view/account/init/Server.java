package view.account.init;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import view.account.Account;

public class Server  extends Application {
    /*Variables*/
    public static Stage s;
    /*SINGLETON*/
    private static Server instance;
    public static Server getInstance(){
        if(instance == null){
            instance = new Server();
        }
        return instance;
    }
    /*---------------*/

    public Server() {
    }
    
    
    
    @Override
    public void start(Stage stage) throws Exception {
        s = stage;
        
        Parent root = FXMLLoader.load(getClass().getResource("Server.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setScene(scene);
        stage.show();
    }
    

}
