package hallpointer.address.testutil;

import java.util.HashSet;
import java.util.Set;

import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.Name;
import hallpointer.address.model.member.Room;
import hallpointer.address.model.member.TelegramHandle;
import hallpointer.address.model.tag.Tag;
import hallpointer.address.model.util.SampleDataUtil;

/**
 * A utility class to help with building Member objects.
 */
public class MemberBuilder {

    public static final String DEFAULT_NAME = "Amy Bee";
    public static final String DEFAULT_TELEGRAM_HANDLE = "85355255";
    public static final String DEFAULT_ROOM = "5/12/207";

    private Name name;
    private TelegramHandle telegramHandle;
    private Room room;
    private Set<Tag> tags;

    /**
     * Creates a {@code MemberBuilder} with the default details.
     */
    public MemberBuilder() {
        name = new Name(DEFAULT_NAME);
        telegramHandle = new TelegramHandle(DEFAULT_TELEGRAM_HANDLE);
        room = new Room(DEFAULT_ROOM);
        tags = new HashSet<>();
    }

    /**
     * Initializes the MemberBuilder with the data of {@code memberToCopy}.
     */
    public MemberBuilder(Member memberToCopy) {
        name = memberToCopy.getName();
        telegramHandle = memberToCopy.getTelegramHandle();
        room = memberToCopy.getRoom();
        tags = new HashSet<>(memberToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Member} that we are building.
     */
    public MemberBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Member} that we are building.
     */
    public MemberBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code Room} of the {@code Member} that we are building.
     */
    public MemberBuilder withRoom(String room) {
        this.room = new Room(room);
        return this;
    }

    /**
     * Sets the {@code TelegramHandle} of the {@code Member} that we are building.
     */
    public MemberBuilder withTelegramHandle(String telegramHandle) {
        this.telegramHandle = new TelegramHandle(telegramHandle);
        return this;
    }


    public Member build() {
        return new Member(name, telegramHandle, room, tags);
    }

}
