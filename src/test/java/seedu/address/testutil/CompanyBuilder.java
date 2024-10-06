package seedu.address.testutil;

import seedu.address.model.company.BillingDate;
import seedu.address.model.company.CompanyName;
import seedu.address.model.company.Company;
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;

public class CompanyBuilder {
    public static final String DEFAULT_NAME = "NUS";
    public static final String DEFAULT_ADDRESS = "21 Lower Kent Ridge Rd, Singapore 119077";
    public static final String DEFAULT_BILLING_DATE = "5";
    public static final String DEFAULT_PHONE = "65166666";
    private CompanyName name;
    private Address address;
    private BillingDate billingDate;
    private Phone phone;

    /**
     * Constructs a new CompanyBuilder with the default values.
     */
    public CompanyBuilder() {
        name = new CompanyName(DEFAULT_NAME);
        address = new Address(DEFAULT_ADDRESS);
        billingDate = new BillingDate(DEFAULT_BILLING_DATE);
        phone = new Phone(DEFAULT_PHONE);
    }

    public CompanyBuilder withName(String name) {
        this.name = new CompanyName(name);
        return this;
    }

    public CompanyBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    public CompanyBuilder withBillingDate(String date) {
        this.billingDate = new BillingDate(date);
        return this;
    }

    public CompanyBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    public Company build() {
        return new Company(name, address, billingDate, phone);
    }
}
