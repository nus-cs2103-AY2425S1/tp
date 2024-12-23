package hallpointer.address.model.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import hallpointer.address.model.HallPointer;
import hallpointer.address.model.ReadOnlyHallPointer;
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
 * Contains utility methods for populating {@code HallPointer} with sample data.
 */
public class SampleDataUtil {

    public static Member[] getSampleMembers() {
        return new Member[] {
            new Member(new Name("Alex Yeoh"), new Telegram("alex_yeoh98"),
                new Room("12-4-201"),
                getTagSet("subcommLeader"),
                getSessionSet(
                    new Session(new SessionName("AGM"), new SessionDate("24 Sep 2024"), new Point("6")),
                    new Session(new SessionName("Team Meeting"), new SessionDate("02 Oct 2024"), new Point("4"))
                )),
            new Member(new Name("Bernice Yu"), new Telegram("BobLim23"),
                new Room("5-3-102"),
                getTagSet("competitionLeader", "achiever"),
                getSessionSet(
                    new Session(new SessionName("Workshop"), new SessionDate("15 Oct 2024"), new Point("10"))
                )),
            new Member(new Name("Charlotte Oliveiro"), new Telegram("oliveiro"),
                new Room("3-2-301"),
                getTagSet("team1"),
                getSessionSet(
                    new Session(new SessionName("Team Bonding"), new SessionDate("20 Sep 2024"), new Point("5"))
                )),
            new Member(new Name("David Li"), new Telegram("davidLiOnly"),
                new Room("14-6-301"),
                getTagSet("team3"),
                getSessionSet()), // Empty session set
            new Member(new Name("Irfan Ibrahim"), new Telegram("ibrahim_irfan"),
                new Room("3-5-323"),
                getTagSet("team2"),
                getSessionSet()), // Empty session set
            new Member(new Name("Roy Balakrishnan"), new Telegram("roy_da_bomb"),
                new Room("8-4-258"),
                getTagSet("team2"),
                getSessionSet()) // Empty session set
        };
    }

    public static ReadOnlyHallPointer getSampleHallPointer() {
        HallPointer sampleAb = new HallPointer();
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
     * Returns a session set containing the list of sessions provided.
     * If no sessions are provided, returns an empty session set.
     */
    public static Set<Session> getSessionSet(Session... sessions) {
        return Arrays.stream(sessions)
                .collect(Collectors.toSet());
    }

    /**
     * Returns an empty session set if no sessions are provided.
     */
    public static Set<Session> getSessionSet() {
        return new HashSet<>();
    }
}
