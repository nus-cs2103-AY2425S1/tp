package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.eventcommands.EditEventCommand;
import seedu.address.model.tag.Tag;
import seedu.address.model.types.common.Address;
import seedu.address.model.types.common.DateTime;
import seedu.address.model.types.common.Name;
import seedu.address.model.types.event.Event;

/**
 * A utility class to help with building EditEventDescriptor objects.
 */
public class EditEventDescriptorBuilder {

    private EditEventCommand.EditEventDescriptor descriptor;

    public EditEventDescriptorBuilder() {
        descriptor = new EditEventCommand.EditEventDescriptor();
    }

    public EditEventDescriptorBuilder(EditEventCommand.EditEventDescriptor descriptor) {
        this.descriptor = new EditEventCommand.EditEventDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditEventDescriptor} with fields containing {@code Event}'s details
     */
    public EditEventDescriptorBuilder(Event event) {
        descriptor = new EditEventCommand.EditEventDescriptor();
        descriptor.setName(event.getName());
        descriptor.setAddress(event.getLocation());
        descriptor.setStartTime(event.getStartTime());
        descriptor.setTags(event.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code StartTime} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withStartTime(String startTime) {
        descriptor.setStartTime(new DateTime(startTime));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditEventDescriptor}
     * that we are building.
     */
    public EditEventDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditEventCommand.EditEventDescriptor build() {
        return descriptor;
    }
}
