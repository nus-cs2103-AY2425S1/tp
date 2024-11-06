package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.DURATION_DESC_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_FORMAT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DURATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESCRIPTION_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_FROM_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_WORKSHOP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TO_DATE_1;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEvents.MEETING;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventDuration;
import seedu.address.model.event.EventName;
import seedu.address.testutil.EventBuilder;

public class AddEventCommandParserTest {

    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder(MEETING).build();

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + NAME_DESC_MEETING + DESCRIPTION_DESC_MEETING + DURATION_DESC_MEETING,
                new AddEventCommand(expectedEvent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_EVENT_NAME_WORKSHOP + DESCRIPTION_DESC_WORKSHOP + DURATION_DESC_WORKSHOP,
                expectedMessage);

        // missing description prefix
        assertParseFailure(parser, NAME_DESC_WORKSHOP + VALID_EVENT_DESCRIPTION_WORKSHOP + DURATION_DESC_WORKSHOP,
                expectedMessage);

        // missing duration prefix
        assertParseFailure(parser,
                NAME_DESC_MEETING + DESCRIPTION_DESC_MEETING + VALID_EVENT_FROM_DATE_1 + " to " + VALID_EVENT_TO_DATE_1,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_EVENT_NAME_MEETING + VALID_EVENT_DESCRIPTION_MEETING + VALID_EVENT_FROM_DATE_1
                + " to " + VALID_EVENT_TO_DATE_1, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + DESCRIPTION_DESC_WORKSHOP + DURATION_DESC_WORKSHOP,
                EventName.MESSAGE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, NAME_DESC_MEETING + INVALID_EVENT_DESCRIPTION_DESC + DURATION_DESC_MEETING,
                EventDescription.MESSAGE_CONSTRAINTS);

        // invalid date format
        assertParseFailure(parser, NAME_DESC_MEETING + DESCRIPTION_DESC_MEETING + INVALID_EVENT_DATE_FORMAT_DESC,
                EventDuration.MESSAGE_CONSTRAINTS_DATE_STRING);

        // invalid date
        assertParseFailure(parser, NAME_DESC_MEETING + DESCRIPTION_DESC_MEETING + INVALID_EVENT_DATE_DESC,
                EventDuration.MESSAGE_CONSTRAINTS_DATE_STRING);

        // invalid duration
        assertParseFailure(parser, NAME_DESC_MEETING + DESCRIPTION_DESC_MEETING + INVALID_EVENT_DURATION_DESC,
                EventDuration.MESSAGE_CONSTRAINTS_DATE_ORDER);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_EVENT_NAME_DESC + INVALID_EVENT_DESCRIPTION_DESC + DURATION_DESC_WORKSHOP,
                EventName.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + NAME_DESC_MEETING + DESCRIPTION_DESC_MEETING + DURATION_DESC_MEETING,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));
    }

}
