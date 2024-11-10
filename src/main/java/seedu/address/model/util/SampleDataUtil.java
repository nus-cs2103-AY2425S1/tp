package seedu.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.delivery.Cost;
import seedu.address.model.delivery.DateTime;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.Quantity;
import seedu.address.model.delivery.Status;
import seedu.address.model.product.Product;
import seedu.address.model.supplier.Company;
import seedu.address.model.supplier.Email;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Phone;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.SupplierStatus;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static final SupplierStatus STATUS = new SupplierStatus("active");
    public static Supplier[] getSampleSuppliers() {
        return new Supplier[] {
            new Supplier(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                new Company("companyA"), getTagSet("friends"), getProductSet("rice"), STATUS),
            new Supplier(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                new Company("companyB"), getTagSet("colleagues", "friends"), getProductSet("bread"), STATUS),
            new Supplier(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                new Company("companyC"), getTagSet("neighbours"), getProductSet("noodle"), STATUS),
            new Supplier(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                new Company("companyD"), getTagSet("family"), getProductSet("flour"), STATUS),
            new Supplier(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                new Company("companyE"), getTagSet("classmates"), getProductSet("pasta"), STATUS),
            new Supplier(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                        new Company("companyF"), getTagSet("colleagues"), getProductSet(), STATUS)
        };
    }

    public static Delivery[] getSampleDeliveries() {
        return new Delivery[] {
            new Delivery(new Product("Apple"),
                new Supplier(new Name("Alex Yeoh"), new Phone("87438807"), new Email("alexyeoh@example.com"),
                    new Company("companyA"), getTagSet("friends"), getProductSet("rice"), STATUS),
                Status.PENDING, new DateTime("21-03-2025 12:12"), new Cost("20"), new Quantity("500 mL")),
            new Delivery(new Product("Bread"),
                new Supplier(new Name("Bernice Yu"), new Phone("99272758"), new Email("berniceyu@example.com"),
                    new Company("companyB"), getTagSet("colleagues", "friends"), getProductSet("bread"), STATUS),
                Status.PENDING, new DateTime("23-12-2024 11:12"), new Cost("200"), new Quantity("50 kg")),
            new Delivery(new Product("Cookies"),
                new Supplier(new Name("Charlotte Oliveiro"), new Phone("93210283"), new Email("charlotte@example.com"),
                        new Company("companyC"), getTagSet("neighbours"), getProductSet("noodle"), STATUS),
                Status.DELIVERED, new DateTime("06-09-2024 12:12"), new Cost("90.50"), new Quantity("7 kg")),
            new Delivery(new Product("Dragonfruit"),
                new Supplier(new Name("David Li"), new Phone("91031282"), new Email("lidavid@example.com"),
                        new Company("companyD"), getTagSet("family"), getProductSet("flour"), STATUS),
                Status.DELIVERED, new DateTime("09-06-2024 12:12"), new Cost("176.75"), new Quantity("17 kg")),
            new Delivery(new Product("Eggs"),
                new Supplier(new Name("Irfan Ibrahim"), new Phone("92492021"), new Email("irfan@example.com"),
                    new Company("companyE"), getTagSet("classmates"), getProductSet("pasta"), STATUS),
                Status.CANCELLED, new DateTime("01-02-2024 22:12"), new Cost("16.99"), new Quantity("900 g")),
            new Delivery(new Product("Flour"),
                new Supplier(new Name("Roy Balakrishnan"), new Phone("92624417"), new Email("royb@example.com"),
                    new Company("companyF"), getTagSet("colleagues"), getProductSet(), STATUS),
                Status.CANCELLED, new DateTime("03-03-2024 12:30"), new Cost("169.99"), new Quantity("25 kg"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Supplier sampleSupplier : getSampleSuppliers()) {
            sampleAb.addSupplier(sampleSupplier);
        }
        for (Delivery sampleDelivery : getSampleDeliveries()) {
            sampleAb.addDelivery(sampleDelivery);
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
     * Returns a product set containing the list of strings given.
     */
    public static Set<Product> getProductSet(String... strings) {
        return Arrays.stream(strings)
                .map(Product::new)
                .collect(Collectors.toSet());
    }
}
