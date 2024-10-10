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
            new Member(new Name("Alex Yeoh"), new Telegram("alex_yeoh98"),
                new Room("12/04/201"),
                getTagSet("subcommLeader")),
            new Member(new Name("Bernice Yu"), new Telegram("BobLim23"),
                new Room("5/03/102"),
                getTagSet("competitionLeader", "achiever")),
            new Member(new Name("Charlotte Oliveiro"), new Telegram("oliveiro"),
                new Room("3/02/301"),
                getTagSet("team1")),
            new Member(new Name("David Li"), new Telegram("davidLiOnly"),
                new Room("14/06/301"),
                getTagSet("team3")),
            new Member(new Name("Irfan Ibrahim"), new Telegram("ibrahim_irfan"),
                new Room("3/05/323"),
                getTagSet("team2")),
            new Member(new Name("Roy Balakrishnan"), new Telegram("roy_da_bomb"),
                new Room("8/04/258"),
                getTagSet("team2"))
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
