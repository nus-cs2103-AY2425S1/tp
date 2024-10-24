package seedu.address.testutil;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.commands.FilterCommand.FilterPersonDescriptor;
import seedu.address.model.person.Gender;
import seedu.address.model.person.Module;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterPersonDescriptorBuilder {

    private FilterCommand.FilterPersonDescriptor descriptor;

    public FilterPersonDescriptorBuilder() {
        this.descriptor = new FilterPersonDescriptor();
    }

    public FilterPersonDescriptorBuilder(FilterPersonDescriptor descriptor) {
        this.descriptor = new FilterPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code FilterPersonDescriptor} with fields containing {@code person}'s details
     */
    public FilterPersonDescriptorBuilder(Person person) {
        descriptor = new FilterPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setGender(person.getGender());
        descriptor.setModules(person.getModules());
        descriptor.setTags(person.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code FilterPersonDescriptor} that we are building.
     */
    public FilterPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code FilterPersonDescriptor} that we are building.
     */
    public FilterPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Parses the {@code modules} into a {@code Set<module>} and set it to the {@code FilterPersonDescriptor}
     * that we are building.
     */
    public FilterPersonDescriptorBuilder withModules(String... modules) {
        Set<Module> moduleSet = Stream.of(modules).map(Module::new).collect(Collectors.toSet());
        descriptor.setModules(moduleSet);
        return this;
    }

    /**
     * Sets the {@code Gender} of the {@code FilterPersonDescriptor} that we are building.
     */
    public FilterPersonDescriptorBuilder withGender(String gender) {
        descriptor.setGender(new Gender(gender));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code FilterPersonDescriptor}
     * that we are building.
     */
    public FilterPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public FilterPersonDescriptor build() {
        return descriptor;
    }
}
