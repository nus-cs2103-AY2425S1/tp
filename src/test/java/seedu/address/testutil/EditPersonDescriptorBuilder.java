package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.log.AppointmentDate;
import seedu.address.model.log.Log;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.IdentityNumber;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
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
        descriptor.setIdentityNumber(person.getIdentityNumber());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setTags(person.getTags());
        descriptor.setLogs(person.getLogs());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Identity Number} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withIdentityNumber(String identityNumber) {
        descriptor.setIdentityNumber(new IdentityNumber(identityNumber));
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
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }
    /**
     * Parses the {@code logs} into a {@code Set<Log>} and sets it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withLogs(String... logs) {
        Set<Log> logSet = Stream.of(logs)
                .map(log -> {
                    String[] logParts = log.split("\\|", 2);
                    if (logParts.length != 2) {
                        throw new IllegalArgumentException("Log format is invalid: " + log);
                    }
                    String dateStr = logParts[0].trim();
                    String details = logParts[1].trim();

                    AppointmentDate appointmentDate = new AppointmentDate(dateStr);

                    return new Log(appointmentDate, details);
                })
                .collect(Collectors.toSet());
        descriptor.setLogs(logSet);
        return this;
    }


    public EditPersonDescriptor build() {
        return descriptor;
    }
}
