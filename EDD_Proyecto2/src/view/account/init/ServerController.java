package view.account.init;

import Controller.ServerClientController;
import Controller.StructureController;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Computer;
import models.Configuration;
import models.DataServer;
import models.User;
import view.account.Account;

public class ServerController implements Initializable {
    @FXML
    public TextField ip, server, port, portServer;
    @FXML Button authenticate;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            // TODO
            String address = InetAddress.getLocalHost().getHostAddress();
            ip.setText(address);
            ip.setText("192.168.0.116");            
            server.setText("192.168.0.116");
            //ip.setText("192.168.1.101");
            //server.setText("192.168.1.101");
            port.setText("8000");
            portServer.setText("8200");

        } catch (UnknownHostException ex) {
            Logger.getLogger(ServerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    
    @FXML
    public void authenticate(ActionEvent event) throws Exception {
        System.out.println("ip" + ip.getText());
        System.out.println("server" + server.getText());
        System.out.println("port"+ port.getText());
        System.out.println("port server"+ portServer.getText());
        Configuration.ip = ip.getText();
        Configuration.ipServer = server.getText();
        Configuration.port = port.getText();
        Configuration.portServer = portServer.getText();
        System.out.println("ip" + Configuration.ip);
        System.out.println("server" + Configuration.ipServer);
        System.out.println("port"+ Configuration.port);
        System.out.println("port server"+ Configuration.portServer);
        ServerClientController.getInstancia().iniciarSocket();
        //StructureController.getInstancia().InsertSimple(Configuration.ip, Integer.parseInt(Configuration.port));
        try {
            Computer c = new Computer(0, Configuration.ip, Integer.parseInt(Configuration.port));
            Socket socket = new Socket(Configuration.ipServer, Integer.parseInt("8000"));
            DataServer servidorEDD = new DataServer();
            servidorEDD.setState("Add");
            servidorEDD.setIp(Configuration.ip);
            servidorEDD.setComputer(c);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(servidorEDD);
            socket.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        //StructureController.getInstancia().InsertSimple(Configuration.ip, Integer.parseInt(Configuration.port));
        StructureController.getInstancia().PrintSimple();
        Account.getInstance().start(Server.s);
        /*Parent root;
        root = FXMLLoader.load(getClass().getClassLoader().getResource("view/account/Account.fxml"));
        Stage stage2 = new Stage();
        stage2.setTitle("Login");
        stage2.setScene(new Scene(root));
        stage2.show();
        // Hide this current window (if this is what you want)
        //((Stage)(event.getSource())).getScene().getWindow().hide();
        Stage stage = (Stage) authenticate.getScene().getWindow();
        // do what you have to do
        stage.hide();*/
    }
    
}
