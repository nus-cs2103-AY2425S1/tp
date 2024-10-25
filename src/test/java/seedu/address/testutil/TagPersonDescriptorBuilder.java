package seedu.address.testutil;

import seedu.address.logic.commands.TagCommand.TagPersonDescriptor;
import seedu.address.model.person.Person;
import seedu.address.model.tag.Tag;

/**
 * A utility class to help with building TagPersonDescriptor objects.
 */
public class TagPersonDescriptorBuilder {

    private TagPersonDescriptor descriptor;

    public TagPersonDescriptorBuilder() {
        descriptor = new TagPersonDescriptor();
    }

    public TagPersonDescriptorBuilder(TagPersonDescriptor descriptor) {
        this.descriptor = new TagPersonDescriptor(descriptor);
    }

    public TagPersonDescriptorBuilder(Person person) {
        descriptor = new TagPersonDescriptor();
        if (person.getRole() != null) {
            descriptor.setTag(person.getRole());
        }
    }

    public TagPersonDescriptorBuilder withTag(String tag) {
        descriptor.setTag(tag != null ? new Tag(tag) : null);
        return this;
    }

    public TagPersonDescriptor build() {
        return descriptor;
    }
}
