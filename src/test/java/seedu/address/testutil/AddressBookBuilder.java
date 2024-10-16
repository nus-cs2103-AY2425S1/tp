package seedu.address.testutil;

import seedu.address.model.AddressBook;
import seedu.address.model.internshipapplication.InternshipApplication;
import seedu.address.model.internshipapplication.Person;

/**
 * A utility class to help with building Addressbook objects.
 * Example usage: <br>
 *     {@code AddressBook ab = new AddressBookBuilder().withPerson("John", "Doe").build();}
 */
public class AddressBookBuilder {

    private AddressBook<InternshipApplication> addressBook;

    public AddressBookBuilder() {
        addressBook = new AddressBook<>();
    }

    public AddressBookBuilder(AddressBook<InternshipApplication> addressBook) {
        this.addressBook = addressBook;
    }

    /**
     * Adds a new {@code Person} to the {@code AddressBook} that we are building.
     */
    public AddressBookBuilder withApplication(InternshipApplication application) {
        addressBook.addItem(application);
        return this;
    }

    public AddressBook<InternshipApplication> build() {
        return addressBook;
    }
}
