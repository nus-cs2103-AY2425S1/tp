package hallpointer.address.testutil;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import hallpointer.address.logic.commands.UpdateMemberCommand.UpdateMemberDescriptor;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.Name;
import hallpointer.address.model.member.Room;
import hallpointer.address.model.member.Telegram;
import hallpointer.address.model.tag.Tag;

/**
 * A utility class to help with building UpdateMemberDescriptor objects.
 */
public class UpdateMemberDescriptorBuilder {

    private UpdateMemberDescriptor descriptor;

    public UpdateMemberDescriptorBuilder() {
        descriptor = new UpdateMemberDescriptor();
    }

    public UpdateMemberDescriptorBuilder(UpdateMemberDescriptor descriptor) {
        this.descriptor = new UpdateMemberDescriptor(descriptor);
    }

    /**
     * Returns an {@code UpdateMemberDescriptor} with fields containing {@code member}'s details
     */
    public UpdateMemberDescriptorBuilder(Member member) {
        descriptor = new UpdateMemberDescriptor();
        descriptor.setName(member.getName());
        descriptor.setTelegram(member.getTelegram());
        descriptor.setRoom(member.getRoom());
        descriptor.setTags(member.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code UpdateMemberDescriptor} that we are building.
     */
    public UpdateMemberDescriptorBuilder withName(String name) {
        descriptor.setName(new Name(name));
        return this;
    }

    /**
     * Sets the {@code Telegram} of the {@code UpdateMemberDescriptor} that we are building.
     */
    public UpdateMemberDescriptorBuilder withTelegram(String telegram) {
        descriptor.setTelegram(new Telegram(telegram));
        return this;
    }

    /**
     * Sets the {@code Room} of the {@code UpdateMemberDescriptor} that we are building.
     */
    public UpdateMemberDescriptorBuilder withRoom(String room) {
        descriptor.setRoom(new Room(room));
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code UpdateMemberDescriptor}
     * that we are building.
     */
    public UpdateMemberDescriptorBuilder withTags(String... tags) {
        Set<Tag> tagSet = Stream.of(tags).map(Tag::new).collect(Collectors.toSet());
        descriptor.setTags(tagSet);
        return this;
    }

    public UpdateMemberDescriptor build() {
        return descriptor;
    }
}
