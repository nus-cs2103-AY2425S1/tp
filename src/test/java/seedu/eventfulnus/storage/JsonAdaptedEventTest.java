package seedu.eventfulnus.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.eventfulnus.testutil.Assert.assertThrows;
import static seedu.eventfulnus.testutil.TypicalEvents.SQUASH_FASS_YNC;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.eventfulnus.commons.exceptions.IllegalValueException;
import seedu.eventfulnus.model.event.Event;
import seedu.eventfulnus.model.event.Venue;
import seedu.eventfulnus.model.person.role.Sport;
import seedu.eventfulnus.model.person.role.SportString;


public class JsonAdaptedEventTest {
    private static final String INVALID_EVENT_SPORT = "  ";
    private static final String INVALID_EVENT_TEAM = " ARTS ";
    private static final String INVALID_EVENT_VENUE = " ";
    private static final String INVALID_EVENT_DATE_TIME = " ";
    private static final String INVALID_EVENT_PARTICIPANT = "#%$#^#";

    private static final String VALID_EVENT_SPORT = SportString.getSportString(SQUASH_FASS_YNC.getSport());
    private static final String VALID_EVENT_TEAM1 = SQUASH_FASS_YNC.getFirstTeam().toString();
    private static final String VALID_EVENT_TEAM2 = SQUASH_FASS_YNC.getSecondTeam().toString();
    private static final String VALID_EVENT_VENUE = SQUASH_FASS_YNC.getVenue().toString();
    private static final String VALID_EVENT_DATE_TIME = SQUASH_FASS_YNC.getDateTimeParseString();
    private static final List<JsonAdaptedPerson> VALID_EVENT_PARTICIPANTS = SQUASH_FASS_YNC.getParticipants().stream()
            .map(JsonAdaptedPerson::new)
            .toList();


    @Test
    public void toModelType_validEventDetails_returnsPerson() throws Exception {
        JsonAdaptedEvent event = new JsonAdaptedEvent(SQUASH_FASS_YNC);
        assertEquals(SQUASH_FASS_YNC, event.toModelType());
    }

    @Test
    public void toModelType_invalidEventSport_throwsIllegalValueException() {
        List<String> teams = new ArrayList<>();
        teams.add(VALID_EVENT_TEAM1);
        teams.add(VALID_EVENT_TEAM2);

        JsonAdaptedEvent event = new JsonAdaptedEvent(INVALID_EVENT_SPORT, teams, VALID_EVENT_VENUE,
                VALID_EVENT_DATE_TIME, VALID_EVENT_PARTICIPANTS);
        String expectedMessage = "Invalid sport: " + INVALID_EVENT_SPORT;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEventSport_throwsIllegalValueException() {
        List<String> teams = new ArrayList<>();
        teams.add(VALID_EVENT_TEAM1);
        teams.add(VALID_EVENT_TEAM2);

        JsonAdaptedEvent event = new JsonAdaptedEvent((String) null, teams, VALID_EVENT_VENUE,
                VALID_EVENT_DATE_TIME, VALID_EVENT_PARTICIPANTS);
        String expectedMessage = String.format(JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT,
                Sport.class.getSimpleName());
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEventTeam_throwsIllegalValueException() {
        List<String> teams = new ArrayList<>();
        teams.add(VALID_EVENT_TEAM1);
        teams.add(INVALID_EVENT_TEAM);

        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_EVENT_SPORT, teams, VALID_EVENT_VENUE,
                VALID_EVENT_DATE_TIME, VALID_EVENT_PARTICIPANTS);
        String expectedMessage = "Invalid faculty: " + INVALID_EVENT_TEAM;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEventTeam_throwsIllegalValueException() {
        List<String> teams = null;

        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_EVENT_SPORT, teams, VALID_EVENT_VENUE,
                VALID_EVENT_DATE_TIME, VALID_EVENT_PARTICIPANTS);
        String expectedMessage = "Exactly 2 teams must be provided.";
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEventVenue_throwsIllegalValueException() {
        List<String> teams = new ArrayList<>();
        teams.add(VALID_EVENT_TEAM1);
        teams.add(VALID_EVENT_TEAM2);

        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_EVENT_SPORT, teams, INVALID_EVENT_VENUE,
                VALID_EVENT_DATE_TIME, VALID_EVENT_PARTICIPANTS);
        String expectedMessage = "Invalid venue name: " + INVALID_EVENT_VENUE + Venue.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEventVenue_throwsIllegalValueException() {
        List<String> teams = new ArrayList<>();
        teams.add(VALID_EVENT_TEAM1);
        teams.add(VALID_EVENT_TEAM2);

        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_EVENT_SPORT, teams, null,
                VALID_EVENT_DATE_TIME, VALID_EVENT_PARTICIPANTS);
        String expectedMessage = String.format(JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT,
                "Venue");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_invalidEventDateTime_throwsIllegalValueException() {
        List<String> teams = new ArrayList<>();
        teams.add(VALID_EVENT_TEAM1);
        teams.add(VALID_EVENT_TEAM2);

        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_EVENT_SPORT, teams, VALID_EVENT_VENUE,
                INVALID_EVENT_DATE_TIME, VALID_EVENT_PARTICIPANTS);
        String expectedMessage = "Invalid date and time: " + INVALID_EVENT_DATE_TIME
                                 + Event.MESSAGE_CONSTRAINTS;
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }

    @Test
    public void toModelType_nullEventDateTime_throwsIllegalValueException() {
        List<String> teams = new ArrayList<>();
        teams.add(VALID_EVENT_TEAM1);
        teams.add(VALID_EVENT_TEAM2);

        JsonAdaptedEvent event = new JsonAdaptedEvent(VALID_EVENT_SPORT, teams, VALID_EVENT_VENUE,
                null, VALID_EVENT_PARTICIPANTS);
        String expectedMessage = String.format(JsonAdaptedEvent.MISSING_FIELD_MESSAGE_FORMAT,
                "LocalDateTime");
        assertThrows(IllegalValueException.class, expectedMessage, event::toModelType);
    }
}
