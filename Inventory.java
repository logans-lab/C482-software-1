package software1;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

/**
 * Inventory class
 * @author Logan
 */
public class Inventory {
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    private static int customPartId = 0;
    private static int customProductId = 0;

    /**
     * On call in AddPartFormController, increments before use.
     * @return the custom part id.
     */
    public static int getCustomPartId() {
        ++customPartId;
        return customPartId;
    }

    /**
     * On call in AddProductFormController, increments before use.
     * @return the custom part id.
     */
    public static int getCustomProductId() {
        ++customProductId;
        return customProductId;
    }

    /**
     * Adds selected part to all parts list.
     * @param newPart the selected part.
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds selected product to all products list.
     * @param newProduct the selected product.
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Searches for part id in all parts list.
     * @param partID the part id to search.
     * @return a selected part or if no match, displays alert.
     */
    public static Part lookupPart(int partID) {
        for(Part p: allParts) {
            if (p.getId() == partID) {
                return p;
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "No matching values found. Try searching again.");
        alert.showAndWait();
        return null;
    }

    /**
     * Searches for product id in all products list.
     * @param productID the part id to search.
     * @return a selected product or if no match, displays alert.
     */
    public static Product lookupProduct(int productID) {
        for(Product p: allProducts) {
            if (p.getId() == productID) {
                return p;
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "No matching values found. Try searching again.");
        alert.showAndWait();
        return null;
    }

    /**
     * Searches for part name in all parts list. If no match found,
     * displays alert and returns all parts.
     * @param partName the part name to search. Can be char or string.
     * @return all parts if no match. If match, returns list of parts.
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> lookupPartList = FXCollections.observableArrayList();
        if (!lookupPartList.isEmpty()) { //clears list for new search
            lookupPartList.clear();
        }
        for (Part p : allParts) { //adds matching parts to list if search is contained in name
            if (p.getName().toLowerCase().contains(partName.toLowerCase())) {
                lookupPartList.add(p);
            }
        }
        if(lookupPartList.isEmpty()) { //no parts found, return all, else return list
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No matching values found. Try searching again.");
            alert.showAndWait();
            return getAllParts();
        } else {
            return lookupPartList;
        }
    }

    /**
     * Searches for product name in all products list. If no match found,
     * displays alert and returns all parts.
     * @param productName the product name to search. Can be char or string.
     * @return all products if no match. If match, returns list of products.
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> lookupProductList = FXCollections.observableArrayList();
        if (!lookupProductList.isEmpty()) { //clears list for new search
            lookupProductList.clear();
        }
        for (Product p : allProducts) { //adds matching parts to list if search is contained in name
            if (p.getName().toLowerCase().contains(productName.toLowerCase())) {
                lookupProductList.add(p);
            }
        }
        if (lookupProductList.isEmpty()) { //no parts found, return all, else return list
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "No matching values found. Try searching again.");
            alert.showAndWait();
            return getAllProducts();
        } else {
            return lookupProductList;
        }
    }

    /**
     * Replaces part with updated values based on index in all parts list.
     * <p>
     * When testing the logic for updatePart and updateProduct, I was challenged
     * with determining what needed to happen in the code to complete the task.
     * In the if statement I attempted to simply add the part at the specific index
     * in question, thinking maybe it would write over the existing data. From there
     * I got the errors: InvocationTargetException and ConcurrentModificationException,
     * so that obviously wasn't correct. An instructor provided a hint of deleting the
     * current part and then saving the new part in it's place. However, literally calling
     * the deletePart and addPart methods resulted in the same errors. After completing
     * the rest of the application logic, I determined that I did not need to delete and
     * save the part itself, but rather use set to pass in the parameters.
     * Now, it seems so obvious as to the correct way of updating parts and products,
     * but through the trial and error, I was really able to understand how objects
     * work across multiple controllers as well as how to effectively get and set the
     * data.
     * @param index the location of part in list.
     * @param selectedPart the part to save over existing part.
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Replaces product with updated values based on index in all products list.
     * @param index the location of product in list.
     * @param newProduct the product to save over existing product.
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes part from all parts list.
     * @param selectedPart the part to delete.
     * @return true if part was deleted. Handled in MainFormController.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes product from all products list.
     * @param selectedProduct the product to delete.
     * @return true if product was deleted. Handled in MainFormController.
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * Gets all parts from list.
     * @return all parts.
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * Gets all products from list.
     * @return all products.
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
