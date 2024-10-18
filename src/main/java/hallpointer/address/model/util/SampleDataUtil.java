package hallpointer.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import hallpointer.address.model.AddressBook;
import hallpointer.address.model.ReadOnlyAddressBook;
import hallpointer.address.model.member.Member;
import hallpointer.address.model.member.Name;
import hallpointer.address.model.member.Room;
import hallpointer.address.model.member.Telegram;
import hallpointer.address.model.point.Point;
import hallpointer.address.model.session.Session;
import hallpointer.address.model.session.SessionDate;
import hallpointer.address.model.session.SessionName;
import hallpointer.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    public static Member[] getSampleMembers() {
        return new Member[] {
            new Member(new Name("Alex Yeoh"), new Telegram("alex_yeoh98"),
                new Room("12/04/201"),
                getTagSet("subcommLeader"), new Point("6"),
                getSessionSet(
                    new SessionName[]{new SessionName("AGM"), new SessionName("Team Meeting")},
                    new SessionDate[]{new SessionDate("24 Sep 2024"), new SessionDate("02 Oct 2024")},
                    new Point[]{new Point("6"), new Point("4")}
                )),
            new Member(new Name("Bernice Yu"), new Telegram("BobLim23"),
                new Room("5/03/102"),
                getTagSet("competitionLeader", "achiever"),
                new Point("10"),
                getSessionSet(
                    new SessionName[]{new SessionName("Workshop")},
                    new SessionDate[]{new SessionDate("15 Oct 2024")},
                    new Point[]{new Point("10")}
                )),
            new Member(new Name("Charlotte Oliveiro"), new Telegram("oliveiro"),
                new Room("3/02/301"),
                getTagSet("team1"), new Point("5"),
                getSessionSet(
                    new SessionName[]{new SessionName("Team Bonding")},
                    new SessionDate[]{new SessionDate("20 Sep 2024")},
                    new Point[]{new Point("5")}
                )),
            new Member(new Name("David Li"), new Telegram("davidLiOnly"),
                new Room("14/06/301"),
                getTagSet("team3"),
                new Point("7"),
                getSessionSet()), // Empty session set
            new Member(new Name("Irfan Ibrahim"), new Telegram("ibrahim_irfan"),
                new Room("3/05/323"),
                getTagSet("team2"),
                new Point("3"),
                getSessionSet()), // Empty session set
            new Member(new Name("Roy Balakrishnan"), new Telegram("roy_da_bomb"),
                new Room("8/04/258"),
                getTagSet("team2"),
                new Point("5"),
                getSessionSet()) // Empty session set
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

    /**
     * Returns a session set containing the list of information given.
     * If no arguments are provided, returns an empty session set.
     */
    public static Set<Session> getSessionSet(SessionName[] names, SessionDate[] dates, Point[] points) {
        Set<Session> sessionSet = new HashSet<>();
        for (int i = 0; i < names.length; i++) {
            sessionSet.add(new Session(names[i], dates[i], points[i]));
        }
        return sessionSet;
    }

    /**
     * Returns an empty session set if no sessions are provided.
     */
    public static Set<Session> getSessionSet() {
        return new HashSet<>();
    }
}
