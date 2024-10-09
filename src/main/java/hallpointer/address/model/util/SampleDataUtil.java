package hallpointer.address.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import hallpointer.address.model.AddressBook;
import hallpointer.address.model.ReadOnlyAddressBook;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.Name;
import hallpointer.address.model.member.Room;
import hallpointer.address.model.member.Telegram;
import hallpointer.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {
    public static Member[] getSampleMembers() {
        return new Member[] {
            new Member(new Name("Alex Yeoh"), new Telegram("87438807"),
                new Room("2/3/1"),
                getTagSet("friends")),
            new Member(new Name("Bernice Yu"), new Telegram("99272758"),
                new Room("8/10/100"),
                getTagSet("colleagues", "friends")),
            new Member(new Name("Charlotte Oliveiro"), new Telegram("93210283"),
                new Room("9/5/59"),
                getTagSet("neighbours")),
            new Member(new Name("David Li"), new Telegram("91031282"),
                new Room("3/2/10"),
                getTagSet("family")),
            new Member(new Name("Irfan Ibrahim"), new Telegram("92492021"),
                new Room("20/10/294"),
                getTagSet("classmates")),
            new Member(new Name("Roy Balakrishnan"), new Telegram("92624417"),
                new Room("4/5/37"),
                getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Member sampleMember : getSampleMembers()) {
            sampleAb.addMember(sampleMember);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
