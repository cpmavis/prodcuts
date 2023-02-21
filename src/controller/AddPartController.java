package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.InHouse;
import model.Inventory;
import model.Outsourced;
import model.Part;

import java.net.URL;
import java.util.Optional;
import java.util.Random;
import java.util.ResourceBundle;
/**
 *  Controller class that provides the logic to add a part
 *  Allows the user to add a new part and checks that the information is valid
 * @author Corey
 */
public class AddPartController implements Initializable {
    /**
     * Label that switches between Company Name and Machine ID
     */
    @FXML
    private Label LabelChanger;
    /**
     * Radio button to select In House
     */
    @FXML
    private RadioButton AddPartInHouseRadioButton;
    /**
     * Radio Button to select Out Sourced
     */
    @FXML
    private RadioButton AddPartOutsourcedRadioButton;
    /**
     * Text field for the Part ID
     */
    @FXML
    private TextField AddPartID;
    /**
     * Text field for the Part Name
     */
    @FXML
    private TextField AddPartName;
    /**
     * Text field for the Part Inventory
     */
    @FXML
    private TextField AddPartInventory;
    /**
     * Text field for the Part Price
     */
    @FXML
    private TextField AddPartPrice;
    /**
     * Text field for the Part Max stock
     */
    @FXML
    private TextField AddPartMax;
    /**
     * Text field for the Part Min stock
     */
    @FXML
    private TextField AddPartMin;
    /**
     * Text field for MachineID/Company Name
     */
    @FXML
    private TextField AddPartSpecial;
    /**
     * Button to save the part
     */
    @FXML
    private Button AddPartSaveButton;
    /**
     * Button to exit the screen
     */
    @FXML
    private Button AddPartCancelButton;
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

        ToggleGroup toggleGroup = new ToggleGroup();
        AddPartInHouseRadioButton.setToggleGroup(toggleGroup);
        AddPartOutsourcedRadioButton.setToggleGroup(toggleGroup);
        AddPartInHouseRadioButton.setSelected(true);

        AddPartInHouseRadioButton.setOnAction(e -> LabelChanger.setText("MachineID"));
        AddPartOutsourcedRadioButton.setOnAction(e -> LabelChanger.setText("Company Name"));
        AddPartCancelButton.setOnAction(e -> cancelButton());
        AddPartSaveButton.setOnAction(e -> savePartButtonClicked());
        AddPartID.setDisable(true);
        AddPartID.setText(String.valueOf(randomIDGenerator()));
    }
    /**
     * Exits the add part screen
     */
    private void cancelButton() {
        final Stage stage = (Stage) AddPartCancelButton.getScene().getWindow();
        stage.close();
    }
    /**
     * Saves the new part to the Inventory
     * Validates that all in the inputs are valid and not null
     */
    private void savePartButtonClicked() {

        int partID;
        String partName = AddPartName.getText();
        double partPrice;
        int partInventory;
        int partMin;
        int partMax;
        String companyName;
        int machineID;

        if (validInt(AddPartID)) partID = Integer.parseInt(AddPartID.getText());
        else return;
        if (validPrice(AddPartPrice)) partPrice = Double.parseDouble(AddPartPrice.getText());
        else return;
        if (validInventory(AddPartInventory)) partInventory = Integer.parseInt(AddPartInventory.getText());
        else return;
        if (validMin(AddPartMin)) partMin = Integer.parseInt(AddPartMin.getText());
        else return;
        if (validMax(AddPartMax)) partMax = Integer.parseInt(AddPartMax.getText());
        else return;
        if (inventoryChecker(partMin, partMax, partInventory)) ;
        else return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Save");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to save the part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(null) == ButtonType.OK) {
            if (AddPartOutsourcedRadioButton.isSelected()) {
                if (validCompanyName(AddPartSpecial)) companyName = AddPartSpecial.getText();
                else return;
                Inventory.addPart(new Outsourced(partID, partName, partPrice, partInventory, partMin, partMax, companyName));
            } else {
                if (validMachineID(AddPartSpecial)) machineID = Integer.parseInt(AddPartSpecial.getText());
                else return;
                Inventory.addPart(new InHouse(partID, partName, partPrice, partInventory, partMin, partMax, machineID));
            }
            final Stage stage = (Stage) AddPartSaveButton.getScene().getWindow();
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
     * Checks that the text field is not empty
     * @param textField text field for the company name
     * @return boolean value if it is a valid string
     */
    private boolean validCompanyName(TextField textField) {

        if (textField.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Company Name is empty");
            alert.showAndWait();
            return false;
        } else {
            return true;
        }

    }
    /**
     * Checks that the Machine ID is valid
     * @param textField text field for the machine ID
     * @return boolean value if it is a valid integer
     */
    private boolean validMachineID(TextField textField) {
        if (textField.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Machine ID is empty");
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
                alert.setContentText("Machine ID is not a number");
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
}