package model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Model for Inventory of parts and products
 * @author Corey
 */
public class Inventory {

    /**
     * List of all parts
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * List of all products
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();
    /**
     * Add part to allParts list
     * @param part part to add to the list
     */
    public static void addPart(Part part){
        allParts.add(part);
    }
    /**
     * Add product to allProducts list
     * @param product product to add to the list
     */
    public static void addProduct(Product product){
        allProducts.add(product);
    }
    /**
     * Gets of list of all the parts
     * @return list of all the parts
     */
    public static ObservableList<Part> getAllParts(){
        return allParts;
    }
    /**
     * Gets a list of all the products
     * @return list of all the parts
     */
    public static ObservableList<Product> getAllProducts(){
        return allProducts;
    }
    /**
     * Look up a part in list by ID
     * @param partID ID of the part
     * @return part found
     */
    public static Part lookupPart(int partID){
        ObservableList<Part> partsLookedUp = Inventory.getAllParts();
        for(Part p : partsLookedUp){
            if(p.getId() == partID){
                return p;
            }
        }
        return null;
    }
    /**
     * Look up a product in list by ID
     * @param productID ID of the product
     * @return part foundcoun
     */
    public static Product lookupProduct(int productID){
        ObservableList<Product> productsLookedUp = Inventory.getAllProducts();
        for(Product p : productsLookedUp){
            if(p.getId() == productID){
                return p;
            }
        }
        return null;
    }
    /**
     * Look up part in list by name
     * @param partName name of the part
     * @return part found
     */
    public static ObservableList<Part> lookupPart(String partName){
        ObservableList<Part> partsLookedUp = FXCollections.observableArrayList();
        for(Part p : allParts){
            if(p.getName().contains(partName)){
                partsLookedUp.add(p);
            }
        }
        return partsLookedUp;
    }
    /**
     * Look up product in List by name
     * @param productName name of the product
     * @return product found
     */
    public static ObservableList<Product> lookupProduct(String productName){
        ObservableList<Product> productsLookedUp = FXCollections.observableArrayList();
        for(Product p : allProducts){
            if(p.getName().contains(productName)){
                productsLookedUp.add(p);
            }
        }
        return productsLookedUp;
    }
    /**
     * Updates the information of the part
     * @param index index of the part
     * @param selectedPart part that was selected
     */
    public static void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    /**
     * Updates the information of the product
     * @param index index of the product
     * @param selectedProduct product that was selected
     */
    public static void updateProduct(int index,Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }
    /**
     * Deletes the part from the allParts list
     * @param selectedPart part selected
     */
    public static void deletePart(Part selectedPart){
        allParts.remove(selectedPart);
    }
    /**
     * Delets the product from the allProducts list
     * @param selectedProduct product selected
     */
    public static void deleteProduct(Product selectedProduct){
        allProducts.remove(selectedProduct);
    }

}
