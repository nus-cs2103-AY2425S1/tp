package hallpointer.address.testutil;

import hallpointer.address.model.session.Session;

/**
 * A utility class containing a list of {@code Session} objects to be used in tests.
 */
public class TypicalSessions {

    public static final Session REHEARSAL = new SessionBuilder().withSessionName("Rehearsal W1")
            .withDate("28 Aug 2019").withPoints("20").build();

    public static final Session MEETING = new SessionBuilder().withSessionName("Meeting W1")
            .withDate("18 Feb 2020").withPoints("1").build();

    public static final Session ATTENDANCE = new SessionBuilder().withSessionName("Meeting W1")
            .withDate("03 Mar 2000").withPoints("0").build();
}
