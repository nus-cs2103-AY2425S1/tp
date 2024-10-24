package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.List;
import java.util.Objects;

import javafx.collections.ObservableList;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.product.UniqueProductList;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.UniqueSupplierList;


/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .isSameSupplier comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniqueSupplierList suppliers;
    private final UniqueProductList products;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        suppliers = new UniqueSupplierList();
        products = new UniqueProductList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Suppliers in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the supplier list with {@code suppliers}.
     * {@code suppliers} must not contain duplicate suppliers.
     */
    public void setSuppliers(List<Supplier> suppliers) {
        this.suppliers.setSuppliers(suppliers);
    }

    /**
     * Replaces the contents of the product list with {@code products}.
     * {@code products} must not contain duplicate products.
     */
    public void setProducts(List<Product> products) {
        this.products.setProducts(products);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        setSuppliers(newData.getSupplierList());
        setProducts(newData.getProductList());
    }

    //// supplier-level operations

    /**
     * Returns true if a supplier with the same identity as {@code supplier} exists in the address book.
     */
    public boolean hasSupplier(Supplier supplier) {
        requireNonNull(supplier);
        return suppliers.contains(supplier);
    }

    /**
     * Adds a supplier to the address book.
     * The supplier must not already exist in the address book.
     */
    public void addSupplier(Supplier p) {
        suppliers.add(p);
    }

    /**
     * Replaces the given supplier {@code target} in the list with {@code editedSupplier}.
     * {@code target} must exist in the address book.
     * The supplier identity of {@code editedSupplier} must not be
     * the same as another existing supplier in the address book.
     */
    public void setSupplier(Supplier target, Supplier editedSupplier) {
        requireNonNull(editedSupplier);

        suppliers.setSupplier(target, editedSupplier);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeSupplier(Supplier key) {
        Name supplierName = key.getName();
        for (Product product : products) {
            product.removeSupplier(supplierName);
        }
        suppliers.remove(key);
    }

    //// product-level operations

    /**
     * Returns true if a product with the same identity as {@code product} exists in the address book.
     */
    public boolean hasProduct(Product product) {
        requireNonNull(product);
        return products.contains(product);
    }

    /**
     * Adds a product to the address book.
     * The product must not already exist in the address book.
     */
    public void addProduct(Product p) {
        products.add(p);
    }

    /**
     * Replaces the given product {@code target} in the list with {@code editedProduct}.
     * {@code target} must exist in the address book.
     * The product identity of {@code editedProduct} must not be
     * the same as another existing product in the address book.
     */
    public void setProduct(Product target, Product editedProduct) {
        requireNonNull(editedProduct);

        products.setProduct(target, editedProduct);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * {@code key} must exist in the address book.
     */
    public void removeProduct(Product key) {
        for (Supplier supplier : suppliers) {
            supplier.removeProduct(key);
        }
        products.remove(key);
    }

    public Product findProductByName(ProductName name) {
        return products.findByName(name);
    }

    //// util methods

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("suppliers", suppliers)
                .add("products", products)
                .toString();
    }

    @Override
    public ObservableList<Supplier> getSupplierList() {
        return suppliers.asUnmodifiableObservableList();
    }

    @Override
    public ObservableList<Product> getProductList() {
        return products.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddressBook)) {
            return false;
        }

        AddressBook otherAddressBook = (AddressBook) other;
        return suppliers.equals(otherAddressBook.suppliers) && products.equals(otherAddressBook.products);
    }

    @Override
    public int hashCode() {
        return Objects.hash(suppliers, products);
    }

    public Supplier findSupplier(Name supplierName) {
        return suppliers.findByName(supplierName);
    }
}
