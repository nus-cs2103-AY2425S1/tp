package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditSupplierDescriptor;
import seedu.address.model.product.Product;
import seedu.address.model.supplier.Company;
import seedu.address.model.supplier.Email;
import seedu.address.model.supplier.Name;
import seedu.address.model.supplier.Phone;
import seedu.address.model.supplier.Supplier;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditSupplierDescriptor objects.
 */
public class EditSupplierDescriptorBuilder {

    private EditSupplierDescriptor descriptor;

    public EditSupplierDescriptorBuilder() {
        descriptor = new EditSupplierDescriptor();
    }

    public EditSupplierDescriptorBuilder(EditSupplierDescriptor descriptor) {
        this.descriptor = new EditSupplierDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditSupplierDescriptor} with fields containing {@code supplier}'s details
     */
    public EditSupplierDescriptorBuilder(Supplier supplier) {
        descriptor = new EditSupplierDescriptor();
        descriptor.setName(supplier.getName());
        descriptor.setPhone(supplier.getPhone());
        descriptor.setEmail(supplier.getEmail());
        descriptor.setCompany(supplier.getCompany());
        descriptor.setTags(supplier.getTags());
        descriptor.setProducts(supplier.getProducts());
    }

    /**
     * Sets the {@code Name} of the {@code EditSupplierDescriptor} that we are building.
     */
    public EditSupplierDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditSupplierDescriptor} that we are building.
     */
    public EditSupplierDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditSupplierDescriptor} that we are building.
     */
    public EditSupplierDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditSupplierDescriptor} that we are building.
     */
    public EditSupplierDescriptorBuilder withCompany(String company) {
        descriptor.setCompany(new Company(company));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditSupplierDescriptor}
     * that we are building.
     */
    public EditSupplierDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Parses the {@code products} into a {@code Set<Product>} and set it to the {@code EditSupplierDescriptor}
     * that we are building.
     */
    public EditSupplierDescriptorBuilder withProducts(String... products) {
        Set<Product> productSet = Stream.of(products).map(Product::new).collect(Collectors.toSet());
        descriptor.setProducts(productSet);
        return this;
    }

    public EditSupplierDescriptor build() {
        return descriptor;
    }
}
