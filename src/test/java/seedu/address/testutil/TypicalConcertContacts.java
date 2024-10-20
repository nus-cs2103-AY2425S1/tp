package seedu.address.testutil;

import static seedu.address.testutil.TypicalConcerts.COACHELLA;
import static seedu.address.testutil.TypicalConcerts.GLASTONBURY;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.concert.ConcertContact;

/**
 * A utility class containing a list of {@code ConcertContact} objects to be used in tests.
 */
public class TypicalConcertContacts {
    public static final ConcertContact ALICE_COACHELLA = new ConcertContactBuilder(
            new ConcertContact(ALICE, COACHELLA)).build();
    public static final ConcertContact ALICE_GLASTONBURY = new ConcertContactBuilder(
            new ConcertContact(ALICE, GLASTONBURY)).build();
    public static final ConcertContact BENSON_COACHELLA = new ConcertContactBuilder(new ConcertContact(
            BENSON, COACHELLA)).build();

    private TypicalConcertContacts() {}

    public static List<ConcertContact> getTypicalConcertContacts() {
        return new ArrayList<>(Arrays.asList(ALICE_COACHELLA, ALICE_GLASTONBURY, BENSON_COACHELLA));
    }
}
