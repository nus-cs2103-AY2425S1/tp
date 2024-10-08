package hallpointer.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hallpointer.address.logic.commands.EditCommand.EditMemberDescriptor;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.Name;
import hallpointer.address.model.member.Phone;
import hallpointer.address.model.member.Room;
import hallpointer.address.model.tag.Tag;

/**
 * A utility class to help with building EditMemberDescriptor objects.
 */
public class EditMemberDescriptorBuilder {

    private EditMemberDescriptor descriptor;

    public EditMemberDescriptorBuilder() {
        descriptor = new EditMemberDescriptor();
    }

    public EditMemberDescriptorBuilder(EditMemberDescriptor descriptor) {
        this.descriptor = new EditMemberDescriptor(descriptor);
    }

    /**
     * Returns an {@code EditMemberDescriptor} with fields containing {@code member}'s details
     */
    public EditMemberDescriptorBuilder(Member member) {
        descriptor = new EditMemberDescriptor();
        descriptor.setName(member.getName());
        descriptor.setPhone(member.getPhone());
        descriptor.setRoom(member.getRoom());
        descriptor.setTags(member.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code EditMemberDescriptor} that we are building.
     */
    public EditMemberDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Phone} of the {@code EditMemberDescriptor} that we are building.
     */
    public EditMemberDescriptorBuilder withPhone(String phone) {
        descriptor.setPhone(new Phone(phone));
        return this;
    }

    /**
     * Sets the {@code Room} of the {@code EditMemberDescriptor} that we are building.
     */
    public EditMemberDescriptorBuilder withRoom(String room) {
        descriptor.setRoom(new Room(room));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code EditMemberDescriptor}
     * that we are building.
     */
    public EditMemberDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public EditMemberDescriptor build() {
        return descriptor;
    }
}
