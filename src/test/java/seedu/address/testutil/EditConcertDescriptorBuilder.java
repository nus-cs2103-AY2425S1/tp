package seedu.address.testutil;

import seedu.address.logic.commands.EditConcertCommand.EditConcertDescriptor;
import seedu.address.model.commons.Address;
import seedu.address.model.commons.Name;
import seedu.address.model.concert.Concert;
import seedu.address.model.concert.ConcertDate;

/**
 * A utility class to help with building EditConcertDescriptor objects.
 */
public class EditConcertDescriptorBuilder {
    private EditConcertDescriptor descriptor;

    public EditConcertDescriptorBuilder() {
        descriptor = new EditConcertDescriptor();
    }

    public EditConcertDescriptorBuilder(EditConcertDescriptor descriptor) {
        this.descriptor = new EditConcertDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditConcertDescriptorBuilder(Concert concert) {
        descriptor = new EditConcertDescriptor();
        descriptor.setName(concert.getName());
        descriptor.setAddress(concert.getAddress());
        descriptor.setDate(concert.getDate());
    }

    /**
     * Sets the {@code Name} of the {@code EditConcertDescriptor} that is being built.
     */
    public EditConcertDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditConcertDescriptor} that is being built.
     */
    public EditConcertDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditConcertDescriptor} that is being built.
     */
    public EditConcertDescriptorBuilder withDate(String date) {
        descriptor.setDate(new ConcertDate(date));
        return this;
    }

    public EditConcertDescriptor build() {
        return descriptor;
    }
}
