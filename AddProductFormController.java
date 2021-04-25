package software1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * AddProductFormController class
 * @author Logan
 */
public class AddProductFormController implements Initializable {

    /**
     * Table and table columns for parts that are available to be associated with the current product (availableParts)
     * and for parts that have been associated with the current product (associatedParts).
     * Required text field inputs to gather data.
     */
    @FXML private TableView<Part> availableParts;
    @FXML private TableColumn<Part, Integer> availPartId;
    @FXML private TableColumn<Part, String> availPartName;
    @FXML private TableColumn<Part, Integer> availPartInv;
    @FXML private TableColumn<Part, Double> availPartPrice;
    @FXML private TableView<Part> associatedParts;
    @FXML private TableColumn<Part, Integer> assocPartId;
    @FXML private TableColumn<Part, String> assocPartName;
    @FXML private TableColumn<Part, Integer> assocPartInv;
    @FXML private TableColumn<Part, Double> assocPartPrice;
    @FXML private TextField productId;
    @FXML private TextField productName;
    @FXML private TextField productInv;
    @FXML private TextField productPrice;
    @FXML private TextField productMax;
    @FXML private TextField productMin;
    @FXML private TextField searchParts;
    Product product = new Product();


    /**
     * Attempts to search parts by part ID or part name. If user entered an integer, the part with the
     * matching part ID will be selected in the table. If user entered a string, lookupPart is called
     * and all parts with names that contain that string will be shown.
     */
    public void searchPartsAction() {
        try {
            int searchPart = Integer.parseInt(searchParts.getText());
            availableParts.getSelectionModel().clearSelection();
            availableParts.getSelectionModel().select(Inventory.lookupPart(searchPart));
        } catch (NumberFormatException e) {
            String searchPart = searchParts.getText();
            availableParts.setItems(Inventory.lookupPart(searchPart));
        }
    }

    /**
     * On add button click, selected part is copied from available parts table to associated parts table
     * in GUI and is associated with that product.
     */
    public void addButtonPushed() {
        Part selectedPart = availableParts.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }
        product.addAssociatedPart(selectedPart);
        associatedParts.setItems(product.getAllAssociatedParts());
    }

    /**
     * On remove button click, alert will display asking to confirm removal. If confirmed, part is removed
     * from the list.
     */
    public void removePartButtonPushed() {
        Part selectedPart = associatedParts.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to remove this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (!product.deleteAssociatedPart(selectedPart)) {
                Alert alert1 = new Alert(Alert.AlertType.ERROR, "The part was not removed. Try again.");
                alert1.showAndWait();
            }
        }
    }

    /**
     * On save button click, verifies correct data types and logical functionality.
     * First, checks if any field is empty. If so, an alert is displayed.
     * Second, checks if values are appropriate for each input and formats the price
     * into a double with two trailing digits (i.e. 2.34). If values are not correct,
     * catches exception and displays alert.
     * Third, checks that price is greater than 0.00 and that Min is less than Max
     * and Inv is between them.
     * Fourth, saves the product information from text fields and loads the main form.
     * @param actionEvent for changing to next scene upon button click.
     * @throws IOException for failed or interrupted I/O operations.
     */
    public void saveButtonPushed(ActionEvent actionEvent) throws IOException {
        try {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            //check if fields are empty
            if (productName.getText().isEmpty()) {
                alert.setContentText("Name should have a value.");
                alert.showAndWait();
                return;
            } else if (productInv.getText().isEmpty()) {
                alert.setContentText("Inv should have a value.");
                alert.showAndWait();
                return;
            } else if (productPrice.getText().isEmpty()) {
                alert.setContentText("Price should have a value");
                alert.showAndWait();
                return;
            } else if (productMax.getText().isEmpty()) {
                alert.setContentText("Max should have a value");
                alert.showAndWait();
                return;
            } else if (productMin.getText().isEmpty()) {
                alert.setContentText("Min should have a value");
                alert.showAndWait();
                return;
            } else { //check if values are appropriate or catch exception
                productPrice.setText(String.format("%,.2f", Double.parseDouble(productPrice.getText())));
                Integer.parseInt(productInv.getText());
                Integer.parseInt(productMax.getText());
                Integer.parseInt(productMin.getText());
            }

            //check if logic is correct
            if (Double.parseDouble(productPrice.getText()) <= 0.00) {
                alert.setContentText("Price should be greater than $0.00");
                alert.showAndWait();
            } else if (Integer.parseInt(productMin.getText()) >= Integer.parseInt(productMax.getText())) {
                alert.setContentText("Min should be less than Max");
                alert.showAndWait();
            } else if (Integer.parseInt(productInv.getText()) < Integer.parseInt(productMin.getText())
                    || Integer.parseInt(productInv.getText()) > Integer.parseInt(productMax.getText())) {
                alert.setContentText("Inv should be between Min and Max");
                alert.showAndWait();
            } else {
                product.setId(Integer.parseInt(productId.getText()));
                product.setName(productName.getText());
                product.setPrice(Double.parseDouble(productPrice.getText()));
                product.setStock(Integer.parseInt(productInv.getText()));
                product.setMax(Integer.parseInt(productMax.getText()));
                product.setMin(Integer.parseInt(productMin.getText()));

                Inventory.addProduct(product);

                //loads main form again
                Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                Parent scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
                stage.setScene(new Scene(scene));
                stage.show();
            }

        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Enter integers for Inv, Max, Machine ID, and Max: 3 \n" +
                    "Enter decimal number for Price: $1.23");
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
        productId.clear();
        productName.clear();
        productInv.clear();
        productPrice.clear();
        productMax.clear();
        productMin.clear();

        //loads main form again
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("MainForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * On load, links table and table columns to display all parts. ALso links table and table columns for
     * associated parts.
     * On load, new part ID is created and text field is populated.
     * @param url The location used to resolve relative paths for the root object, or null if the location
     *            is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was
     *                       not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //init all parts in top table
        availPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        availPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        availPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        availPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        availableParts.setItems(Inventory.getAllParts());

        assocPartId.setCellValueFactory(new PropertyValueFactory<>("id"));
        assocPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        assocPartInv.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assocPartPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        //unique product id
        productId.setText(String.valueOf(Inventory.getCustomProductId()));
    }
}
