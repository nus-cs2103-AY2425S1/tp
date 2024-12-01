package seedu.address.testutil;

import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.DoctorName;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Relationship;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setIndexOfEmergencyContactToEdit(INDEX_FIRST_PERSON);
        descriptor.setEmergencyContactName(person.getFirstEmergencyContact().getName());
        descriptor.setEmergencyContactPhone(person.getFirstEmergencyContact().getPhone());
        descriptor.setEmergencyContactRelationship(person.getFirstEmergencyContact().getRelationship());
        descriptor.setDoctorName(person.getDoctor().getName());
        descriptor.setDoctorPhone(person.getDoctor().getPhone());
        descriptor.setDoctorEmail(person.getDoctor().getEmail());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code EmergencyContact Index} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmergencyContactIndex(Index index) {
        descriptor.setIndexOfEmergencyContactToEdit(index);
        return this;
    }

    /**
     * Sets the {@code EmergencyContact Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmergencyContactName(String name) {
        descriptor.setEmergencyContactName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code EmergencyContact Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmergencyContactPhone(String phone) {
        descriptor.setEmergencyContactPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code EmergencyContact relationship} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmergencyContactRelationship(String relationship) {
        descriptor.setEmergencyContactRelationship(new Relationship(relationship));
        return this;
    }

    /**
     * Sets the {@code Doctor Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withDoctorName(String name) {
        descriptor.setDoctorName(new DoctorName(name));
        return this;
    }

    /**
     * Sets the {@code Doctor Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withDoctorPhone(String phone) {
        descriptor.setDoctorPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Doctor Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withDoctorEmail(String email) {
        descriptor.setDoctorEmail(new Email(email));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
