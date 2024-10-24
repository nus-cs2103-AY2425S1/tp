package seedu.address.testutil;

import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.model.commons.name.Name;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;

/**
 * A utility class to help with building EditEventDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventDescriptor descriptor) {
        this.descriptor = new EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing
     * {@code event}'s details
     */
    public EditEventDescriptorBuilder(Event event) {
        descriptor = new EditEventDescriptor();
        descriptor.setName(event.getName());
        descriptor.setDate(event.getDate());
    }

    /**
     * Sets the {@code Name} of the {@code EditEventDescriptor} that we are
     * building.
     */
    public EditEventDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditEventDescriptor} that we are
     * building.
     */
    public EditEventDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }
}
