package seedu.address.testutil;

import java.util.HashSet;
import java.util.Set;

import seedu.address.model.product.Product;
import seedu.address.model.supplier.Company;
import seedu.address.model.supplier.Email;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Phone;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.supplier.SupplierStatus;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Supplier objects.
 */
public class SupplierBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_PHONE = "85355255";
    public static final String DEFAULT_EMAIL = "amy@gmail.com";
    public static final String DEFAULT_COMPANY = "company A";
    public static final String DEFAULT_STATUS = "active";

    private Name name;
    private Phone phone;
    private Email email;
    private Company company;
    private Set<Tag> tags;
    private Set<Product> products;
    private SupplierStatus status;

    /**
     * Creates a {@code SupplierBuilder} with the default details.
     */
    public SupplierBuilder() {
        name = new Name(DEFAULT_NAME);
        phone = new Phone(DEFAULT_PHONE);
        email = new Email(DEFAULT_EMAIL);
        company = new Company(DEFAULT_COMPANY);
        tags = new HashSet<>();
        products = new HashSet<>();
        status = new SupplierStatus(DEFAULT_STATUS);
    }

    /**
     * Initializes the SupplierBuilder with the data of {@code supplierToCopy}.
     */
    public SupplierBuilder(Supplier supplierToCopy) {
        name = supplierToCopy.getName();
        phone = supplierToCopy.getPhone();
        email = supplierToCopy.getEmail();
        company = supplierToCopy.getCompany();
        tags = new HashSet<>(supplierToCopy.getTags());
        products = new HashSet<>(supplierToCopy.getProducts());
        status = supplierToCopy.getStatus();
    }

    /**
     * Sets the {@code Name} of the {@code Supplier} that we are building.
     */
    public SupplierBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Supplier} that we are building.
     */
    public SupplierBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Parses the {@code products} into a {@code Set<Product>} and set it to the {@code Supplier} that we are building.
     */
    public SupplierBuilder withProducts(String ... products) {
        this.products = SampleDataUtil.getProductSet(products);
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
     * Sets the {@code Phone} of the {@code Supplier} that we are building.
     */
    public SupplierBuilder withPhone(String phone) {
        this.phone = new Phone(phone);
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code Supplier} that we are building.
     */
    public SupplierBuilder withEmail(String email) {
        this.email = new Email(email);
        return this;
    }
    /**
     * Sets the {@code SupplierStatus} of the {@code Supplier} that we are building.
     */
    public SupplierBuilder withStatus(String status) {
        this.status = new SupplierStatus(status);
        return this;
    }

    public Supplier build() {
        return new Supplier(name, phone, email, company, tags, products, status);
    }

}
