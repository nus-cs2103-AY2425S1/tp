package seedu.address.testutil;

import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.company.BillingDate;
import seedu.address.model.company.Company;
import seedu.address.model.person.Phone;

/**
 * Class used to build Company objects.
 */
public class CompanyBuilder {
    public static final String DEFAULT_NAME = "NUS";
    public static final String DEFAULT_ADDRESS = "21 Lower Kent Ridge Rd, Singapore 119077";
    public static final String DEFAULT_BILLING_DATE = "5";
    public static final String DEFAULT_PHONE = "65166666";
    private Name name;
    private Address address;
    private BillingDate billingDate;
    private Phone phone;

    /**
     * Constructs a new CompanyBuilder with the default values.
     */
    public CompanyBuilder() {
        name = new Name(DEFAULT_NAME);
        address = new Address(DEFAULT_ADDRESS);
        billingDate = new BillingDate(DEFAULT_BILLING_DATE);
        phone = new Phone(DEFAULT_PHONE);
    }

    /**
     * Sets the company name to the specified name.
     *
     * @param name Name to be set.
     * @return CompanyBuilder object.
     */
    public CompanyBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Sets the company address to the specified address.
     *
     * @param address Address to be set.
     * @return CompanyBuilder object.
     */
    public CompanyBuilder withAddress(String address) {
        this.address = new Address(address);
        return this;
    }

    /**
     * Sets the company billing date to the specified billing date.
     *
     * @param date Billing date to be set.
     * @return CompanyBuilder object.
     */
    public CompanyBuilder withBillingDate(String date) {
        this.billingDate = new BillingDate(date);
        return this;
    }

    /**
     * Sets the company phone number to the specified phone number.
     *
     * @param phone Phone number to be set.
     * @return CompanyBuilder object.
     */
    public CompanyBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Builds a Company with the initialised attributes.
     *
     * @return Company object.
     */
    public Company build() {
        return new Company(name, address, billingDate, phone);
    }
}
