package seedu.address.testutil;

import seedu.address.logic.commands.EditwCommand.EditWeddingDescriptor;
import seedu.address.model.person.Name;
import seedu.address.model.wedding.Date;
import seedu.address.model.wedding.Venue;
import seedu.address.model.wedding.Wedding;

/**
 * A utility class to help with building EditWeddingDescriptor objects.
 */
public class EditWeddingDescriptorBuilder {

    private EditWeddingDescriptor descriptor;

    public EditWeddingDescriptorBuilder() {
        descriptor = new EditWeddingDescriptor();
    }

    /**
     * Initializes the EditWeddingDescriptorBuilder with the data of {@code descriptor}.
     */
    public EditWeddingDescriptorBuilder(EditWeddingDescriptor descriptor) {
        this.descriptor = new EditWeddingDescriptor();
        if (descriptor.getName().isPresent()) {
            this.descriptor.setName(descriptor.getName().get());
        }
        if (descriptor.getDate().isPresent()) {
            this.descriptor.setDate(descriptor.getDate().get());
        }
        if (descriptor.getVenue().isPresent()) {
            this.descriptor.setVenue(descriptor.getVenue().get());
        }
    }

    /**
     * Returns an {@code EditWeddingDescriptor} with fields containing {@code wedding}'s details
     */
    public EditWeddingDescriptorBuilder(Wedding wedding) {
        descriptor = new EditWeddingDescriptor();
        descriptor.setName(wedding.getName());
        descriptor.setDate(wedding.getDate());
        descriptor.setVenue(wedding.getVenue());
    }

    /**
     * Sets the {@code Name} of the {@code EditWeddingDescriptor} that we are building.
     */
    public EditWeddingDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditWeddingDescriptor} that we are building.
     */
    public EditWeddingDescriptorBuilder withDate(String date) {
        descriptor.setDate(new Date(date));
        return this;
    }

    /**
     * Sets the {@code Venue} of the {@code EditWeddingDescriptor} that we are building.
     */
    public EditWeddingDescriptorBuilder withVenue(String venue) {
        descriptor.setVenue(new Venue(venue));
        return this;
    }

    public EditWeddingDescriptor build() {
        return descriptor;
    }
}
