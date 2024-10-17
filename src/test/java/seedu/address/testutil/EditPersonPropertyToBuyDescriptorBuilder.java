package seedu.address.testutil;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.DeletePropertyToBuyCommand.EditPersonPropertyToBuyDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Property;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonPropertyToBuyDescriptorBuilder {

    private EditPersonPropertyToBuyDescriptor descriptor;

    public EditPersonPropertyToBuyDescriptorBuilder() {
        descriptor = new EditPersonPropertyToBuyDescriptor();
    }

    public EditPersonPropertyToBuyDescriptorBuilder(EditPersonPropertyToBuyDescriptor descriptor) {
        this.descriptor = new EditPersonPropertyToBuyDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonPropertyToBuyDescriptorBuilder(Person person) {
        descriptor = new EditPersonPropertyToBuyDescriptor();
    }

    public EditPersonPropertyToBuyDescriptor build() {
        return descriptor;
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonPropertyToBuyDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonPropertyToBuyDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonPropertyToBuyDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonPropertyToBuyDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonPropertyToBuyDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Sets the {@code buyingProperties} of the {@code EditPersonPropertyDescriptor} that we are building.
     */
    public EditPersonPropertyToBuyDescriptorBuilder withPropertyToBuy(Property propertyToBuy) {
        List<Property> propertyToBuyList = new ArrayList<>();
        propertyToBuyList.add(propertyToBuy);
        descriptor.setBuyingProperties(propertyToBuyList);
        return this;
    }

    /**
     * Sets the {@code sellingProperties} of the {@code EditPersonPropertyDescriptor} that we are building.
     */
    public EditPersonPropertyToBuyDescriptorBuilder withPropertyToSell(Property propertyToSell) {
        List<Property> propertyToSellList = new ArrayList<>();
        propertyToSellList.add(propertyToSell);
        descriptor.setBuyingProperties(propertyToSellList);
        return this;
    }
}
