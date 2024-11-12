package seedu.address.testutil;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import seedu.address.logic.commands.EditWeddingCommand.EditWeddingDescriptor;
import seedu.address.model.wedding.Wedding;
import seedu.address.model.wedding.WeddingDate;
import seedu.address.model.wedding.WeddingName;


/**
 * A utility class to help with building EditWeddingDescriptor objects.
 */
public class EditWeddingDescriptorBuilder {

    private EditWeddingDescriptor descriptor;

    public EditWeddingDescriptorBuilder() {
        descriptor = new EditWeddingDescriptor();
    }

    public EditWeddingDescriptorBuilder(EditWeddingDescriptor descriptor) {
        this.descriptor = new EditWeddingDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditWeddingDescriptor} with fields containing {@code Wedding}'s details
     */
    public EditWeddingDescriptorBuilder(Wedding wedding) {
        descriptor = new EditWeddingDescriptor();
        descriptor.setWeddingName(wedding.getWeddingName());
        descriptor.setWeddingDate(wedding.getWeddingDate());
    }

    /**
     * Sets the {@code Name} of the {@code EditWeddingDescriptor} that we are building.
     */
    public EditWeddingDescriptorBuilder withWeddingName(String name) {
        descriptor.setWeddingName(new WeddingName(name));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditWeddingDescriptor} that we are building.
     */
    public EditWeddingDescriptorBuilder withWeddingDate(String date) {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        descriptor.setWeddingDate(new WeddingDate(LocalDate.parse(date, formatter)));
        return this;
    }

    public EditWeddingDescriptor build() {
        return descriptor;
    }
}
