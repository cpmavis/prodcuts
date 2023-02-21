package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Controller class that provides logic for the application to run
 * Allows for users to search, add, and delete both parts and products and allow for searching for both as well
 * @author Corey
 */
public class MainScreenController implements Initializable {


    /**
     * Button to search for parts
     */
    public Button partSearchButton;
    /**
     * Button to refresh the parts
     */
    public Button partRefreshButton;
    /**
     * Button to search for products
     */
    public Button productSearchButton;
    /**
     * Button to refresh the products
     */
    public Button productRefreshButton;
    /**
     * The text field for the parts to be searched
     */
    @FXML
    private TextField MainScreenPartsSearch;
    /**
     * The text field for the products to be searched
     */
    @FXML
    private TextField MainScreenProductsSearch;
    /**
     * Button to exit the program
     */
    @FXML
    private Button MainScreenExitButton;
    /**
     * Button to add a part
     */
    @FXML
    private Button MainScreenPartsAddButton;
    /**
     * Button to modify a part
     */
    @FXML
    private Button MainScreenPartsModifyButton;
    /**
     * Button to delete a part
     */
    @FXML
    private Button MainScreenPartsDeleteButton;
    /**
     * Button to add a product
     */
    @FXML
    private Button MainScreenProductsAddButton;
    /**
     * Button to modify a product
     */
    @FXML
    private Button MainScreenProductsModifyButton;
    /**
     * Button to delete a product
     */
    @FXML
    private Button MainScreenProductsDeleteButton;
    /**
     * Table view for the parts
     */
    @FXML
    private TableView<Part> partTableView;
    /**
     * ID column for the part table
     */
    @FXML
    private TableColumn<Part, Integer> partIDColumn;
    /**
     * Name column for the part table
     */
    @FXML
    private TableColumn<Part, String> partNameColumn;
    /**
     * Inventory column for the part table
     */
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;
    /**
     * Cost column for the part table
     */
    @FXML
    private TableColumn<Part, Double> partCostColumn;
    /**
     * Table view for the products
     */
    @FXML
    public TableView<Product> productTableView;
    /**
     * ID column for the product table
     */
    @FXML
    public TableColumn<Product, Integer> productIDColumn;
    /**
     * Name column for the product table
     */
    @FXML
    public TableColumn<Product, String> productNameColumn;
    /**
     * Inventory column for the product table
     */
    @FXML
    public TableColumn<Product, Integer> productInventoryColumn;
    /**
     * Cost column for the product table
     */
    @FXML
    public TableColumn<Product, Double> productCostColumn;
    /**
     * Initialize the controller and populates the tables
     * Initializes the buttons to run the methods when called
     * @param url location for the program to load the root object
     * @param resourceBundle resources used to locate the root object
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Load up the parts table
        partTableView.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Load up the product table
        productTableView.setItems(Inventory.getAllProducts());
        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Buttons for parts Screen
        MainScreenPartsAddButton.setOnAction(e -> addPartScreen());
        MainScreenPartsModifyButton.setOnAction(e -> modifyPartScreen());
        MainScreenPartsDeleteButton.setOnAction(e -> deletePart());
        partSearchButton.setOnAction(e-> partSearch());

        //Buttons for products Screen
        MainScreenProductsAddButton.setOnAction(e -> addProductScreen());
        MainScreenProductsModifyButton.setOnAction(e -> modifyProductScreen());
        MainScreenProductsDeleteButton.setOnAction(e -> deleteProduct());
        productSearchButton.setOnAction(e-> productSearch());

        partRefreshButton.setOnAction(e-> refreshPart());
        productRefreshButton.setOnAction(e-> refreshProduct());

        MainScreenExitButton.setOnAction(e -> exitScreen());

    }
    /**
     * Loads the AddPartController
     */
    public void addPartScreen() {
        Parent addPartsParent;
        try {
            addPartsParent = FXMLLoader.load(getClass().getResource("/view/AddPartView.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Scene scene = new Scene(addPartsParent);
        Stage stage = new Stage();
        stage.setTitle("Add Parts");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    /**
     * Loads the ModifyPartController
     */
    public void modifyPartScreen() {

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyPartView.fxml"));
            loader.load();


            ModifyPartController MPC = loader.getController();
            MPC.partToModify(partTableView.getSelectionModel().getSelectedIndex(), partTableView.getSelectionModel().getSelectedItem());

            Stage stage = new Stage();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select a part before you can modify it!");
            alert.show();
        }

    }
    /**
     * Loads the AddProductController
     */
    public void addProductScreen() {
        Parent addPartsParent;
        try {
            addPartsParent = FXMLLoader.load(getClass().getResource("/view/AddProductView.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Scene scene = new Scene(addPartsParent);
        Stage stage = new Stage();
        stage.setTitle("Add Parts");
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    /**
     * Loads the ModifyProductController
     */
    public void modifyProductScreen() {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/view/ModifyProductView.fxml"));
            loader.load();


            ModifyProductController MPC = loader.getController();
            MPC.productToModify(productTableView.getSelectionModel().getSelectedIndex(), productTableView.getSelectionModel().getSelectedItem());

            Stage stage = new Stage();
            Parent scene = loader.getRoot();
            stage.setScene(new Scene(scene));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (NullPointerException | IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("You must select a product before you can modify it!");
            alert.show();
        }
    }
    /**
     * Deletes the part the user selected in the part table view
     */
    public void deletePart () {
        Part part = partTableView.getSelectionModel().getSelectedItem();
        if (part == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No part selected");
            alert.setHeaderText(null);
            alert.setContentText("No part selected");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to delete the part?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.orElse(null) == ButtonType.OK) {
                Part deletePart = partTableView.getSelectionModel().getSelectedItem();
                Inventory.deletePart(deletePart);
            }
        }
    }
    /**
     * Deletes the product the user selected in the product table view
     */
    public void deleteProduct () {
        Product product = productTableView.getSelectionModel().getSelectedItem();
        if (product == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No product selected");
            alert.setHeaderText(null);
            alert.setContentText("No product selected");
            alert.showAndWait();
        } else {
            if (!product.getAllAssociatedParts().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Product cant be deleted while it has a part associated with it");
                alert.showAndWait();
            } else {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Delete");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete the product?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.orElse(null) == ButtonType.OK) {
                    Product deleteProduct = productTableView.getSelectionModel().getSelectedItem();
                    Inventory.deleteProduct(deleteProduct);
                }
            }
        }
    }
    /**
     * Searches for a part in the Inventory
     */
    public void partSearch() {
        // If no string found, returns correct result. Shows empty list for incorrect Integer
        // Had to add warning for no int found
        String partSearchText = MainScreenPartsSearch.getText();
        ObservableList<Part> foundPart = Inventory.lookupPart(partSearchText);
        if (partSearchText.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Nothing Selected!");
            alert.showAndWait();
        }
        try{
            if (foundPart.isEmpty()) {
                int partIDSearched = Integer.parseInt(partSearchText);
                Part p = Inventory.lookupPart(partIDSearched);
                foundPart.add(p);
                if (p != null) {
                    partTableView.setItems(foundPart);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("No parts found");
                    alert.showAndWait();
                }
            }
            else {
                partTableView.setItems(foundPart);
            }

        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("No parts found");
            alert.showAndWait();
        }
    }
    /**
     * Searches for a product in the Inventory
     */
    public void productSearch() {
        String productSearchText = MainScreenProductsSearch.getText();
        ObservableList<Product> foundProduct = Inventory.lookupProduct(productSearchText);
        if (productSearchText.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("Nothing Selected!");
            alert.showAndWait();
        }
        try{
            if (foundProduct.isEmpty()) {
                int partIDSearched = Integer.parseInt(productSearchText);
                Product p = Inventory.lookupProduct(partIDSearched);
                foundProduct.add(p);
                if (p != null) {
                    productTableView.setItems(foundProduct);
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setHeaderText(null);
                    alert.setContentText("No products found");
                    alert.showAndWait();
                }
            }
            else {
                productTableView.setItems(foundProduct);
            }

        } catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setHeaderText(null);
            alert.setContentText("No products found");
            alert.showAndWait();
        }
    }
    /**
     * Reloads the parts table
     */
    public void refreshPart(){
        partTableView.setItems(Inventory.getAllParts());
        MainScreenPartsSearch.setText(null);
    }
    /**
     * Reloads the product table
     */
    public void refreshProduct(){
        productTableView.setItems(Inventory.getAllProducts());
        MainScreenProductsSearch.setText(null);
    }
    /**
     * Exits the screen
     */
    public void exitScreen () {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to exit?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(null) == ButtonType.OK) {
            System.exit(0);
        }
        }
    }

