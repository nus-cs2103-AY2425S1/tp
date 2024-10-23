package seedu.address.testutil;

//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
//import seedu.address.model.person.Address;
//import seedu.address.model.person.Email;
import seedu.address.model.person.Diagnosis;
import seedu.address.model.person.Id;
import seedu.address.model.person.Medication;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Ward;
//import seedu.address.model.person.Phone;
//import seedu.address.model.tag.Tag;

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
        descriptor.setId(person.getId());
        descriptor.setName(person.getName());
        descriptor.setDiagnosis(person.getDiagnosis());
        descriptor.setWard(person.getWard());
        descriptor.setMedication(person.getMedication());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Id} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withId(String id) {
        descriptor.setId(new Id(id));
        return this;
    }

    /**
     * Sets the {@code Ward} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withWard(String ward) {
        descriptor.setWard(new Ward(ward));
        return this;
    }

    /**
     * Sets the {@code Diagnosis} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withDiagnosis(String diagnosis) {
        descriptor.setDiagnosis(new Diagnosis(diagnosis));
        return this;
    }

    /**
     * Sets the {@code Medication} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withMedication(String medication) {
        descriptor.setMedication(new Medication(medication));
        return this;
    }


    /*
    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    /*
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }
    */

    /*
    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    /*
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }
    /*
     */

    /*
    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    /*
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

     */

    /*
    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    /*
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

     */


    public EditPersonDescriptor build() {
        return descriptor;
    }
}
