package software1;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Main class
 * @author Logan
 * A compatible feature to extend functionality could be a button on the main form next
 * to the delete product button. After clicking delete, and being prompted by the error
 * that states the product has an associated part and cannot be deleted, this new button
 * would disassociate all associated parts from that product without having to go to the
 * modify product form, remove parts one by one while being prompted each time, and then
 * re-save the product. 
 */
public class Main extends Application {

    /**
     * Starts GUI application by launching MainForm.fxml.
     * @param stage creates new stage for the main form.
     * @throws IOException for failed or interrupted I/O operations.
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        Scene mainForm = new Scene(root);
        stage.setScene(mainForm);
        stage.show();
    }

    /**
     * Main function to start application.
     * @param args standard input.
     */
    public static void main(String[] args) {

        launch(args);
    }
}
