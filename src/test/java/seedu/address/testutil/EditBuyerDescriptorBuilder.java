package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.buyer.EditCommand.EditPersonDescriptor;
import seedu.address.model.buyer.Address;
import seedu.address.model.buyer.BuyerType;
import seedu.address.model.buyer.Email;
import seedu.address.model.buyer.Name;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.buyer.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditBuyerDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditBuyerDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditBuyerDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code buyer}'s details
     */
    public EditBuyerDescriptorBuilder(Buyer buyer) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(buyer.getName());
        descriptor.setPhone(buyer.getPhone());
        descriptor.setEmail(buyer.getEmail());
        descriptor.setAddress(buyer.getAddress());
        descriptor.setBuyerType(buyer.getPersonType());
        descriptor.setTags(buyer.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBuyerDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBuyerDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBuyerDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBuyerDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code BuyerType} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditBuyerDescriptorBuilder withPersonType(String personType) {
        descriptor.setBuyerType(new BuyerType(personType));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditBuyerDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
