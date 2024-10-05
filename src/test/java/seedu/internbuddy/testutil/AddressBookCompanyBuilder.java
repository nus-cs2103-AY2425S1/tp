package seedu.internbuddy.testutil;

import seedu.internbuddy.model.AddressBookCompany;
import seedu.internbuddy.model.company.Company;

/**
 * A utility class to help with building AddressBookCompany objects.
 * Example usage: <br>
 *     {@code AddressBookCompany ab = new AddressBookCompanyBuilder().withCompany("TechCorp").build();}
 */
public class AddressBookCompanyBuilder {

    private AddressBookCompany addressBookCompany;

    public AddressBookCompanyBuilder() {
        addressBookCompany = new AddressBookCompany();
    }

    public AddressBookCompanyBuilder(AddressBookCompany addressBookCompany) {
        this.addressBookCompany = addressBookCompany;
    }

    /**
     * Adds a new {@code Company} to the {@code AddressBookCompany} that we are building.
     */
    public AddressBookCompanyBuilder withCompany(Company company) {
        addressBookCompany.addCompany(company);
        return this;
    }

    public AddressBookCompany build() {
        return addressBookCompany;
    }
}
