package seedu.hireme.testutil;

import seedu.hireme.model.AddressBook;
import seedu.hireme.model.internshipapplication.InternshipApplication;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private final AddressBook addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook();
    }

    public AddressBookBuilder(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withApplication(InternshipApplication application) {
        addressBook.addItem(application);
        return this;
    }

    public AddressBook build() {
        return addressBook;
    }
}
