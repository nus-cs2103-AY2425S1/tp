package seedu.address.testutil.property;

import seedu.address.logic.commands.property.EditCommand.EditPropertyDescriptor;
import seedu.address.model.property.AskingPrice;
import seedu.address.model.property.Property;
import seedu.address.model.property.LandlordName;
import seedu.address.model.property.Location;
import seedu.address.model.property.Phone;

/**
 * A utility class to help with building EditPropertyDescriptor objects.
 */
public class EditPropertyDescriptorBuilder {

    private EditPropertyDescriptor descriptor;

    public EditPropertyDescriptorBuilder() {
        descriptor = new EditPropertyDescriptor();
    }

    public EditPropertyDescriptorBuilder(EditPropertyDescriptor descriptor) {
        this.descriptor = new EditPropertyDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPropertyDescriptor} with fields containing {@code property}'s details
     */
    public EditPropertyDescriptorBuilder(Property property) {
        descriptor = new EditPropertyDescriptor();
        descriptor.setLandlordName(property.getName());
        descriptor.setPhone(property.getPhone());
        descriptor.setLocation(property.getLocation());
        descriptor.setAskingPrice(property.getAskingPrice());
    }

    /**
     * Sets the {@code LandlordName} of the {@code EditPropertyDescriptor} that we are building.
     */
    public EditPropertyDescriptorBuilder withLandlordName(String name) {
        descriptor.setLandlordName(new LandlordName(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPropertyDescriptor} that we are building.
     */
    public EditPropertyDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Location} of the {@code EditPropertyDescriptor} that we are building.
     */
    public EditPropertyDescriptorBuilder withLocation(String location) {
        descriptor.setLocation(new Location(location));
        return this;
    }

    /**
     * Sets the {@code AskingPrice} of the {@code EditPropertyDescriptor} that we are building.
     */
    public EditPropertyDescriptorBuilder withAskingPrice(String askingPrice) {
        descriptor.setAskingPrice(new AskingPrice(askingPrice));
        return this;
    }

    public EditPropertyDescriptor build() {
        return descriptor;
    }
}
