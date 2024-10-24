package seedu.address.model;
import java.nio.file.Path;
import java.util.List;
import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Supplier;

/**
 * The API of the Model component.
 */
public interface Model {
    /** {@code Predicate} that always evaluate to true */
    Predicate<Supplier> PREDICATE_SHOW_ALL_SUPPLIERS = unused -> true;
    /** {@code Predicate} that always evaluate to true */
    Predicate<Product> PREDICATE_SHOW_ALL_PRODUCTS = unused -> true;
    List<String> getAllTags();
    /**
     * Replaces user prefs data with the data in {@code userPrefs}.
     */
    void setUserPrefs(ReadOnlyUserPrefs userPrefs);

    /**
     * Returns the user prefs.
     */
    ReadOnlyUserPrefs getUserPrefs();

    /**
     * Returns the user prefs' GUI settings.
     */
    GuiSettings getGuiSettings();

    /**
     * Sets the user prefs' GUI settings.
     */
    void setGuiSettings(GuiSettings guiSettings);

    /**
     * Returns the user prefs' address book file path.
     */
    Path getAddressBookFilePath();

    /**
     * Sets the user prefs' address book file path.
     */
    void setAddressBookFilePath(Path addressBookFilePath);

    /**
     * Replaces address book data with the data in {@code addressBook}.
     */
    void setAddressBook(ReadOnlyAddressBook addressBook);

    /** Returns the AddressBook */
    ReadOnlyAddressBook getAddressBook();

    /**
     * Returns true if a supplier with the same identity as {@code supplier} exists in the address book.
     */
    boolean hasSupplier(Supplier supplier);
    /**
     * Returns true if a product with the same identity as {@code product} exists in InvenTrack.
     */
    boolean hasProduct(Product product);

    /**
     * Deletes the given supplier.
     * The supplier must exist in the address book.
     */
    void deleteSupplier(Supplier target);

    /**
     * Deletes the given product.
     * The product must exist in the address book.
     */
    void deleteProduct(Product target);

    /**
     * Adds the given supplier.
     * {@code supplier} must not already exist in the address book.
     */
    void addSupplier(Supplier supplier);

    /**
     * Adds the given product.
     * {@code product} must not already exist in InvenTrack.
     */
    void addProduct(Product product);


    /**
     * Replaces the given supplier {@code target} with {@code editedSupplier}.
     * {@code target} must exist in the address book.
     * The supplier identity of {@code editedSupplier} must not be
     * the same as another existing supplier in the address book.
     */
    void setSupplier(Supplier target, Supplier editedSupplier);

    /**
     * Replaces the given Product {@code target} with {@code editedProduct}.
     * {@code target} must exist in the address book.
     * The product identity of {@code editedProduct} must not be
     * the same as another existing product in the address book.
     */
    void setProduct(Product target, Product editedProduct);

    /** Returns an unmodifiable view of the filtered supplier list */
    ObservableList<Supplier> getFilteredSupplierList();

    /** Returns an unmodifiable view of the filtered product list */
    ObservableList<Product> getFilteredProductList();

    /**
     * Updates the filter of the filtered supplier list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredSupplierList(Predicate<Supplier> predicate);

    /**
     * Updates the filter of the filtered product list to filter by the given {@code predicate}.
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredProductList(Predicate<Product> predicate);

    Product findProductByName(ProductName productName);

    Supplier findSupplier(Name supplierName);
}

