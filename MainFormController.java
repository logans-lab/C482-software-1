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
 * MainFormController class
 * @author Logan
 */
public class MainFormController implements Initializable {

    /**
     * Table and table columns for parts that are available to be associated with the current product (availableParts)
     * and for parts that have been associated with the current product (associatedParts).
     * Required text field inputs for searching corresponding tables.
     */
    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, Integer> partsPartID;
    @FXML private TableColumn<Part, String> partsPartName;
    @FXML private TableColumn<Part, Integer> partsInventoryLevel;
    @FXML private TableColumn<Part, Double> partsPricePerUnit;
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, Integer> productsProductID;
    @FXML private TableColumn<Product, String> productsProductName;
    @FXML private TableColumn<Product, Integer> productsInventoryLevel;
    @FXML private TableColumn<Product, Double> productsPricePerUnit;
    @FXML private TextField searchParts;
    @FXML private TextField searchProducts;

    /**
     * Attempts to search parts by part ID or part name. If user entered an integer, the part with the
     * matching part ID will be selected in the table. If user entered a string, lookupPart is called
     * and all parts with names that contain that string will be shown.
     */
    public void searchPartsAction() {
        try {
            int searchPart = Integer.parseInt(searchParts.getText());
            partsTable.getSelectionModel().clearSelection();
            partsTable.getSelectionModel().select(Inventory.lookupPart(searchPart));
        } catch (NumberFormatException e) {
            String searchPart = searchParts.getText();
            partsTable.setItems(Inventory.lookupPart(searchPart));
        }
    }

    /**
     * Attempts to search products by product ID or product name. If user entered an integer, the part with the
     * matching product ID will be selected in the table. If user entered a string, lookupProduct is called
     * and all products with names that contain that string will be shown.
     */
    public void searchProductsAction() {
        try {
            int searchProduct = Integer.parseInt(searchProducts.getText());
            productsTable.getSelectionModel().clearSelection();
            productsTable.getSelectionModel().select(Inventory.lookupProduct(searchProduct));
        } catch (NumberFormatException e) {
            String searchProduct = searchProducts.getText();
            productsTable.setItems(Inventory.lookupProduct(searchProduct));
        }
    }

    /**
     * On add part button click, next scene is loaded.
     * @param actionEvent for changing to next scene upon button click.
     * @throws IOException for failed or interrupted I/O operations.
     */
    public void addPartButtonPushed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("AddPartForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * On modify part button click, passes selected part to initData method of the
     * ModifyPartFormController to initialize that form with selected data. Then next
     * scene is loaded.
     * @param actionEvent for changing to next scene upon button click.
     * @throws IOException for failed or interrupted I/O operations.
     */
    public void modifyPartButtonPushed(ActionEvent actionEvent) throws IOException {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }

        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyPartForm.fxml"));
        Parent scene = loader.load();
        stage.setScene(new Scene(scene));

        ModifyPartFormController controller = loader.getController();
        controller.initData(partsTable.getSelectionModel().getSelectedItem());
        stage.show();
    }

    /**
     * On delete part button click, verifies user wished to delete selected part. If so,
     * deletes selected part from parts list. If part was not deleted, displays error.
     */
    public void deletePartButtonPushed() {
        Part selectedPart = partsTable.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (Inventory.deletePart(selectedPart)) {
                partsTable.refresh();
            } else {
                Alert alert1 = new Alert(Alert.AlertType.ERROR, "The part was not deleted. Try again.");
                alert1.showAndWait();
            }
        }
    }

    /**
     * On add product button click, changes GUI to add product form.
     * @param actionEvent for changing to next scene upon button click.
     * @throws IOException for failed or interrupted I/O operations.
     */
    public void addProductButtonPushed(ActionEvent actionEvent) throws IOException {
        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        Parent scene = FXMLLoader.load(getClass().getResource("AddProductForm.fxml"));
        stage.setScene(new Scene(scene));
        stage.show();
    }

    /**
     * On modify product button click, passes selected product to initData method of the
     * ModifyProductFormController to initialize that form with selected data. Then next
     * scene is loaded.
     * @param actionEvent for changing to next scene upon button click.
     * @throws IOException for failed or interrupted I/O operations.
     */
    public void modifyProductButtonPushed(ActionEvent actionEvent) throws IOException {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            return;
        }

        Stage stage = (Stage)((Button)actionEvent.getSource()).getScene().getWindow();
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("ModifyProductForm.fxml"));
        Parent scene = loader.load();
        stage.setScene(new Scene(scene));

        ModifyProductFormController controller = loader.getController();
        controller.initData(productsTable.getSelectionModel().getSelectedItem());
        stage.show();
    }

    /**
     * On delete product button click, checks if product has associated parts. If so, error is
     * displayed. If product does not have associated parts, upon click verifies user wants to
     * delete selected product. If so, deletes selected product from products list. If product
     * was not deleted, displays error.
     */
    public void deleteProductButtonPushed() {
        Product selectedProduct = productsTable.getSelectionModel().getSelectedItem();
        if (selectedProduct == null) {
            return;
        }
        if (!selectedProduct.getAllAssociatedParts().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You can not delete a product with associated parts.");
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                if (Inventory.deleteProduct(selectedProduct)) {
                    productsTable.refresh();
                } else {
                    Alert alert1 = new Alert(Alert.AlertType.ERROR, "The product was not deleted. Try again.");
                    alert1.showAndWait();
                }
            }
        }
    }

    /**
     * On exit button click, closes application.
     */
    public void exitMainFormButtonPushed() {
        System.exit(0);
    }

    /**
     * On load, links table and table columns to display all parts. ALso links table and table columns for
     * all products.
     * @param url The location used to resolve relative paths for the root object, or null if the location
     *            is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was
     *                       not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //link table and columns to items for viewing
        partsPartID.setCellValueFactory(new PropertyValueFactory<>("id"));
        partsPartName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partsPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        partsTable.setItems(Inventory.getAllParts());

        productsProductID.setCellValueFactory(new PropertyValueFactory<>("id"));
        productsProductName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productsInventoryLevel.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productsPricePerUnit.setCellValueFactory(new PropertyValueFactory<>("price"));
        productsTable.setItems(Inventory.getAllProducts());
    }
}
