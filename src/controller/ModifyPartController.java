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
import java.util.ResourceBundle;

/**
 *  Controller class that provides the logic to modify a part
 *  Allows the user to modify a preexisting part and checks that the information is valid
 * @author Corey
 */
public class ModifyPartController implements Initializable {
    /**
     * Label that switches between Company Name and Machine ID
     */
    @FXML
    private Label LabelChanger;
    /**
     * Radio button to select In House
     */
    @FXML
    private RadioButton ModifyPartInHouseRadioButton;
    /**
     * Radio Button to select Out Sourced
     */
    @FXML
    private RadioButton ModifyPartOutsourcedRadioButton;
    /**
     * Text field for the Part ID
     */
    @FXML
    private TextField ModifyPartID;
    /**
     * Text field for the Part Name
     */
    @FXML
    private TextField ModifyPartName;
    /**
     * Text field for the Part Inventory
     */
    @FXML
    private TextField ModifyPartInventory;
    /**
     * Text field for the Part Price
     */
    @FXML
    private TextField ModifyPartPrice;
    /**
     * Text field for the Part Max stock
     */
    @FXML
    private TextField ModifyPartMax;
    /**
     * Text field for the Part Min stock
     */
    @FXML
    private TextField ModifyPartMin;
    /**
     * Text field for MachineID/Company Name
     */
    @FXML
    private TextField ModifyPartSpecial;
    /**
     * Button to save the part
     */
    @FXML
    private Button ModifyPartSaveButton;
    /**
     * Button to exit the screen
     */
    @FXML
    private Button ModifyPartCancelButton;
    /**
     * Integer to track index of the part
     */
    private int updateIndex = 0;
    /**
     * Calls the information from the selected part in the main screen
     * @param partIndex index of the part in inventory
     * @param part part selected
     */
    @FXML
    public void partToModify(int partIndex, Part part){

        updateIndex = partIndex;

        if (part instanceof InHouse){
            ModifyPartInHouseRadioButton.setSelected(true);
            LabelChanger.setText("MachineID");
            ModifyPartSpecial.setText(String.valueOf(((InHouse) part).getMachineID()));
        }
        else{
            ModifyPartOutsourcedRadioButton.setSelected(true);
            LabelChanger.setText("Company Name");
            ModifyPartSpecial.setText(((Outsourced) part).getCompanyName());
        }
        ModifyPartID.setText(String.valueOf(part.getId()));
        ModifyPartName.setText(part.getName());
        ModifyPartInventory.setText(String.valueOf(part.getStock()));
        ModifyPartPrice.setText(String.valueOf(part.getPrice()));
        ModifyPartMax.setText(String.valueOf(part.getMax()));
        ModifyPartMin.setText(String.valueOf(part.getMin()));
    }
    /**
     * Initialize the controller and populates the tables
     * Initializes the buttons to run the methods when called
     * @param url location for the program to load the root object
     * @param resourceBundle resources used to locate the root object
     */
    @Override
    public void initialize (URL url, ResourceBundle resourceBundle) {

        ToggleGroup toggleGroup = new ToggleGroup();
        ModifyPartInHouseRadioButton.setToggleGroup(toggleGroup);
        ModifyPartOutsourcedRadioButton.setToggleGroup(toggleGroup);
        ModifyPartID.setEditable(false);

        //Changes the text to the appropriate name depending on which radio button is toggled
        ModifyPartInHouseRadioButton.setOnAction(e ->
            LabelChanger.setText("MachineID"));
        ModifyPartOutsourcedRadioButton.setOnAction(e ->
            LabelChanger.setText("Company Name"));
        ModifyPartSaveButton.setOnAction(e->
                saveModificationButton());
        ModifyPartCancelButton.setOnAction(e ->
                cancelButton());
    }
    /**
     * Saves the modifications of the part that was selected
     * Validates that all in the inputs are valid and not null
     */
    private void saveModificationButton(){
        int partID;
        String partName = ModifyPartName.getText();
        double partPrice;
        int partInventory;
        int partMin;
        int partMax;
        String companyName;
        int machineID;

        if (validInt(ModifyPartID)) partID = Integer.parseInt(ModifyPartID.getText());
        else return;
        if (validPrice(ModifyPartPrice)) partPrice = Double.parseDouble(ModifyPartPrice.getText());
        else return;
        if (validInventory(ModifyPartInventory)) partInventory = Integer.parseInt(ModifyPartInventory.getText());
        else return;
        if (validMin(ModifyPartMin)) partMin = Integer.parseInt(ModifyPartMin.getText());
        else return;
        if (validMax(ModifyPartMax)) partMax = Integer.parseInt(ModifyPartMax.getText());
        else return;
        if (inventoryChecker(partMin, partMax, partInventory));
        else return;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm Save");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to save the part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.orElse(null) == ButtonType.OK) {
            if (ModifyPartOutsourcedRadioButton.isSelected()) {
                if (validCompanyName(ModifyPartSpecial)) companyName = ModifyPartSpecial.getText();
                else return;
                Inventory.updatePart(updateIndex, new Outsourced(partID, partName, partPrice, partInventory, partMin, partMax, companyName));
            } else {
                if (validMachineID(ModifyPartSpecial)) machineID = Integer.parseInt(ModifyPartSpecial.getText());
                else return;
                Inventory.updatePart(updateIndex, new InHouse(partID, partName, partPrice, partInventory, partMin, partMax, machineID));
            }
        }
        final Stage stage = (Stage) ModifyPartSaveButton.getScene().getWindow();
        stage.close();
    }
    /**
     * Checks that the Integer is valid
     * @param textField text field for the integer
     * @return boolean value if it is a valid integer
     */
    private boolean validInt (TextField textField){
        if (textField.getText().isEmpty()){
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
            }catch (NumberFormatException e){
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
    private boolean validPrice (TextField textField){
        if (textField.getText().isEmpty()){
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
            }catch (NumberFormatException e){
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
    private boolean validInventory (TextField textField){
        if (textField.getText().isEmpty()){
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
            }catch (NumberFormatException e){
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
    private boolean validMin (TextField textField){
        if (textField.getText().isEmpty()){
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
            }catch (NumberFormatException e){
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
    private boolean validMax (TextField textField){
        if (textField.getText().isEmpty()){
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
            }catch (NumberFormatException e){
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
    private boolean validCompanyName (TextField textField) {

        if (textField.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Company Name is empty");
            alert.showAndWait();
            return false;
        }
        else {
            return true;
        }

    }
    /**
     * Checks that the Machine ID is valid
     * @param textField text field for the machine ID
     * @return boolean value if it is a valid integer
     */
    private boolean validMachineID (TextField textField){
        if (textField.getText().isEmpty()){
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
            }catch (NumberFormatException e){
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
    private boolean inventoryChecker (int min, int max, int inventory){
        if (max < inventory){
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
     * Button to exit the screen
     */
    private void cancelButton() {

        final Stage stage = (Stage) ModifyPartCancelButton.getScene().getWindow();
        stage.close();
    }
}