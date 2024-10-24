package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.util.EditGuestDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Guest;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Relation;
import seedu.address.model.person.Rsvp;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditGuestDescriptor objects.
 */
public class EditGuestDescriptorBuilder {

    private EditGuestDescriptor descriptor;

    public EditGuestDescriptorBuilder() {
        descriptor = new EditGuestDescriptor();
    }

    public EditGuestDescriptorBuilder(EditGuestDescriptor descriptor) {
        this.descriptor = new EditGuestDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditGuestDescriptor} with fields containing {@code guest}'s details
     */
    public EditGuestDescriptorBuilder(Guest guest) {
        descriptor = new EditGuestDescriptor();
        descriptor.setName(guest.getName());
        descriptor.setPhone(guest.getPhone());
        descriptor.setEmail(guest.getEmail());
        descriptor.setAddress(guest.getAddress());
        descriptor.setTags(guest.getTags());
        descriptor.setRsvp(guest.getRsvp());
        descriptor.setRelation(guest.getRelation());
    }

    /**
     * Sets the {@code Name} of the {@code EditGuestDescriptor} that we are building.
     */
    public EditGuestDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditGuestDescriptor} that we are building.
     */
    public EditGuestDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditGuestDescriptor} that we are building.
     */
    public EditGuestDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditGuestDescriptor} that we are building.
     */
    public EditGuestDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditGuestDescriptor}
     * that we are building.
     */
    public EditGuestDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Sets the {@code Rsvp} of the {@code EditGuestDescriptor} that we are building.
     */
    public EditGuestDescriptorBuilder withRsvp(String rsvp) {
        descriptor.setRsvp(new Rsvp(rsvp));
        return this;
    }

    /**
     * Sets the {@code Relation} of the {@code EditGuestDescriptor} that we are building.
     */
    public EditGuestDescriptorBuilder withRelation(String relation) {
        descriptor.setRelation(new Relation(relation));
        return this;
    }

    public EditGuestDescriptor build() {
        return descriptor;
    }
}
