package seedu.address.model.product;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.product.exceptions.DuplicateProductException;
import seedu.address.model.product.exceptions.ProductNotFoundException;

/**
 * A list of products that enforces uniqueness between its elements and does not allow nulls.
 * A product is considered unique by comparing using {@code Product#isSameProduct(Product)}.
 * As such, adding and updating of products uses Product#isSameProduct(Product) for equality to ensure that
 * the product being added or updated is unique in terms of identity in the UniqueProductList.
 * However, the removal of a product uses Product#equals(Object) to ensure that the product
 * with exactly the same fields will be removed.
 *
 * Supports a minimal set of list operations.
 *
 * @see Product#isSameProduct(Product)
 */
public class UniqueProductList implements Iterable<Product> {

    private final ObservableList<Product> internalList = FXCollections.observableArrayList();
    private final ObservableList<Product> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);


    /**
     * Finds and returns the product with the specified {@code ProductName}.
     * Returns null if the product is not found.
     */
    public Product findByName(ProductName productName) {
        requireNonNull(productName);
        for (Product product : internalList) {
            if (product.getName().equals(productName)) {
                return product;
            }
        }
        return null;
    }


    /**
     * Returns true if the list contains an equivalent product as the given argument.
     */
    public boolean contains(Product toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::isSameProduct);
    }

    /**
     * Adds a product to the list.
     * The product must not already exist in the list.
     */
    public void add(Product toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateProductException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the product {@code target} in the list with {@code editedProduct}.
     * {@code target} must exist in the list.
     * The product identity of {@code editedProduct} must not be the same as another existing product in the list.
     */
    public void setProduct(Product target, Product editedProduct) {
        requireAllNonNull(target, editedProduct);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ProductNotFoundException();
        }

        if (!target.isSameProduct(editedProduct) && contains(editedProduct)) {
            throw new DuplicateProductException();
        }

        internalList.set(index, editedProduct);
    }

    /**
     * Removes the equivalent product from the list.
     * The product must exist in the list.
     */
    public void remove(Product toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ProductNotFoundException();
        }
    }

    public void setProducts(UniqueProductList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code products}.
     * {@code products} must not contain duplicate products.
     */
    public void setProducts(List<Product> products) {
        requireAllNonNull(products);
        if (!productsAreUnique(products)) {
            throw new DuplicateProductException();
        }

        internalList.setAll(products);
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Product> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Product> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UniqueProductList)) {
            return false;
        }

        UniqueProductList otherUniqueProductList = (UniqueProductList) other;
        return internalList.equals(otherUniqueProductList.internalList);
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    @Override
    public String toString() {
        return internalList.toString();
    }

    /**
     * Returns true if {@code products} contains only unique products.
     */
    private boolean productsAreUnique(List<Product> products) {
        for (int i = 0; i < products.size() - 1; i++) {
            for (int j = i + 1; j < products.size(); j++) {
                if (products.get(i).isSameProduct(products.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }
}
