package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.supplier.Supplier;

import java.util.List;

/**
    * A utility class containing a list of {@code Supplier} objects to be used in tests.
 **/
public class TypicalSuppliers {
    public static final Supplier AMY = new SupplierBuilder().build();
    public static final Supplier BOB = new SupplierBuilder().withName("Bob Choo").withPhone("98765432")
            .withEmail("bob@gmail.com").withCompany("Bob's Company").withProduct("bread").withStatus("active").build();
    public static final Supplier CHARLIE = new SupplierBuilder().withName("Charlie Tan").withPhone("87654321")
            .withEmail("charlie@gmail.com").withCompany("Charlie's Company")
            .withProduct("canned drinks").withStatus("inactive").build();
    public static final Supplier DAVID = new SupplierBuilder().withName("David Lim").withPhone("76543210")
            .withEmail("david@gmail.com").withCompany("David's Company").withProduct("sunshine bread")
            .withStatus("active").build();
    private TypicalSuppliers() {} // prevents instantiation
    /**
     * Returns an {@code AddressBook} with all the typical suppliers.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Supplier supplier : getTypicalSuppliers()) {
            ab.addSupplier(supplier);
        }
        return ab;
    }
    public static List<Supplier> getTypicalSuppliers() {
        return List.of(AMY, BOB, CHARLIE, DAVID);
    }
}

