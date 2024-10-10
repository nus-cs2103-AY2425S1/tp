package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.product.Product;

/**
 * A utility class containing a list of {@code Product} objects to be used in tests.
 */
public class TypicalProducts {

    public static final Product APPLE = new ProductBuilder().withName("Apple").build();
    public static final Product BANANA = new ProductBuilder().withName("Banana").build();
    public static final Product CARROT = new ProductBuilder().withName("Carrot").build();
    public static final Product DATE = new ProductBuilder().withName("Date").build();
    public static final Product EGGPLANT = new ProductBuilder().withName("Eggplant").build();
    public static final Product FIG = new ProductBuilder().withName("Fig").build();
    public static final Product GRAPE = new ProductBuilder().withName("Grape").build();

    // Manually added
    public static final Product HONEYDEW = new ProductBuilder().withName("Honeydew").build();
    public static final Product ITALIAN_PLUM = new ProductBuilder().withName("Italian Plum").build();

    // Manually added - Product's details found in {@code CommandTestUtil}
    public static final Product JACKFRUIT = new ProductBuilder().withName("Jackfruit").build();
    public static final Product KIWI = new ProductBuilder().withName("Kiwi").build();

    private TypicalProducts() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical products.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Product product : getTypicalProducts()) {
            ab.addProduct(product);
        }
        return ab;
    }

    public static List<Product> getTypicalProducts() {
        return new ArrayList<>(Arrays.asList(APPLE, BANANA, CARROT, DATE, EGGPLANT, FIG, GRAPE));
    }
}
