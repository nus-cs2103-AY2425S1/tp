package seedu.internbuddy.testutil;

import seedu.internbuddy.model.AddressBook;
import seedu.internbuddy.model.company.Company;

/**
 * A utility class to help with building AddressBookCompany objects.
 * Example usage: <br>
 *     {@code AddressBookCompany ab = new AddressBookCompanyBuilder().withCompany("TechCorp").build();}
 */
public class AddressBookCompanyBuilder {

    private AddressBook addressBookCompany;

    public AddressBookCompanyBuilder() {
        addressBookCompany = new AddressBook();
    }

    public AddressBookCompanyBuilder(AddressBook addressBookCompany) {
        this.addressBookCompany = addressBookCompany;
    }

    /**
     * Adds a new {@code Company} to the {@code AddressBookCompany} that we are building.
     */
    public AddressBookCompanyBuilder withCompany(Company company) {
        addressBookCompany.addCompany(company);
        return this;
    }

    public AddressBook build() {
        return addressBookCompany;
    }
}
