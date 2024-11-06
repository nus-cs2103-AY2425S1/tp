package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_EVENT_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_END_TIME_EVENT_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_EVENT_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_START_TIME_EVENT_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_DATE_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_EMAIL_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_NAME_ALICE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VOLUNTEER_PHONE_ALICE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.parser.Prefix;
import seedu.address.model.event.Event;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.VolunteerBuilder;

public class MessagesTest {

    @Test
    public void messageConstants_areCorrect() {
        assertEquals("Unknown command", Messages.MESSAGE_UNKNOWN_COMMAND);
        assertEquals("Invalid command format! \n%1$s", Messages.MESSAGE_INVALID_COMMAND_FORMAT);
        assertEquals("The volunteer index provided is invalid",
                Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
        assertEquals("%1$d volunteer listed!", Messages.MESSAGE_VOLUNTEERS_LISTED_OVERVIEW);
        assertEquals("Multiple values specified for the following single-valued field(s): ",
                Messages.MESSAGE_DUPLICATE_FIELDS);
        assertEquals("The event index provided is invalid", Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_singlePrefix_returnsCorrectMessage() {
        Prefix duplicatePrefix = new Prefix("n/");
        String expectedMessage = Messages.MESSAGE_DUPLICATE_FIELDS + "n/";
        assertEquals(expectedMessage, Messages.getErrorMessageForDuplicatePrefixes(duplicatePrefix));
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_multiplePrefixes_returnsCorrectMessage() {
        Prefix duplicatePrefix1 = new Prefix("n/");
        Prefix duplicatePrefix2 = new Prefix("p/");
        String expectedMessage = Messages.MESSAGE_DUPLICATE_FIELDS + "n/ p/";
        assertEquals(expectedMessage, Messages.getErrorMessageForDuplicatePrefixes(duplicatePrefix1, duplicatePrefix2));
    }

    @Test
    public void format_volunteer_returnsFormattedString() {
        Volunteer volunteer = new VolunteerBuilder().withName(VALID_VOLUNTEER_NAME_ALICE)
                .withPhone(VALID_VOLUNTEER_PHONE_ALICE)
                .withEmail(VALID_VOLUNTEER_EMAIL_ALICE).withAvailableDate(VALID_VOLUNTEER_DATE_ALICE).build();

        String expectedFormat = "Alice Wong; Phone: 91234567; Email: alicewong@example.com; Available Date: 2023-05-10";
        assertEquals(expectedFormat, Messages.format(volunteer));
    }

    @Test
    public void format_event_returnsFormattedString() {
        Event event = new EventBuilder().withEventName(VALID_EVENT_NAME_A).withLocation(VALID_LOCATION_EVENT_A)
                .withDate(VALID_DATE_EVENT_A).withStartTime(VALID_START_TIME_EVENT_A)
                .withEndTime(VALID_END_TIME_EVENT_A)
                .withDescription(VALID_DESCRIPTION_EVENT_A).build();

        String expectedFormat = "Annual Charity Run; Location: East Coast Park; Date: 2024-12-15; Start Time: 08:00; "
                + "End Time: 10:00; Description: Annual charity run for raising funds";
        assertEquals(expectedFormat, Messages.format(event));
    }
}
