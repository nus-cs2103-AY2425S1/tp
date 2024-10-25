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

    /**
     * Returns an {@code TagPersonDescriptor} with fields containing {@code person}'s tag details.
     */
    public TagPersonDescriptorBuilder(Person person) {
        descriptor = new TagPersonDescriptor();
        if (!person.getTags().isEmpty()) {
            descriptor.setTag(person.getTags().iterator().next());
        }
    }

    /**
     * Sets a single tag to the {@code TagPersonDescriptor} that we are building.
     */
    public TagPersonDescriptorBuilder withTag(String tag) {
        descriptor.setTag(tag != null ? new Tag(tag) : null);
        return this;
    }

    public TagPersonDescriptor build() {
        return descriptor;
    }
}
