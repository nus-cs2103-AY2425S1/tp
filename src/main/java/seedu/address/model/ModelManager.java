package seedu.address.model;


import static java.util.Objects.requireNonNull;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Supplier;


/**
 * Represents the in-memory model of the address book data.
 */
public class ModelManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<Supplier> filteredSuppliers;
    private final FilteredList<Product> filteredProducts;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUserPrefs userPrefs) {
        requireNonNull(addressBook);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = new UserPrefs(userPrefs);
        filteredSuppliers = new FilteredList<>(this.addressBook.getSupplierList());
        filteredProducts = new FilteredList<>(this.addressBook.getProductList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
        requireNonNull(userPrefs);
        this.userPrefs.resetData(userPrefs);
    }

    @Override
    public ReadOnlyUserPrefs getUserPrefs() {
        return userPrefs;
    }

    @Override
    public GuiSettings getGuiSettings() {
        return userPrefs.getGuiSettings();
    }

    @Override
    public void setGuiSettings(GuiSettings guiSettings) {
        requireNonNull(guiSettings);
        userPrefs.setGuiSettings(guiSettings);
    }

    @Override
    public Path getAddressBookFilePath() {
        return userPrefs.getAddressBookFilePath();
    }

    @Override
    public void setAddressBookFilePath(Path addressBookFilePath) {
        requireNonNull(addressBookFilePath);
        userPrefs.setAddressBookFilePath(addressBookFilePath);
    }

    @Override
    public void setAddressBook(ReadOnlyAddressBook addressBook) {
        this.addressBook.resetData(addressBook);
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public boolean hasSupplier(Supplier supplier) {
        requireNonNull(supplier);
        return addressBook.hasSupplier(supplier);
    }

    @Override
    public void deleteSupplier(Supplier target) {
        addressBook.removeSupplier(target);
    }

    @Override
    public void deleteProduct(Product target) {
        addressBook.removeProduct(target);
    }

    @Override
    public boolean hasProduct(Product product) {
        requireNonNull(product);
        return addressBook.hasProduct(product);
    }

    @Override
    public void addSupplier(Supplier supplier) {
        addressBook.addSupplier(supplier);
        updateFilteredSupplierList(PREDICATE_SHOW_ALL_SUPPLIERS);
    }

    @Override
    public List<String> getAllTags() {
        Set<String> tagSet = new HashSet<>();

        for (Product product : addressBook.getProductList()) {
            tagSet.addAll(product.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toSet()));
        }

        for (Supplier supplier : addressBook.getSupplierList()) {
            tagSet.addAll(supplier.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toSet()));
        }

        List<String> allTags = new ArrayList<>(tagSet);
        Collections.sort(allTags); // Optional: sort alphabetically
        return allTags;
    }

    @Override
    public void setSupplier(Supplier target, Supplier editedSupplier) {
        requireNonNull(target);
        requireNonNull(editedSupplier);
        addressBook.setSupplier(target, editedSupplier);
    }

    @Override
    public void setProduct(Product target, Product editedProduct) {
        requireNonNull(target);
        addressBook.setProduct(target, editedProduct);
    }

    @Override
    public void addProduct(Product product) {
        addressBook.addProduct(product);
        updateFilteredProductList(PREDICATE_SHOW_ALL_PRODUCTS);

    }

    @Override
    public Product findProductByName(ProductName productName) {
        return addressBook.findProductByName(productName);
    }

    @Override
    public Supplier findSupplier(Name supplierName) {
        return addressBook.findSupplier(supplierName);
    }

    //=========== Filtered Supplier List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Supplier} backed by the internal list of
     * {@code versionedAddressBook}
     */
    @Override
    public ObservableList<Supplier> getFilteredSupplierList() {
        return filteredSuppliers;
    }

    @Override
    public ObservableList<Product> getFilteredProductList() {
        return filteredProducts;
    }

    @Override
    public void updateFilteredSupplierList(Predicate<Supplier> predicate) {
        requireNonNull(predicate);
        filteredSuppliers.setPredicate(predicate);
    }

    @Override
    public void updateFilteredProductList(Predicate<Product> predicate) {
        requireNonNull(predicate);
        filteredProducts.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ModelManager)) {
            return false;
        }

        ModelManager otherModelManager = (ModelManager) other;
        return addressBook.equals(otherModelManager.addressBook)
                && userPrefs.equals(otherModelManager.userPrefs)
                && filteredSuppliers.equals(otherModelManager.filteredSuppliers);
    }

}
