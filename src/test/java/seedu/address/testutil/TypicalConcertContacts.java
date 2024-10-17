package seedu.address.testutil;

import static seedu.address.testutil.TypicalConcerts.GLASTONBURY;
import static seedu.address.testutil.TypicalPersons.BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.concert.ConcertContact;

/**
 * A utility class containing a list of {@code ConcertContact} objects to be used in tests.
 */
public class TypicalConcertContacts {
    public static final ConcertContact ALICE_COACHELLA = new ConcertContactBuilder().build();
    public static final ConcertContact ALICE_GLASTONBURY = new ConcertContactBuilder(
            ALICE_COACHELLA).withConcert(GLASTONBURY).build();
    public static final ConcertContact BOB_COACHELLA = new ConcertContactBuilder(ALICE_COACHELLA)
            .withPerson(BOB).build();

    private TypicalConcertContacts() {}

    public static List<ConcertContact> getTypicalConcertContacts() {
        return new ArrayList<>(Arrays.asList(ALICE_COACHELLA, ALICE_GLASTONBURY, BOB_COACHELLA));
    }
}
