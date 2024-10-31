package tuteez.testutil;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tuteez.logic.commands.EditCommand.EditPersonDescriptor;
import tuteez.model.person.Address;
import tuteez.model.person.Email;
import tuteez.model.person.Name;
import tuteez.model.person.Person;
import tuteez.model.person.Phone;
import tuteez.model.person.TelegramUsername;
import tuteez.model.person.lesson.Lesson;
import tuteez.model.tag.Tag;

/**
 * A utility class to help with building EditPersonDescriptor objects.
 */
public class EditPersonDescriptorBuilder {

    private EditPersonDescriptor descriptor;

    public EditPersonDescriptorBuilder() {
        descriptor = new EditPersonDescriptor();
    }

    public EditPersonDescriptorBuilder(EditPersonDescriptor descriptor) {
        this.descriptor = new EditPersonDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditPersonDescriptor} with fields containing {@code person}'s details
     */
    public EditPersonDescriptorBuilder(Person person) {
        descriptor = new EditPersonDescriptor();
        descriptor.setName(person.getName());
        descriptor.setPhone(person.getPhone());
        descriptor.setEmail(person.getEmail());
        descriptor.setAddress(person.getAddress());
        descriptor.setTelegramUsername(person.getTelegramUsername());
        descriptor.setTags(person.getTags());
        descriptor.setLessons(person.getLessons());
    }

    /**
     * Sets the {@code Name} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Email} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withEmail(String email) {
        descriptor.setEmail(new Email(email));
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAddress(String address) {
        descriptor.setAddress(new Address(address));
        return this;
    }

    /**
     * Sets the {@code TelegramUsername} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withTelegram(String username) {
        if (username == null) {
            descriptor.setTelegramUsername(TelegramUsername.empty());
        } else {
            descriptor.setTelegramUsername(TelegramUsername.of(username));
        }
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    /**
     * Parses the {@code lessons} into a {@code Set<Lesson>} and set it to the {@code EditPersonDescriptor}
     * that we are building.
     */
    public EditPersonDescriptorBuilder withLessons(String... lessons) {
        List<Lesson> lessonLst = Stream.of(lessons).map(Lesson::new).collect(Collectors.toList());
        descriptor.setLessons(lessonLst);
        return this;
    }

    public EditPersonDescriptor build() {
        return descriptor;
    }
}
