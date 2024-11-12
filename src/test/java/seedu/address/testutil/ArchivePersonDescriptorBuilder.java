package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.ArchiveCommand.ArchivePersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class ArchivePersonDescriptorBuilder {

    private ArchivePersonDescriptor descriptor;

    public ArchivePersonDescriptorBuilder() {
        descriptor = new ArchivePersonDescriptor();
    }

    public ArchivePersonDescriptorBuilder(ArchivePersonDescriptor descriptor) {
        this.descriptor = new ArchivePersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code ArchivePersonDescriptor} with fields containing {@code person}'s details
     */
    public ArchivePersonDescriptorBuilder(Person person) {
        descriptor = new ArchivePersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setTags(person.getTags());
        descriptor.setPostalCode(person.getPostalCode());
        descriptor.setIsArchived(person.isArchived());
    }

    /**
     * Sets the {@code Name} of the {@code ArchivePersonDescriptor} that we are building.
     */
    public ArchivePersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code ArchivePersonDescriptor} that we are building.
     */
    public ArchivePersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code ArchivePersonDescriptor} that we are building.
     */
    public ArchivePersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code ArchivePersonDescriptor} that we are building.
     */
    public ArchivePersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code ArchivePersonDescriptor}
     * that we are building.
     */
    public ArchivePersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Sets the {@code PostalCode} of the {@code ArchivePersonDescriptor} that we are building.
     */
    public ArchivePersonDescriptorBuilder withPostalCode(String postalCode) {
        descriptor.setPostalCode(new PostalCode(postalCode));
        return this;
    }

    /**
     * Sets the {@code isArchived} of the {@code ArchivePersonDescriptor} that we are building.
     */
    public ArchivePersonDescriptorBuilder withIsArchived(Boolean isArchived) {
        descriptor.setIsArchived(isArchived);
        return this;
    }

    public ArchivePersonDescriptor build() {
        return descriptor;
    }
}
