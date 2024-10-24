package seedu.address.testutil;

import seedu.address.model.person.Budget;
import seedu.address.model.person.Company;
import seedu.address.model.person.Vendor;

/**
 * A utility class to help with building Vendor objects.
 */
public class VendorBuilder extends PersonBuilder<VendorBuilder> {
    public static final String DEFAULT_COMPANY = "The Wedding People";

    private Company company;
    private Budget budget;

    /**
     * Creates a {@code VendorBuilder} with the default details.
     */
    public VendorBuilder() {
        super();
        company = new Company(DEFAULT_COMPANY);
    }

    /**
     * Initializes the {@code VendorBuilder} with the data of {@code vendorToCopy}.
     */
    public VendorBuilder(Vendor vendorToCopy) {
        super(vendorToCopy);
        company = vendorToCopy.getCompany();
    }

    /**
     * Sets the {@code Company} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withCompany(String company) {
        this.company = new Company(company);
        return this;
    }

    /**
     * Sets the {@code Company} of the {@code Vendor} that we are building.
     */
    public VendorBuilder withBudget(Double budget) {
        this.budget = new Budget(budget);
        return this;
    }

    public Vendor build() {
        return new Vendor(getName(), getPhone(), getEmail(), getAddress(), getTags(), company);
    }
}
