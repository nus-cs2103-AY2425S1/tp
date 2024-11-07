package seedu.address.testutil;

import java.math.BigDecimal;

import seedu.address.logic.commands.listingcommands.EditListingCommand.EditListingDescriptor;
import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

/**
 * A utility class to help with building EditListingDescriptor objects.
 * Used ChatGPT to assist in writing JavaDocs
 */
public class EditListingDescriptorBuilder {
    private EditListingDescriptor descriptor;

    /**
     * Creates a new {@code EditListingDescriptorBuilder} with an empty {@code EditListingDescriptor}.
     */
    public EditListingDescriptorBuilder() {
        descriptor = new EditListingDescriptor();
    }

    /**
     * Initializes the {@code EditListingDescriptorBuilder} with an existing {@code EditListingDescriptor}.
     *
     * @param descriptor The existing descriptor to copy from.
     */
    public EditListingDescriptorBuilder(EditListingDescriptor descriptor) {
        this.descriptor = new EditListingDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditListingDescriptor} with fields containing {@code listing}'s details
     */
    public EditListingDescriptorBuilder(Listing listing) {
        descriptor = new EditListingDescriptor();
        descriptor.setName(listing.getName());
        descriptor.setPrice(listing.getPrice());
        descriptor.setAddress(listing.getAddress());
        descriptor.setArea(listing.getArea());
        descriptor.setRegion(listing.getRegion());
        descriptor.setSellerName(listing.getSeller().getName());
    }

    /**
     * Sets the {@code Name} of the {@code EditListingDescriptor} that we are building.
     *
     * @param name The name of the listing.
     * @return This EditListingDescriptorBuilder object for method chaining.
     */
    public EditListingDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Name} of the {@code EditListingDescriptor} that we are building.
     *
     * @param name The Name object for the listing.
     * @return This EditListingDescriptorBuilder object for method chaining.
     */
    public EditListingDescriptorBuilder withName(Name name) {
        descriptor.setName(name);
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code EditListingDescriptor} that we are building.
     *
     * @param price The price as a string.
     * @return This EditListingDescriptorBuilder object for method chaining.
     */
    public EditListingDescriptorBuilder withPrice(String price) {
        descriptor.setPrice(new Price(price, new BigDecimal(price)));
        return this;
    }

    /**
     * Sets the {@code Price} of the {@code EditListingDescriptor} that we are building.
     *
     * @param price The Price object for the listing.
     * @return This EditListingDescriptorBuilder object for method chaining.
     */
    public EditListingDescriptorBuilder withPrice(Price price) {
        descriptor.setPrice(price);
        return this;
    }

    /**
     * Sets the {@code Area} of the {@code EditListingDescriptor} that we are building.
     *
     * @param area The area as a String.
     * @return This EditListingDescriptorBuilder object for method chaining.
     */
    public EditListingDescriptorBuilder withArea(String area) {
        descriptor.setArea(new Area(area));
        return this;
    }

    /**
     * Sets the {@code Area} of the {@code EditListingDescriptor} that we are building.
     *
     * @param area The Area object for the listing.
     * @return This EditListingDescriptorBuilder object for method chaining.
     */
    public EditListingDescriptorBuilder withArea(Area area) {
        descriptor.setArea(area);
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditListingDescriptor} that we are building.
     *
     * @param address The address as a string.
     * @return This EditListingDescriptorBuilder object for method chaining.
     */
    public EditListingDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditListingDescriptor} that we are building.
     *
     * @param address The Address object for the listing.
     * @return This EditListingDescriptorBuilder object for method chaining.
     */
    public EditListingDescriptorBuilder withAddress(Address address) {
        descriptor.setAddress(address);
        return this;
    }

    /**
     * Sets the {@code Region} of the {@code EditListingDescriptor} that we are building.
     *
     * @param region The region as a string.
     * @return This EditListingDescriptorBuilder object for method chaining.
     */
    public EditListingDescriptorBuilder withRegion(String region) {
        descriptor.setRegion(Region.fromString(region));
        return this;
    }

    /**
     * Sets the {@code Region} of the {@code EditListingDescriptor} that we are building.
     *
     * @param region The Region object for the listing.
     * @return This EditListingDescriptorBuilder object for method chaining.
     */
    public EditListingDescriptorBuilder withRegion(Region region) {
        descriptor.setRegion(region);
        return this;
    }

    /**
     * Sets the seller's {@code Name} of the {@code EditListingDescriptor} that we are building.
     *
     * @param sellerName The seller's name.
     * @return This EditListingDescriptorBuilder object for method chaining.
     */
    public EditListingDescriptorBuilder withSeller(Name sellerName) {
        descriptor.setSellerName(sellerName);
        return this;
    }

    /**
     * Sets the seller's {@code Name} of the {@code EditListingDescriptor} that we are building.
     *
     * @param seller The seller's Person object.
     * @return This EditListingDescriptorBuilder object for method chaining.
     */
    public EditListingDescriptorBuilder withSeller(Person seller) {
        descriptor.setSellerName(seller.getName());
        return this;
    }

    /**
     * Builds and returns the {@code EditListingDescriptor}.
     *
     * @return The built EditListingDescriptor object.
     */
    public EditListingDescriptor build() {
        return descriptor;
    }
}
