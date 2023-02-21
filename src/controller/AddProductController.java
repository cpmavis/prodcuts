package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Inventory;
import model.Part;
import model.Product;

import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
/**
 *  Controller class that provides the logic to add a product
 *  Allows the user to add a new product and checks that the information is valid
 * @author Corey
 */
public class AddProductController implements Initializable {
    /**
     * An observable list of all parts associated with a product
     */
    private ObservableList<Part> associatedProductPartList = FXCollections.observableArrayList();
    /**
     * Text field for the Product ID
     */
    @FXML private TextField AddProductID;
    /**
     * Text field for the Product Name
     */
    @FXML private TextField AddProductName;
    /**
     * Text field for the Product Inventory
     */
    @FXML private TextField AddProductInventory;
    /**
     * Text field for the Product Price
     */
    @FXML private TextField AddProductPrice;
    /**
     * Text field for the Product Max
     */
    @FXML private TextField AddProductMax;
    /**
     * Text field for the Product Min
     */
    @FXML private TextField AddProductMin;
    /**
     * Text field for the Part to be searched
     */
    @FXML private TextField AddProductSearch;
    /**
     * Button to add part to associated parts table
     */
    @FXML private Button AddProductAddButton;
    /**
     * Button to remove part from the associated parts table
     */
    @FXML private Button AddProductRemoveButton;
    /**
     * Button to save the product
     */
    @FXML private Button AddProductSaveButton;
    /**
     * Button to exit the screen
     */
    @FXML private Button AddProductCancelButton;
    /**
     * Button to search for a product
     */
    @FXML public Button addProductSearchButton;
    /**
     * Button to refresh the parts table
     */
    @FXML public Button addProductClearButton;
    /**
     * Table view for parts
     */
    @FXML private TableView<Part> partTableView;
    /**
     * ID column for the part table
     */
    @FXML private TableColumn<Part, Integer> partIDColumn;
    /**
     * Name column for the part table
     */
    @FXML private TableColumn<Part, String> partNameColumn;
    /**
     * Inventory column for the part table
     */
    @FXML private TableColumn<Part, Integer> partInventoryColumn;
    /**
     * Cost column for the part table
     */
    @FXML private TableColumn<Part, Double> partCostColumn;
    /**
     * Table view for associated parts
     */
    @FXML private TableView<Part> associatedPartTableView ;
    /**
     * ID column for the associated part table
     */
    @FXML private TableColumn<Part, Integer> associatedPartIDColumn;
    /**
     * Name column for the associated part table
     */
    @FXML private TableColumn<Part, String> associatedPartNameColumn;
    /**
     * Inventory column for the associated part table
     */
    @FXML private TableColumn<Part, Integer> associatedPartInventoryColumn;
    /**
     * Cost column for the associated part table
     */
    @FXML private TableColumn<Part, Double> associatedPartCostColumn;
    /**
     * Instance of a random object
     */
    Random randomId =  new Random();

    /**
     * Initialize the controller and populates the tables
     * Initializes the buttons to run the methods when called
     * @param url location for the program to load the root object
     * @param resourceBundle resources used to locate the root object
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Load up the parts
        partTableView.setItems(Inventory.getAllParts());
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // Load up the associated parts table
        associatedPartTableView.setItems(associatedProductPartList);
        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        AddProductAddButton.setOnAction(e-> addButton());
        AddProductSaveButton.setOnAction(e-> saveButton());
        AddProductRemoveButton.setOnAction(e-> removeButton());
        addProductSearchButton.setOnAction(e-> partSearch());
        addProductClearButton.setOnAction(e-> refreshPart());

        AddProductID.setDisable(true);
        AddProductID.setText(String.valueOf(randomIDGenerator()));
        AddProductCancelButton.setOnAction(e->
                cancelButton());
    }
    /**
     * Adds the selected part to the associated parts table
     */
    private void addButton(){
        Part selectedPart = partTableView.getSelectionModel().getSelectedItem();
        associatedProductPartList.add(selectedPart);
        associatedPartTableView.setItems(associatedProductPartList);

    }
    /**
     * Saves the new product to the Inventory
     * Validates that all in the inputs are valid and not null
     */
    private void saveButton(){

        int productID;
        String productName = AddProductName.getText();
        double productPrice;
        int productInventory;
        int productMin;
        int productMax;

        if (validInt(AddProductID)) productID = Integer.parseInt(AddProductID.getText());
        else return;
        if (validPrice(AddProductPrice)) productPrice = Double.parseDouble(AddProductPrice.getText());
        else return;
        if (validInventory(AddProductInventory)) productInventory = Integer.parseInt(AddProductInventory.getText());
        else return;
        if (validMin(AddProductMin)) productMin = Integer.parseInt(AddProductMin.getText());
        else return;
        if (validMax(AddProductMax)) productMax = Integer.parseInt(AddProductMax.getText());
        else return;
        if (inventoryChecker(productMin, productMax, productInventory)) ;
        else return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Save");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to save the product?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(null) == ButtonType.OK) {
            Product newProduct = new Product(productID, productName, productPrice, productInventory, productMin, productMax);
            for (Part part : associatedProductPartList) {
                newProduct.addAssociatedPart(part);
            }
            Inventory.addProduct(newProduct);
            final Stage stage = (Stage) AddProductSaveButton.getScene().getWindow();
            stage.close();
        }
    }
    /**
     * Checks that the Integer is valid
     * @param textField text field for the integer
     * @return boolean value if it is a valid integer
     */
    private boolean validInt(TextField textField) {
        if (textField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("ID is empty");
            alert.showAndWait();
            return false;
        }
        {
            try {
                Integer.parseInt(textField.getText());
                return true;
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("ID is not a number");
                alert.showAndWait();
                return false;
            }
        }
    }
    /**
     * Checks that the Double is valid
     * @param textField text field for the price
     * @return boolean value if it is a valid double
     */
    private boolean validPrice(TextField textField) {
        if (textField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Price field is empty");
            alert.showAndWait();
            return false;
        }
        {
            try {
                Double.parseDouble(textField.getText());
                return true;
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Price is not a number");
                alert.showAndWait();
                return false;
            }
        }
    }
    /**
     * Checks that the Inventory is valid
     * @param textField text field for the inventory
     * @return boolean value if it is a valid integer
     */
    private boolean validInventory(TextField textField) {
        if (textField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Inventory field is empty");
            alert.showAndWait();
            return false;
        }
        {
            try {
                Integer.parseInt(textField.getText());
                return true;
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Inventory is not a number");
                alert.showAndWait();
                return false;
            }
        }
    }
    /**
     * Checks that the Minimum is valid
     * @param textField text field for the minimum
     * @return boolean value if it is a valid integer
     */
    private boolean validMin(TextField textField) {
        if (textField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Minimum field is empty");
            alert.showAndWait();
            return false;
        }
        {
            try {
                Integer.parseInt(textField.getText());
                return true;
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Minimum is not a number");
                alert.showAndWait();
                return false;
            }
        }
    }
    /**
     * Checks that the Max is valid
     * @param textField text field for the integer
     * @return boolean value if it is a valid integer
     */
    private boolean validMax(TextField textField) {
        if (textField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Max field is empty");
            alert.showAndWait();
            return false;
        }
        {
            try {
                Integer.parseInt(textField.getText());
                return true;
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Max is not a number");
                alert.showAndWait();
                return false;
            }
        }
    }
    /**
     * Checks the inventory against the maximum and minimum
     * @param inventory the number in stock
     * @param max the maximum number
     * @param min the minimum number
     * @return boolean value that the Inventory, max, and min are all valid
     */
    private boolean inventoryChecker(int min, int max, int inventory) {
        if (max < inventory) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Maximum is lower than the Inventory");
            alert.showAndWait();
            return false;
        } else if (min > max) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Minimum is higher than the Maximum");
            alert.showAndWait();
            return false;
        } else if (min > inventory) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Minimum is higher than the Inventory");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }
    }
    /**
     * Sets a random ID for the new part
     * If the ID is already present, it reruns the method
     * @return randomID generated for a new part
     */
    private int randomIDGenerator (){
        int randomID;
        randomID = randomId.nextInt(1, 999);
        for (Part p :Inventory.getAllParts()){
            if (p.getId() == randomID){
                randomIDGenerator();
            }
        }
        return randomID;
    }
    /**
     * Removes the part from the associated part table
     */
    private void removeButton(){
        Part selectedPart = associatedPartTableView.getSelectionModel().getSelectedItem();
        associatedProductPartList.remove(selectedPart);
    }
    /**
     * Closes the Add Product Screen
     */
    private void cancelButton(){

        final Stage stage = (Stage) AddProductCancelButton.getScene().getWindow();
        stage.close();
    };
    /**
     * Searches for a part in the Inventory
     */
    public void partSearch() {

        String partSearchText = AddProductSearch.getText();
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
     * Reloads the part table
     */
    public void refreshPart(){
        partTableView.setItems(Inventory.getAllParts());
        AddProductSearch.setText(null);
    }
}