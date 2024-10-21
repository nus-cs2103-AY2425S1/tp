package seedu.address.testutil;

import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;

/**
 * A utility class to help with building {@link EditEventDescriptor} objects.
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
     * Returns an {@link EditEventDescriptor} with fields containing {@link Event}'s details.
     */
    public EditEventDescriptorBuilder(Event event) {
        descriptor = new EditEventDescriptor();
        descriptor.setName(event.getName());
    }

    /**
     * Sets the {@link EventName} of the {@link EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withName(String name) {
        descriptor.setName(new EventName(name));
        return this;
    }

    public EditEventDescriptor build() {
        return descriptor;
    }
}
