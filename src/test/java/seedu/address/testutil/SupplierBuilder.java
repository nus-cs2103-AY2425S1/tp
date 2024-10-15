package seedu.address.testutil;

import seedu.address.model.product.Product;
import seedu.address.model.supplier.Company;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.SupplierContact;
import seedu.address.model.supplier.SupplierEmail;
import seedu.address.model.supplier.SupplierName;
import seedu.address.model.supplier.SupplierStatus;

/**
 * A utility class to help with building Supplier objects.
 */
public class SupplierBuilder {
    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amybee@gmail.com";
    public static final String DEFAULT_COMPANY = "Amy Bee Company";
    public static final String DEFAULT_PRODUCT = "bread";
    public static final String DEFAULT_STATUS = "active";
    private SupplierName name;
    private SupplierContact contact;
    private SupplierEmail email;
    private Company company;
    private Product product;
    private SupplierStatus status;
    /**
     * Creates a {@code SupplierBuilder} with the default details.
     */
    public SupplierBuilder() {
        name = new SupplierName(DEFAULT_NAME);
        contact = new SupplierContact(DEFAULT_PHONE);
        email = new SupplierEmail(DEFAULT_EMAIL);
        company = new Company(DEFAULT_COMPANY);
        product = new Product(DEFAULT_PRODUCT);
        status = new SupplierStatus(DEFAULT_STATUS);
    }
    /**
     * Initializes the SupplierBuilder with the data of {@code supplierToCopy}.
     */
    public SupplierBuilder(Supplier supplierToCopy) {
        name = supplierToCopy.getName();
        contact = supplierToCopy.getPhone();
        email = supplierToCopy.getEmail();
        company = supplierToCopy.getCompany();
        product = supplierToCopy.getProduct();
        status = supplierToCopy.getSupplierStatus();
    }
    /**
     * Sets the {@code Name} of the {@code Supplier} that we are building.
     */
    public SupplierBuilder withName(String name) {
        this.name = new SupplierName(name);
        return this;
    }
    /**
     * Sets the {@code Phone} of the {@code Supplier} that we are building.
     */
    public SupplierBuilder withPhone(String phone) {
        this.contact = new SupplierContact(phone);
        return this;
    }
    /**
     * Sets the {@code Email} of the {@code Supplier} that we are building.
     */
    public SupplierBuilder withEmail(String email) {
        this.email = new SupplierEmail(email);
        return this;
    }
    /**
     * Sets the {@code Company} of the {@code Supplier} that we are building.
     */
    public SupplierBuilder withCompany(String company) {
        this.company = new Company(company);
        return this;
    }
    /**
     * Sets the {@code Product} of the {@code Supplier} that we are building.
     */
    public SupplierBuilder withProduct(String product) {
        this.product = new Product(product);
        return this;
    }
    /**
     * Sets the {@code Status} of the {@code Supplier} that we are building.
     */
    public SupplierBuilder withStatus(String status) {
        this.status = new SupplierStatus(status);
        return this;
    }
    public Supplier build() {
        return new Supplier(name, contact, email, company, product, status);
    }
}
