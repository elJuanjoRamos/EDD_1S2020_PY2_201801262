/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Admin.Reports;

import Controller.StructureController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Juan José Ramos
 */
public class ReportsController implements Initializable {

    @FXML
    ImageView imagenView, imageAvlInOrder, imageAvlTreePosOrden, imageBTree,
            imageAvlTreePreOrdern, imageUsersHashTable, imageSimpleList, imageBlockChain;

    @FXML
    ComboBox comboCategory;
    
    @FXML
    StackPane stackPane;

    public ReportsController() {
        StructureController.getInstancia().PrintAvl();
        StructureController.getInstancia().PrintSimple();
        StructureController.getInstancia().PrintTable();
    
    }
     
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        StructureController.getInstancia().PrintAvl();
        StructureController.getInstancia().PrintSimple();
        StructureController.getInstancia().PrintTable();
        StructureController.getInstancia().PrintDouble();
        
        
        SetImage("AVLTree", imagenView);
        SetImage("AVLTreeInOrden", imageAvlInOrder);
        SetImage("AVLTreePosOrden", imageAvlTreePosOrden);
        SetImage("AVLTreePreOren", imageAvlTreePreOrdern);
        SetImage("HashTable", imageUsersHashTable);
        SetImage("SimpleList", imageSimpleList);
        SetImage("DoubleList", imageBlockChain);
        SetImage("BTree", imageBTree);
        
        /*LLAMA LAS CATEGORIAS DISPONIBLES*/
        StructureController.getInstancia().PrintSimple();                    
        for (Object object : StructureController.getInstancia().getMyCategory()) {
            System.out.println(object);
        }
        comboCategory.setItems(StructureController.getInstancia().getMyCategory());

    }

    public void SetImage(String name, ImageView element) {

        File file = new File(name + ".png");
        if (file.exists()) {
            if (file != null) {
                String path = "file:///" + file.getAbsoluteFile().getParent() + "\\" + name + ".png";
                Image i = new Image(path);
                element.setImage(i);
            }
        } else {
            System.out.println("no encontrado");
        }
    }

    
    @FXML
    private void PrintBTree(ActionEvent event) throws InterruptedException {
        if (comboCategory.getSelectionModel().getSelectedItem() != null) {
            
            StructureController.getInstancia().PrintB(comboCategory.getSelectionModel().getSelectedItem().toString());
            imageBTree.setImage(null);
            
            Thread.sleep(2000);
            SetImage("BTree", imageBTree);
            
        } else {
            getAlert("No Category selected.");
        }
    }
    
    public void getAlert(String content) {
        JFXDialogLayout c = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(stackPane, c, JFXDialog.DialogTransition.CENTER);
        c.setHeading(new Text("Error!"));
        c.setBody(new Text(content));
        JFXButton button = new JFXButton("Close");
        button.setOnAction((ActionEvent event) -> {
            dialog.close();
        });;
        c.setActions(button);
        dialog.show();
    }
}
