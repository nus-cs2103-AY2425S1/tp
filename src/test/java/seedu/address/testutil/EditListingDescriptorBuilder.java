package seedu.address.testutil;

import seedu.address.logic.commands.EditListingCommand.EditListingDescriptor;
import seedu.address.model.listing.Address;
import seedu.address.model.listing.Area;
import seedu.address.model.listing.Listing;
import seedu.address.model.listing.Price;
import seedu.address.model.listing.Region;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;

import java.math.BigDecimal;

public class EditListingDescriptorBuilder {
    private EditListingDescriptor descriptor;

    public EditListingDescriptorBuilder() {
        descriptor = new EditListingDescriptor();
    }

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

    public EditListingDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    public EditListingDescriptorBuilder withName(Name name) {
        descriptor.setName(name);
        return this;
    }

    public EditListingDescriptorBuilder withPrice(String price) {
        descriptor.setPrice(new Price(price, new BigDecimal(price)));
        return this;
    }

    public EditListingDescriptorBuilder withPrice(Price price) {
        descriptor.setPrice(price);
        return this;
    }

    public EditListingDescriptorBuilder withArea(int area) {
        descriptor.setArea(new Area(area));
        return this;
    }

    public EditListingDescriptorBuilder withArea(Area area) {
        descriptor.setArea(area);
        return this;
    }

    public EditListingDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    public EditListingDescriptorBuilder withAddress(Address address) {
        descriptor.setAddress(address);
        return this;
    }

    public EditListingDescriptorBuilder withRegion(String region) {
        descriptor.setRegion(Region.fromString(region));
        return this;
    }

    public EditListingDescriptorBuilder withRegion(Region region) {
        descriptor.setRegion(region);
        return this;
    }

    public EditListingDescriptorBuilder withSeller(Name sellerName) {
        descriptor.setSellerName(sellerName);
        return this;
    }

    public EditListingDescriptorBuilder withSeller(Person seller) {
        descriptor.setSellerName(seller.getName());
        return this;
    }

    public EditListingDescriptor build() {
        return descriptor;
    }
}
