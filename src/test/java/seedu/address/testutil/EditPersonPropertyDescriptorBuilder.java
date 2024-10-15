package seedu.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.DeletePropertyToBuyCommand;
import seedu.address.logic.commands.DeletePropertyToBuyCommand.EditPersonPropertyDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonPropertyDescriptorBuilder {

    private EditPersonPropertyDescriptor descriptor;

    public EditPersonPropertyDescriptorBuilder() {
        descriptor = new EditPersonPropertyDescriptor();
    }

    public EditPersonPropertyDescriptorBuilder(EditPersonPropertyDescriptor descriptor) {
        this.descriptor = new EditPersonPropertyDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonPropertyDescriptorBuilder(Person person) {
        descriptor = new EditPersonPropertyDescriptor();
    }

    public EditPersonPropertyDescriptor build() {
        return descriptor;
    }
}
