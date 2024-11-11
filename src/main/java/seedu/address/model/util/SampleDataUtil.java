package seedu.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.product.Product;
import seedu.address.model.product.ProductName;
import seedu.address.model.product.StockLevel;
import seedu.address.model.supplier.Address;
import seedu.address.model.supplier.Email;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Phone;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final Set<Product> EMPTY_PRODUCT_LIST = new HashSet<>();

    public static Supplier[] getSampleSuppliers() {
        return new Supplier[] {
            new Supplier(new Name("Fresh Foods Co."), new Phone("82430021"), new Email("contact@freshfoods.com"),
                new Address("Blk 101 Geylang Street 11, #02-05"),
                getTagSet("wholesaler", "foodsupplier"), EMPTY_PRODUCT_LIST),
            new Supplier(new Name("Household Essentials Ltd."), new Phone("89273950"),
                    new Email("info@householdessentials.com"),
                new Address("Blk 88 Bedok North Avenue 3, #08-22"),
                getTagSet("cleaningsupplies"), EMPTY_PRODUCT_LIST),
            new Supplier(new Name("Beverages Unlimited"), new Phone("90234012"),
                    new Email("sales@beveragesunlimited.com"),
                new Address("Blk 52 Clementi Road, #10-02"),
                getTagSet("drinkssupplier"), EMPTY_PRODUCT_LIST),
            new Supplier(new Name("Snack Haven Pte Ltd"), new Phone("91827364"), new Email("orders@snackhaven.com"),
                new Address("Blk 24 Tanjong Pagar Road, #03-14"),
                getTagSet("snacks", "confectionery"), EMPTY_PRODUCT_LIST),
            new Supplier(new Name("Dairy Delights"), new Phone("96543210"), new Email("service@dairydelights.com"),
                new Address("Blk 55 Toa Payoh Lorong 5, #04-19"),
                getTagSet("dairyproducts"), EMPTY_PRODUCT_LIST),
            new Supplier(new Name("Eco Essentials"), new Phone("93428567"), new Email("contact@ecoessentials.com"),
                new Address("Blk 77 Queenstown Way, #09-03"),
                getTagSet("ecofriendly", "packaging"), EMPTY_PRODUCT_LIST),
        };
    }

    public static Product[] getSampleProducts() {
        return new Product[] {
            new Product(new ProductName("Coca Cola Can 330ml"),
                new StockLevel(10, 10, 20),
                    getTagSet("beverages", "softdrinks")),

            new Product(new ProductName("Instant Noodles - Chicken Flavor"),
                new StockLevel(20, 10, 50),
                getTagSet("snacks", "noodles")),

            new Product(new ProductName("Toilet Paper Roll - 3 ply"),
                new StockLevel(15, 10, 30),
                getTagSet("householditems", "essentials")),

            new Product(new ProductName("Chocolate Bar - Milk"),
                new StockLevel(7, 10, 15),
                getTagSet("snacks", "confectionery")),

            new Product(new ProductName("Organic Whole Milk - 1L"),
                new StockLevel(5, 5, 10),
                getTagSet("dairyproducts", "organic")),

            new Product(new ProductName("Biodegradable Food Packaging"),
                new StockLevel(12, 5, 25),
                getTagSet("packaging", "ecofriendly")),
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Supplier sampleSupplier : getSampleSuppliers()) {
            sampleAb.addSupplier(sampleSupplier);
        }
        for (Product sampleProduct: getSampleProducts()) {
            sampleAb.addProduct(sampleProduct);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

    /**
     * Returns a product set containing the list of products with name given.
     */
    public static Set<Product> getProductSet(String... strings) {
        return Arrays.stream(strings)
                .map(name -> new Product(new ProductName(name), new StockLevel(0, 0, 0), new HashSet<>()))
                .collect(Collectors.toSet());
    }

}
