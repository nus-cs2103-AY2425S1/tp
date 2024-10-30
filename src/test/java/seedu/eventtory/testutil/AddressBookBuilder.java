package seedu.eventtory.testutil;

import seedu.eventtory.model.AddressBook;
import seedu.eventtory.model.vendor.Vendor;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withVendor("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Vendor} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withVendor(Vendor vendor) {
        addressBook.addVendor(vendor);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
