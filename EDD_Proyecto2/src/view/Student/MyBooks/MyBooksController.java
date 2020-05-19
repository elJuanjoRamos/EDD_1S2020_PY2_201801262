/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view.Student.MyBooks;

import Controller.ServerClientController;
import Controller.StructureController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXTextField;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import models.Book;
import models.Category;
import models.Configuration;
import models.DataServer;
import models.User;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import view.Student.Student;

/**
 * FXML Controller class
 *
 * @author Juan José Ramos
 */
public class MyBooksController implements Initializable {

    private static MyBooksController instance;
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
    ComboBox comboCategory;
    ServerSocket serverSocket;

    @FXML
    TextField eIsbn, eTittle, eAutor, eYear, eLanguaje, eEdition, eEditorial, searchText;
    @FXML
    Button aceptar, editar, eliminar, cancelar, subir;
    @FXML
    Text texto;
    @FXML
    StackPane stackPane;
    Thread thread;
    /*VARIABLES */
    User client;
    int count = 1;
    ObservableList observableList;
    ObservableList filteredData;
    /**
     * @return the instance
     *
     */
    public static MyBooksController getInstance() {
        if (instance == null) {
            instance = new MyBooksController();
        }
        return instance;
    }

    public MyBooksController() {
        client = Student.user;
        observableList = FXCollections.observableArrayList();
        filteredData = FXCollections.observableArrayList();
        
        observableList.addListener(new ListChangeListener<Book>() {
            @Override
            public void onChanged(ListChangeListener.Change<? extends Book> change) {
                updateFilteredData();
            }
        });
        //thread = new Thread(this);
        //thread.start();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        editar.setVisible(false);
        cancelar.setVisible(false);
        texto.setText("Add a new Book");
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
        observableList = StructureController.getInstancia().getMyBooks(client.getCarnet());
        tableView.setItems(observableList);
        /*LLAMA LAS CATEGORIAS DISPONIBLES*/
        comboCategory.setItems(StructureController.getInstancia().getMyCategory());

    }

    /**
     * ELIMINAR DATOS
     */
    /**
     * AGREGAR DATOS
     */
    @FXML
    private void add(ActionEvent event) {
        
        if (getValidations() == true) {
            try {
                Integer.parseInt(eIsbn.getText());
                Integer.parseInt(eYear.getText());
                ArrayList edition = new ArrayList();
                edition.add(eEdition.getText() );
                
                ArrayList editorial = new ArrayList();
                editorial.add(eEditorial.getText() );
                
                if (StructureController.getInstancia().searchKey(Integer.parseInt(eIsbn.getText())) == false) {
                    
                    try {
                        //String categoryName, int isbn, String tittle, String autor, int year, ArrayList edition, ArrayList editorial, String languaje, int carnet
                        //Category c = new Category(0, client.getCarnet(), 0, eName.getText(), true);
                        Book b = new Book(Integer.parseInt(eIsbn.getText()),
                                eTittle.getText(), 
                                eAutor.getText(),
                                comboCategory.getSelectionModel().getSelectedItem().toString(),
                                Integer.parseInt(eYear.getText()),
                                edition, 
                                editorial, 
                                eLanguaje.getText(), 
                                client.getCarnet());
                        Socket socket = new Socket(Configuration.ipServer, Integer.parseInt("8000"));
                        DataServer servidorEDD = new DataServer();
                        servidorEDD.setState("Add");
                        servidorEDD.setIp(Configuration.ip);
                        servidorEDD.setBook(b);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                        objectOutputStream.writeObject(servidorEDD);
                        socket.close();
                    } catch (IOException ex) {
                        System.err.println(ex.getMessage());
                    }
                    //StructureController.getInstancia().InsertB(b.getCategory(), b.getIsbn(), b.getTittle(), b.getAutor(), b.getYear(), b.getEdition(), b.getEditorial(), b.getLanguaje(), b.getCarnet());
                            //server.setText("Add book");
                            try {
                Thread.sleep(2000);
                initTableView();
            } catch (InterruptedException ex) {
                Logger.getLogger(MyBooksController.class.getName()).log(Level.SEVERE, null, ex);
            }
                            clearFields();
                } else {
                    getAlert("A Book whit ISBN:" + eIsbn.getText() + " has already been entered" );
                }   
            } catch (Exception e) {
                getAlert("ISBN and Year only can be number");
            }
        }
    }

    /**
     * ACTUALIZAR DATOS
     */
    @FXML
    private void update(ActionEvent event) {
        if (getValidations() == true) {
            /*if (tableView.getSelectionModel().getSelectedItem() != null) {
                int id = tableView.getSelectionModel().getSelectedItem().getId();

                CarController.getInstance().update(id, ePlate.getText(), eBrand.getText(), eModel.getText(), ePath.getText());
            }
            aceptar.setVisible(true);
            editar.setVisible(false);
            cancelar.setVisible(false);
            texto.setText("Add a new Car");
            initTableView();
            clearFields();
                */
        }
    }

    @FXML
    private void delete(ActionEvent event) {
        if (tableView.getSelectionModel().getSelectedItem() != null) {
            Book b = tableView.getSelectionModel().getSelectedItem();
            try {
               
                Socket socket = new Socket(Configuration.ipServer, Integer.parseInt("8000"));
                DataServer servidorEDD = new DataServer();
                servidorEDD.setState("Del");
                servidorEDD.setIp(Configuration.ip);
                servidorEDD.setBook(b);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(servidorEDD);
                socket.close();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
            searchText.clear();
            try {
                Thread.sleep(2000);
                initTableView();
            } catch (InterruptedException ex) {
                Logger.getLogger(MyBooksController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        } else {
            getAlert(" No items have been selected.");
        }
    }
    
    @FXML
    private void deleteById(ActionEvent event) {
        
        JFXDialogLayout content = new JFXDialogLayout();
        JFXDialog dialog = new JFXDialog(stackPane, content, JFXDialog.DialogTransition.CENTER);
        JFXTextField t = new JFXTextField();
        content.setBody(t);
        content.setHeading(new Text("Delete a Book by ISNB"));

        JFXButton button = new JFXButton("Delete");
        
        button.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (!t.getText().isEmpty()) {
                    try{
                        Integer.parseInt(t.getText());
                        
                        String category ="";
                        boolean encontrado = false;
                        for (Object object : observableList) {
                            
                            Book p = (Book)object;
                            
                            if (p.getIsbn() == Integer.parseInt(t.getText())) {
                                encontrado = true;
                                category = p.getCategory();
                                break;
                            }
                            
                        }
                        if (encontrado) {
                            //StructureController.getInstancia().DeleteB(Integer.parseInt(t.getText()), category);
                            try {
                                Book b = new Book(Integer.parseInt(t.getText()), category);
                                Socket socket = new Socket(Configuration.ipServer, Integer.parseInt("8000"));
                                DataServer servidorEDD = new DataServer();
                                servidorEDD.setState("Del");
                                servidorEDD.setIp(Configuration.ip);
                                servidorEDD.setBook(b);
                                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                                objectOutputStream.writeObject(servidorEDD);
                                socket.close();
                            } catch (IOException ex) {
                                System.err.println(ex.getMessage());
                            }
                            dialog.close();
                            StructureController.getInstancia().PrintB(category);
                            try {
                                Thread.sleep(2000);
                                initTableView();
                            } catch (InterruptedException ex) {
                                Logger.getLogger(MyBooksController.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else {
                            getAlert("Book not found or does not belong to your library");
                        }
                    } catch(Exception e){
                        getAlert("ISBN can only be number");
                    }
                } else {
                    getAlert("You can not leave fields blank.");
                }
            }
        });
        
        JFXButton button1 = new JFXButton("Close");
        button1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                dialog.close();
            }
        });;
        content.setActions(button, button1);
        dialog.show();
        
    }

    @FXML
    private void cancel(ActionEvent event){
        
    }

    /*Actualizar*/
    @FXML
    private void subir(ActionEvent event) throws FileNotFoundException, IOException, ParseException {
        Stage stage = new Stage();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON File", "*.json"));
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            
             Object obj = new JSONParser().parse(new FileReader(selectedFile)); 

            // typecasting obj to JSONObject 
            JSONObject jo = (JSONObject) obj; 

            Iterator<Map.Entry> itr1 = null; 
            
            
            // getting phoneNumbers 
            JSONArray ja = (JSONArray) jo.get("libros"); 
            
            
            // iterating phoneNumbers 
            Iterator itr2 = ja.iterator(); 
            ArrayList<Book> books = new ArrayList<>();
            ArrayList<Category> categories = new ArrayList<>();
            while (itr2.hasNext())  
            { 
                itr1 = ((Map) itr2.next()).entrySet().iterator(); 
                int ISBN = 0;
                int year = 0;
                String Idioma = "";
                String Titulo = "";
                String Editorial = "";
                String Autor = "";
                String Edicion = "";
                String Categoria = "";
                
                while (itr1.hasNext()) { 
                    Map.Entry pair = itr1.next(); 
                    if (pair.getKey().equals("ISBN")) {
                        ISBN = Integer.parseInt(pair.getValue().toString());
                    }
                    if (pair.getKey().equals("Año")) {
                        year = Integer.parseInt(pair.getValue().toString());
                    }
                    if (pair.getKey().equals("Idioma")) {
                        Idioma = (String)pair.getValue();
                    }
                    if (pair.getKey().equals("Titulo")) {
                        Titulo = (String)pair.getValue();
                    }
                    if (pair.getKey().equals("Editorial")) {
                        Editorial = (String)(pair.getValue());
                    }
                    if (pair.getKey().equals("Autor")) {
                        Autor = (String)(pair.getValue());
                    }if (pair.getKey().equals("Edicion")) {
                        Edicion = pair.getValue().toString();
                    }if (pair.getKey().equals("Categoria")) {
                        Categoria = (String)(pair.getValue());
                        categories.add( new Category(client.getCarnet(), Categoria));
                    }
                    
                    
                }
                ArrayList t = new ArrayList();
                        t.add(Edicion);
                ArrayList e = new ArrayList();
                e.add(Editorial);
                
                if (StructureController.getInstancia().searchKey(ISBN) == false) {
                    Book b = new Book(ISBN, Titulo, Autor, Categoria, year, t, e, Idioma, client.getCarnet());
                    books.add(b);
                } else {
                    getAlert("A Book whit ISBN:" + ISBN + " has already been entered" );
                }
            }
            
            try {
                
                Socket socket = new Socket(Configuration.ipServer, Integer.parseInt("8000"));
                DataServer servidorEDD = new DataServer();
                servidorEDD.setState("Array");
                servidorEDD.setIp(Configuration.ip);
                servidorEDD.setCategoryNames(categories);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(servidorEDD);
                socket.close();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
            
            try {
                
                Socket socket = new Socket(Configuration.ipServer, Integer.parseInt("8000"));
                DataServer servidorEDD = new DataServer();
                servidorEDD.setState("Array");
                servidorEDD.setIp(Configuration.ip);
                servidorEDD.setBooks(books);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(servidorEDD);
                socket.close();
            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
              
        }
        try {
            Thread.sleep(2000);
            initTableView();
        } catch (InterruptedException ex) {
            Logger.getLogger(MyBooksController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*VALIDA SI YA EXISTE EL NOMBRE DE USUARIO O SI DEJA CAMPOS EN BLANCO*/
    public boolean getValidations() {
        if (!eIsbn.getText().isEmpty() && !eTittle.getText().isEmpty()
                && !eAutor.getText().isEmpty() && !eYear.getText().isEmpty()
                && !eEdition.getText().isEmpty()&& !eEditorial.getText().isEmpty()
                && comboCategory.getSelectionModel().getSelectedItem() != null) {
            return true;
        }
        getAlert("You can not leave fields blank.");
        return false;
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

    /**
     * LIMPIAR CAMPOS DE TEXTO
     */
    public void clearFields() {
        eIsbn.clear();
        eTittle.clear();
        eAutor.clear();
        eYear.clear();
        comboCategory.getSelectionModel().clearSelection();
        eLanguaje.clear();
        eEdition.clear();
        eEditorial.clear();
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
