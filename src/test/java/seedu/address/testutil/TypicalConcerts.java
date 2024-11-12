package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_ADELE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_COACHELLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_GLASTONBURY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_TOMORROWLAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_ADELE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_COACHELLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_GLASTONBURY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_TOMORROWLAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_ADELE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_COACHELLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_GLASTONBURY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_TOMORROWLAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.concert.Concert;

/**
 * A utility class containing a list of {@code Concert} objects to be used in tests.
 */
public class TypicalConcerts {
    public static final Concert COACHELLA = new ConcertBuilder().withName(VALID_NAME_COACHELLA)
            .withAddress(VALID_ADDRESS_COACHELLA).withDate(VALID_DATE_COACHELLA).build();
    public static final Concert GLASTONBURY = new ConcertBuilder().withName(VALID_NAME_GLASTONBURY)
            .withAddress(VALID_ADDRESS_GLASTONBURY).withDate(VALID_DATE_GLASTONBURY).build();
    public static final Concert TOMORROWLAND = new ConcertBuilder().withName(VALID_NAME_TOMORROWLAND)
            .withAddress(VALID_ADDRESS_TOMORROWLAND).withDate(VALID_DATE_TOMORROWLAND).build();
    public static final Concert ADELE = new ConcertBuilder().withName(VALID_NAME_ADELE)
            .withAddress(VALID_ADDRESS_ADELE).withDate(VALID_DATE_ADELE).build();

    private TypicalConcerts() {} // prevents instantiation

    public static List<Concert> getTypicalConcerts() {
        return new ArrayList<>(Arrays.asList(COACHELLA, GLASTONBURY, TOMORROWLAND, ADELE));
    }
}
