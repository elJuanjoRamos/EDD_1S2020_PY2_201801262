/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Student.GeneralBooks;

import Controller.StructureController;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import models.Book;

/**
 * FXML Controller class
 *
 * @author Juan José Ramos
 */
public class GeneralBooksController implements Initializable {

    private static GeneralBooksController instance;
    @FXML
    Text texto;
    
    
    @FXML
    TableView<Book> tableView;
    @FXML
    TableColumn<Book, String> tableColumnIsbn;
    @FXML
    TableColumn<Book, String> tableColumnTittle;
    @FXML
    TableColumn<Book, String> tableColumnAutor;
    @FXML
    TableColumn<Book, String> tableColumnYear;
    @FXML
    TableColumn<Book, String> tableColumnCategory;
    @FXML
    TableColumn<Book, String> tableColumnLanguaje;
    @FXML
    TableColumn<Book, String> tableColumnUser;
    @FXML
    TextField searchText;
    
    ObservableList observableList;
    ObservableList filteredData;
    
     public static GeneralBooksController getInstance() {
        if (instance == null) {
            instance = new GeneralBooksController();
        }
        return instance;
    }

    public GeneralBooksController() {
        observableList = FXCollections.observableArrayList();
        filteredData = FXCollections.observableArrayList();
        
        observableList.addListener(new ListChangeListener<Book>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Book> change) {
                updateFilteredData();
            }
        });
    }
     
    
     
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        texto.setText("Search a Book");
        tableColumnIsbn.setCellValueFactory(new PropertyValueFactory<>("isbn"));
        tableColumnTittle.setCellValueFactory(new PropertyValueFactory<>("tittle"));
        tableColumnAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));
        tableColumnCategory.setCellValueFactory(new PropertyValueFactory<>("category"));
        tableColumnLanguaje.setCellValueFactory(new PropertyValueFactory<>("languaje"));
        tableColumnUser.setCellValueFactory(new PropertyValueFactory<>("carnet"));

        initTableView();
        searchText.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                updateFilteredData();
                
            }
        });
    }


/**
     * INICIALIZAR DATOS EN TABLA
     */
    public void initTableView() {
        observableList.clear();
        tableView.getItems().clear();
        observableList = StructureController.getInstancia().getGeneralBooks();
        tableView.setItems(observableList);
        
    }


    private void updateFilteredData() {
        filteredData.clear();
        ArrayList temp = new ArrayList();
        for (Object p : observableList) {
            
            temp.add((Book)p);
        }
        for (int i = 0; i < temp.size(); i++) {
            Book b = (Book) temp.get(i);
            if (searchText.getText() == null || searchText.getText().isEmpty()) {

            }
            String lowerCaseFilterString = searchText.getText().toLowerCase();

            if (b.getTittle().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
                filteredData.add(b);
            } else if (b.getTittle().toLowerCase().indexOf(lowerCaseFilterString) != -1) {
                filteredData.add(b);
            }
        }
        
        // Must re-sort table after items changed
        reapplyTableSortOrder();
    }
    
    private void reapplyTableSortOrder() {
        tableView.getSortOrder().clear();
        tableView.setItems(filteredData);
    }    
    
}
