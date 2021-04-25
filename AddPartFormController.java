package software1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * AddPartFormController class
 * @author Logan
 */
public class AddPartFormController implements Initializable {

    /**
     * Radio buttons, label, and toggle group for changing between
     * Machine ID and Company Name. Required text field inputs to gather data.
     */
    @FXML private RadioButton inHouseRadioButton;
    @FXML private RadioButton outsourcedRadioButton;
    @FXML private TextField partId;
    @FXML private TextField partName;
    @FXML private TextField partInv;
    @FXML private TextField partPrice;
    @FXML private TextField partMax;
    @FXML private TextField partMin;
    @FXML private TextField partMachineCompany;
    @FXML private Label partRadioButtonLabel;
    private ToggleGroup partToggleGroup;


    /**
     * On radio button click, the label text is changed to match.
     */
    public void radioButtonPushed() {
        if (partToggleGroup.getSelectedToggle().equals(inHouseRadioButton)) {
            partRadioButtonLabel.setText("Machine ID");
        }
        if (partToggleGroup.getSelectedToggle().equals(outsourcedRadioButton)) {
            partRadioButtonLabel.setText("Company Name");
        }
    }

    /**
     * On save button click, verifies correct data types and logical functionality.
     * First, checks if any field is empty. If so, an alert is displayed.
     * Second, checks if values are appropriate for each input and formats
     * the price into a double with two trailing digits (i.e. 2.34). If values are not
     * correct, catches exception and displays alert.
     * Third, checks that price is greater than 0.00 and that Min is less than Max
     * and Inv is between them.
     * Fourth, saves the part information from text fields based on radio button selection
     * and loads the main form.
     * <p>
     * Upon testing, determined that if an integer (2) or double with one trailing digit (2.2)
     * were entered into price text field, Java automatically truncates double to only display
     * one trailing digit (i.e. 2.0 or 2.2) instead of 2.00 or 2.20. Left as-is so the data
     * types would match the UML diagram. Would correct by changing set type and table column
     * to String. Text field input would be checked as a double, formatted by "%,.2f", then would
     * be saved and edited as a String from then on.
     * @param actionEvent for changing to next scene upon button click.
     * @throws IOException for failed or interrupted I/O operations.
     */
    public void saveButtonPushed(ActionEvent actionEvent) throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            //check if fields are empty
            if (partName.getText().isEmpty()) {
                alert.setContentText("Name should have a value.");
                alert.showAndWait();
                return;
            } else if (partInv.getText().isEmpty()) {
                alert.setContentText("Inv should have a value.");
                alert.showAndWait();
                return;
            } else if (partPrice.getText().isEmpty()) {
                alert.setContentText("Price should have a value");
                alert.showAndWait();
                return;
            } else if (partMax.getText().isEmpty()) {
                alert.setContentText("Max should have a value");
                alert.showAndWait();
                return;
            } else if (partMin.getText().isEmpty()) {
                alert.setContentText("Min should have a value");
                alert.showAndWait();
                return;
            } else if (partToggleGroup.getSelectedToggle().equals(inHouseRadioButton) &&
                    partMachineCompany.getText().isEmpty()) {
                alert.setContentText("Machine ID should have a value");
                alert.showAndWait();
                return;
            } else if (partToggleGroup.getSelectedToggle().equals(outsourcedRadioButton) &&
                    partMachineCompany.getText().isEmpty()) {
                alert.setContentText("Company Name should have a value");
                alert.showAndWait();
                return;
            }

            //check if values are appropriate or catch exception
            if (partToggleGroup.getSelectedToggle().equals(inHouseRadioButton)) {
                partPrice.setText(String.format("%,.2f", Double.parseDouble(partPrice.getText())));
                Integer.parseInt(partInv.getText());
                Integer.parseInt(partMax.getText());
                Integer.parseInt(partMin.getText());
                Integer.parseInt(partMachineCompany.getText());
            } else if (partToggleGroup.getSelectedToggle().equals(outsourcedRadioButton)) {
                partPrice.setText(String.format("%,.2f", Double.parseDouble(partPrice.getText())));
                Integer.parseInt(partInv.getText());
                Integer.parseInt(partMax.getText());
                Integer.parseInt(partMin.getText());
            }

            //check if logic is correct
            if (Double.parseDouble(partPrice.getText()) <= 0.00) {
                alert.setContentText("Price should be greater than $0.00");
                alert.showAndWait();
            } else if (Integer.parseInt(partMin.getText()) >= Integer.parseInt(partMax.getText())) {
                alert.setContentText("Min should be less than Max");
                alert.showAndWait();
            } else if (Integer.parseInt(partInv.getText()) < Integer.parseInt(partMin.getText())
                    || Integer.parseInt(partInv.getText()) > Integer.parseInt(partMax.getText())) {
                alert.setContentText("Inv should be between Min and Max");
                alert.showAndWait();
            } else {
                if (partToggleGroup.getSelectedToggle().equals(inHouseRadioButton)) {
                    Part newPart = new InHouse(Integer.parseInt(partId.getText()),
                            partName.getText(),
                            Double.parseDouble(partPrice.getText()),
                            Integer.parseInt(partInv.getText()),
                            Integer.parseInt(partMin.getText()),
                            Integer.parseInt(partMax.getText()),
                            Integer.parseInt(partMachineCompany.getText())
                    );
                    Inventory.addPart(newPart);
                }
                if (partToggleGroup.getSelectedToggle().equals(outsourcedRadioButton)) {
                    Part newPart = new Outsourced(Integer.parseInt(partId.getText()),
                            partName.getText(),
                            Double.parseDouble(partPrice.getText()),
                            Integer.parseInt(partInv.getText()),
                            Integer.parseInt(partMin.getText()),
                            Integer.parseInt(partMax.getText()),
                            partMachineCompany.getText()
                    );
                    Inventory.addPart(newPart);
                }

                //loads main form again
                Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter integers for Inv, Max, Machine ID, and Max: 3 \n"
                    + "Enter decimal number for Price: $1.23");
            alert.showAndWait();
        }
    }

    /**
     * On cancel button click, data in text fields are cleared and next scene is loaded.
     * @param actionEvent for changing to next scene upon button click.
     * @throws IOException for failed or interrupted I/O operations.
     */
    public void cancelButtonPushed(ActionEvent actionEvent) throws IOException {
        //clears screen
        partId.clear();
        partName.clear();
        partInv.clear();
        partPrice.clear();
        partMax.clear();
        partMin.clear();
        partMachineCompany.clear();

        //loads main form again
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * On load, the Machine ID radio button is selected by default and the corresponding text is displayed
     * in the label.
     * On load, new part ID is created and text field is populated.
     * @param url The location used to resolve relative paths for the root object, or null if the location
     *            is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was
     *                       not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        partRadioButtonLabel.setText("Machine ID");

        //radio button toggle group
        partToggleGroup = new ToggleGroup();
        this.inHouseRadioButton.setToggleGroup(partToggleGroup);
        this.outsourcedRadioButton.setToggleGroup(partToggleGroup);
        inHouseRadioButton.setSelected(true);

        //unique part id
        partId.setText(String.valueOf(Inventory.getCustomPartId()));
    }
}
