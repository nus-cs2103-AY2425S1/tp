package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.testutil.Assert.assertThrows;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import javafx.collections.ObservableList;
import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyUserPrefs;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Supplier;
import seedu.address.testutil.ProductBuilder;

public class AddProductCommandTest {
    //null passed
    @Test
    public void constructor_nullProduct_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> new AddProductCommand(null));
    }
    //Product does not exist and supplier exists initially
    @Test
    public void execute_productAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingProductAdded modelStub = new ModelStubAcceptingProductAdded();
        Product validProduct = new ProductBuilder().build();
        CommandResult commandResult = new AddProductCommand(validProduct).execute(modelStub);
        assertEquals(String.format(AddProductCommand.MESSAGE_SUCCESS, Messages.format(validProduct)),
                commandResult.getFeedbackToUser());
        assertEquals(Arrays.asList(validProduct), modelStub.productsAdded);
    }
    //Product exists initially
    @Test
    public void execute_duplicateProduct_throwsCommandException() {
        Product validProduct = new ProductBuilder().build();
        AddProductCommand addProductCommand = new AddProductCommand(validProduct);
        ModelStub modelStub = new ModelStubWithProduct(validProduct);
        assertThrows(CommandException.class, AddProductCommand.MESSAGE_DUPLICATE_PRODUCT, () ->
                addProductCommand.execute(modelStub));
    }
    //Supplier does not exist initially
    @Test
    public void execute_nonExistentSupplier_throwsCommandException() {
        Product productWithNonExistentSupplier = new ProductBuilder()
                .withSupplierName("NonExistentSupplier")
                .build();
        AddProductCommand addProductCommand = new AddProductCommand(productWithNonExistentSupplier);
        ModelStubWithoutSupplier modelStub = new ModelStubWithoutSupplier();
        assertThrows(CommandException.class, String.format(AddProductCommand.MESSAGE_SUPPLIER_NOT_FOUND,
                "NonExistentSupplier"), () -> addProductCommand.execute(modelStub));
    }

    private class ModelStub implements Model {
        @Override
        public void setUserPrefs(ReadOnlyUserPrefs userPrefs) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public List<String> getAllTags() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public boolean hasProduct(Product product) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void addProduct(Product product) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ReadOnlyUserPrefs getUserPrefs() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public GuiSettings getGuiSettings() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setGuiSettings(GuiSettings guiSettings) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public Path getAddressBookFilePath() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setAddressBookFilePath(Path addressBookFilePath) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void addSupplier(Supplier supplier) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setAddressBook(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasSupplier(Supplier supplier) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void deleteSupplier(Supplier target) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void deleteProduct(Product target) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setSupplier(Supplier target, Supplier editedSupplier) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void setProduct(Product target, Product editedSupplier) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ObservableList<Supplier> getModifiedSupplierList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void updateFilteredSupplierList(Predicate<Supplier> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public ObservableList<Product> getModifiedProductList() {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void updateFilteredProductList(Predicate<Product> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public void updateSortedProductList(Comparator<Product> predicate) {
            throw new AssertionError("This method should not be called.");
        }
        @Override
        public Product findProductByName(ProductName productName) {
            return null;
        }

        @Override
        public Supplier findSupplier(Name supplierName) {
            return null;
        }
        @Override
        public boolean isProductAssignedToAnySupplier(Product product) {
            throw new AssertionError("This method should not be called.");
        };
    }

    /**
     * A Model stub that accepts any product being added.
     */
    private class ModelStubAcceptingProductAdded extends ModelStub {
        final ArrayList<Product> productsAdded = new ArrayList<>();
        @Override
        public boolean hasProduct(Product product) {
            requireNonNull(product);
            return productsAdded.stream().anyMatch(product::isSameProduct);
        }
        @Override
        public void addProduct(Product product) {
            requireNonNull(product);
            productsAdded.add(product);
        }

    }
    /**
     * A Model stub for testing duplicate products.
     */
    private class ModelStubWithProduct extends ModelStub {
        private final Product product;
        ModelStubWithProduct(Product product) {
            requireNonNull(product);
            this.product = product;
        }
        @Override
        public boolean hasProduct(Product product) {
            requireNonNull(product);
            return this.product.isSameProduct(product);
        }
        @Override
        public Supplier findSupplier(Name supplierName) {
            return null;
        }
    }

    /**
     * A Model stub for testing non-existent supplier scenarios.
     */
    private class ModelStubWithoutSupplier extends ModelStub {
        final ArrayList<Product> productsAdded = new ArrayList<>();
        @Override
        public boolean hasProduct(Product product) {
            requireNonNull(product);
            return productsAdded.stream().anyMatch(product::isSameProduct);
        }
        @Override
        public void addProduct(Product product) {
            requireNonNull(product);
            productsAdded.add(product);
        }
        @Override
        public Supplier findSupplier(Name supplierName) {
            return null; // Simulates a missing supplier by returning null.
        }
    }
}
