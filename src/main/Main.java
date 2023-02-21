/*
JAVADOCS LOCATED IN JAVADOCS FOLDER!!!!!!
 */

package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Inventory Management system
 * Loads up all the information
 * @author Corey
 * --- FUTURE IMPROVEMENT ---
 * One change that could be made to the product is to have realtime inventory tracking. When a part is added to a product,
 * it would be good to see that inventory for the product decrease, and if the product no longer needs that part, have the
 * inventory increase for that part.
 */
public class Main extends Application {
    /**
     * Creates and loads up the initial stage
     * @param stage first stage to be loaded
     * @throws Exception throws an exception if any errors occurs
     */
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 1280, 800));
        stage.show();
    }
    /**
     * Launches the application
     * @param args arguments
     */
    public static void main(String[] args){
        launch(args);
    }
}

