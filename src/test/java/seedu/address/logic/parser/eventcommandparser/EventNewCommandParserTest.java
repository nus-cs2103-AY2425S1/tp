package seedu.address.logic.parser.eventcommandparser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_BEACH_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESCRIPTION_DESC_BEACH_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_END_TIME_DESC_BEACH_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_LOCATION_DESC_BEACH_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_DESC_BEACH_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_START_TIME_DESC_BEACH_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_END_TIME_BEFORE_START_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_END_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_START_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_BEACH_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_BEACH_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_END_TIME_BEACH_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_LOCATION_BEACH_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_BEACH_CLEANUP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_START_TIME_BEACH_CLEANUP;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.EVENT_PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.eventcommands.EventNewCommand;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.event.Location;
import seedu.address.model.event.Time;
import seedu.address.model.event.exceptions.ChronologicalOrderException;
import seedu.address.testutil.EventBuilder;

public class EventNewCommandParserTest {
    private EventNewCommandParser parser = new EventNewCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder()
                .withEventName(VALID_EVENT_NAME_BEACH_CLEANUP)
                .withLocation(VALID_EVENT_LOCATION_BEACH_CLEANUP)
                .withDate(VALID_EVENT_DATE_BEACH_CLEANUP)
                .withStartTime(VALID_EVENT_START_TIME_BEACH_CLEANUP)
                .withEndTime(VALID_EVENT_END_TIME_BEACH_CLEANUP)
                .withDescription(VALID_EVENT_DESCRIPTION_BEACH_CLEANUP)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + EVENT_NAME_DESC_BEACH_CLEANUP
                        + EVENT_LOCATION_DESC_BEACH_CLEANUP + EVENT_DATE_DESC_BEACH_CLEANUP
                        + EVENT_START_TIME_DESC_BEACH_CLEANUP + EVENT_END_TIME_DESC_BEACH_CLEANUP
                        + EVENT_DESCRIPTION_DESC_BEACH_CLEANUP,
                new EventNewCommand(expectedEvent));
    }

    @Test
    public void parse_duplicateFields_failure() {
        String validCommand = EVENT_NAME_DESC_BEACH_CLEANUP + EVENT_LOCATION_DESC_BEACH_CLEANUP
                + EVENT_DATE_DESC_BEACH_CLEANUP + EVENT_START_TIME_DESC_BEACH_CLEANUP
                + EVENT_END_TIME_DESC_BEACH_CLEANUP;

        // duplicate name prefix - rejected
        assertParseFailure(parser, EVENT_NAME_DESC_BEACH_CLEANUP + EVENT_NAME_DESC_BEACH_git CLEANUP
                        + EVENT_LOCATION_DESC_BEACH_CLEANUP + EVENT_DATE_DESC_BEACH_CLEANUP
                        + EVENT_START_TIME_DESC_BEACH_CLEANUP + EVENT_END_TIME_DESC_BEACH_CLEANUP,
                Messages.getErrorMessageForDuplicatePrefixes(EVENT_PREFIX_NAME));

        // duplicate location prefix - rejected
        assertParseFailure(parser, EVENT_NAME_DESC_BEACH_CLEANUP + EVENT_LOCATION_DESC_BEACH_CLEANUP
                        + EVENT_LOCATION_DESC_BEACH_CLEANUP + EVENT_DATE_DESC_BEACH_CLEANUP
                        + EVENT_START_TIME_DESC_BEACH_CLEANUP + EVENT_END_TIME_DESC_BEACH_CLEANUP,
                Messages.getErrorMessageForDuplicatePrefixes(EVENT_PREFIX_LOCATION));

        // duplicate date prefix - rejected
        assertParseFailure(parser, EVENT_NAME_DESC_BEACH_CLEANUP + EVENT_LOCATION_DESC_BEACH_CLEANUP
                        + EVENT_DATE_DESC_BEACH_CLEANUP + EVENT_DATE_DESC_BEACH_CLEANUP
                        + EVENT_START_TIME_DESC_BEACH_CLEANUP + EVENT_END_TIME_DESC_BEACH_CLEANUP,
                Messages.getErrorMessageForDuplicatePrefixes(EVENT_PREFIX_DATE));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventNewCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_EVENT_NAME_BEACH_CLEANUP + EVENT_LOCATION_DESC_BEACH_CLEANUP
                + EVENT_DATE_DESC_BEACH_CLEANUP + EVENT_START_TIME_DESC_BEACH_CLEANUP
                + EVENT_END_TIME_DESC_BEACH_CLEANUP, expectedMessage);

        // missing location prefix
        assertParseFailure(parser, EVENT_NAME_DESC_BEACH_CLEANUP + VALID_EVENT_LOCATION_BEACH_CLEANUP
                + EVENT_DATE_DESC_BEACH_CLEANUP + EVENT_START_TIME_DESC_BEACH_CLEANUP
                + EVENT_END_TIME_DESC_BEACH_CLEANUP, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, EVENT_NAME_DESC_BEACH_CLEANUP + EVENT_LOCATION_DESC_BEACH_CLEANUP
                + VALID_EVENT_DATE_BEACH_CLEANUP + EVENT_START_TIME_DESC_BEACH_CLEANUP
                + EVENT_END_TIME_DESC_BEACH_CLEANUP, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_EVENT_NAME_BEACH_CLEANUP + VALID_EVENT_LOCATION_BEACH_CLEANUP
                + VALID_EVENT_DATE_BEACH_CLEANUP + VALID_EVENT_START_TIME_BEACH_CLEANUP
                + VALID_EVENT_END_TIME_BEACH_CLEANUP, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + EVENT_LOCATION_DESC_BEACH_CLEANUP
                + EVENT_DATE_DESC_BEACH_CLEANUP + EVENT_START_TIME_DESC_BEACH_CLEANUP
                + EVENT_END_TIME_DESC_BEACH_CLEANUP, EventName.MESSAGE_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser, EVENT_NAME_DESC_BEACH_CLEANUP + INVALID_EVENT_LOCATION_DESC
                + EVENT_DATE_DESC_BEACH_CLEANUP + EVENT_START_TIME_DESC_BEACH_CLEANUP
                + EVENT_END_TIME_DESC_BEACH_CLEANUP, Location.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, EVENT_NAME_DESC_BEACH_CLEANUP + EVENT_LOCATION_DESC_BEACH_CLEANUP
                + INVALID_EVENT_DATE_DESC + EVENT_START_TIME_DESC_BEACH_CLEANUP
                + EVENT_END_TIME_DESC_BEACH_CLEANUP, Date.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + EVENT_NAME_DESC_BEACH_CLEANUP
                        + EVENT_LOCATION_DESC_BEACH_CLEANUP + EVENT_DATE_DESC_BEACH_CLEANUP
                        + EVENT_START_TIME_DESC_BEACH_CLEANUP + EVENT_END_TIME_DESC_BEACH_CLEANUP,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, EventNewCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_startTimeAfterEndTime_failure() {
        // start time is after end time
        String args = EVENT_NAME_DESC_BEACH_CLEANUP + EVENT_LOCATION_DESC_BEACH_CLEANUP
                + EVENT_DATE_DESC_BEACH_CLEANUP + EVENT_START_TIME_DESC_BEACH_CLEANUP
                + INVALID_EVENT_END_TIME_BEFORE_START_DESC;

        assertParseFailure(parser, args, new ChronologicalOrderException().getMessage());
    }

    @Test
    public void parse_invalidStartTimeFormat_failure() {
        // invalid start time format
        String input = EVENT_NAME_DESC_BEACH_CLEANUP + EVENT_LOCATION_DESC_BEACH_CLEANUP
                + EVENT_DATE_DESC_BEACH_CLEANUP + INVALID_EVENT_START_TIME_DESC // Invalid start time
                + EVENT_END_TIME_DESC_BEACH_CLEANUP;

        assertParseFailure(parser, input, "Start " + Time.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidEndTimeFormat_failure() {
        // invalid end time format
        String input = EVENT_NAME_DESC_BEACH_CLEANUP + EVENT_LOCATION_DESC_BEACH_CLEANUP
                + EVENT_DATE_DESC_BEACH_CLEANUP + EVENT_START_TIME_DESC_BEACH_CLEANUP
                + INVALID_EVENT_END_TIME_DESC; // Invalid end time

        assertParseFailure(parser, input, "End " + Time.MESSAGE_CONSTRAINTS);
    }
}
