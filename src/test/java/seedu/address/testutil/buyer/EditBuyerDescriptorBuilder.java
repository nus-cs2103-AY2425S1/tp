package seedu.address.testutil.buyer;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.buyer.EditCommand.EditBuyerDescriptor;
import seedu.address.model.buyer.Address;
import seedu.address.model.buyer.Buyer;
import seedu.address.model.buyer.BuyerType;
import seedu.address.model.buyer.Email;
import seedu.address.model.buyer.Name;
import seedu.address.model.buyer.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditBuyerDescriptor objects.
 */
public class EditBuyerDescriptorBuilder {

    private EditBuyerDescriptor descriptor;

    public EditBuyerDescriptorBuilder() {
        descriptor = new EditBuyerDescriptor();
    }

    public EditBuyerDescriptorBuilder(EditBuyerDescriptor descriptor) {
        this.descriptor = new EditBuyerDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditBuyerDescriptor} with fields containing {@code buyer}'s details
     */
    public EditBuyerDescriptorBuilder(Buyer buyer) {
        descriptor = new EditBuyerDescriptor();
        descriptor.setName(buyer.getName());
        descriptor.setPhone(buyer.getPhone());
        descriptor.setEmail(buyer.getEmail());
        descriptor.setAddress(buyer.getAddress());
        descriptor.setBuyerType(buyer.getBuyerType());
        descriptor.setTags(buyer.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditBuyerDescriptor} that we are building.
     */
    public EditBuyerDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditBuyerDescriptor} that we are building.
     */
    public EditBuyerDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditBuyerDescriptor} that we are building.
     */
    public EditBuyerDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditBuyerDescriptor} that we are building.
     */
    public EditBuyerDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code BuyerType} of the {@code EditBuyerDescriptor} that we are building.
     */
    public EditBuyerDescriptorBuilder withBuyerType(String buyerType) {
        descriptor.setBuyerType(new BuyerType(buyerType));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditBuyerDescriptor}
     * that we are building.
     */
    public EditBuyerDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditBuyerDescriptor build() {
        return descriptor;
    }
}
