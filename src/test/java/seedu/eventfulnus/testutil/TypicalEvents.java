package seedu.eventfulnus.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.eventfulnus.model.event.Event;

/**
 * A utility class containing a list of {@link Event} objects to be used in tests.
 */
public class TypicalEvents {
    // in typicaladdressbook.json
    public static final Event CHESS_COM_NUSC = new EventBuilder()
            .withSport("Chess")
            .withTeams("COM", "NUSC")
            .withVenue("UT AUD2")
            .withDateTime("2024 09 20 1000")
            .withParticipants("Alice Pauline", "Elle Meyer")
            .build();
    public static final Event SQUASH_FASS_YNC = new EventBuilder().withSport("Squash")
            .withTeams("FASS", "YNC")
            .withVenue("Arena")
            .withDateTime("2024 09 20 1400")
            .withParticipants("Carl Kurz")
            .build();
    public static final Event SWIMMING_M_MED_DEN = new EventBuilder().withSport("Swimming Men")
            .withTeams("MED", "DEN")
            .withVenue("Pool")
            .withDateTime("2024 09 20 1600")
            .withParticipants("Benson Meier", "Fiona Kunz")
            .build();
    public static final Event TABLE_TENNIS_LAW_BIZ = new EventBuilder().withSport("Table Tennis")
            .withTeams("LAW", "BIZ")
            .withVenue("Court")
            .withDateTime("2024 09 21 1000")
            .withParticipants("Daniel Meier", "George Best")
            .build();

    // Manually added
    public static final Event TRACK_W_SCI_CDE = new EventBuilder().withSport("Track Women")
            .withTeams("SCI", "CDE")
            .withVenue("Track")
            .withDateTime("2024 09 21 1900")
            .withParticipants()
            .build();
    public static final Event VALORANT_COM_FASS = new EventBuilder().withSport("VALORANT")
            .withTeams("COM", "FASS")
            .withVenue("COM3")
            .withDateTime("2024 09 22 1000")
            .withParticipants()
            .build();

    private TypicalEvents() {} // prevents instantiation

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(
                Arrays.asList(CHESS_COM_NUSC, SQUASH_FASS_YNC, SWIMMING_M_MED_DEN, TABLE_TENNIS_LAW_BIZ));
    }
}
