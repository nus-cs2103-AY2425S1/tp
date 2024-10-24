package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CreateEventCommand;
import seedu.address.model.commons.name.Name;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;

public class CreateEventCommandParserTest {

    private CreateEventCommandParser parser = new CreateEventCommandParser();
    private Event excursionEvent = new Event(new Name("Zoo Excursion"), new Date("2024-10-10"));

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = excursionEvent;

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + " " + PREFIX_EVENT + " "
                        + PREFIX_NAME + "Zoo Excursion" + " " + PREFIX_DATE + "2024-10-10",
                new CreateEventCommand(expectedEvent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateEventCommand.MESSAGE_USAGE);

        // missing event prefix
        assertParseFailure(parser, " " + PREFIX_EVENT + " Zoo Excursion" + " " + PREFIX_DATE + "2024-10-10",
                expectedMessage);

        // missing name prefix
        assertParseFailure(parser, " " + PREFIX_EVENT + " Zoo Excursion" + " " + PREFIX_DATE + "2024-10-10",
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, " " + PREFIX_EVENT
                        + " " + PREFIX_NAME + "Zoo Excursion" + " " + "2024-10-10",
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, " " + PREFIX_EVENT + "Zoo Excursion" + " " + "2024-10-10",
                expectedMessage);
    }

    @Test
    public void parse_ignoresEventPrefixValue_success() {

        Event expectedEvent = excursionEvent;

        // event prefix contains parameter value
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " " + PREFIX_EVENT + "some parameter" + " "
                        + PREFIX_NAME + "Zoo Excursion" + " " + PREFIX_DATE + "2024-10-10",
                new CreateEventCommand(expectedEvent));

        // event prefix contains no parameter value
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " " + PREFIX_EVENT + " " + PREFIX_NAME
                        + "Zoo Excursion" + " " + PREFIX_DATE + "2024-10-10",
                new CreateEventCommand(expectedEvent));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, " " + PREFIX_EVENT + " " + PREFIX_NAME + "Zoo Excursion?" + " "
                        + PREFIX_DATE + "2024-10-10",
                Name.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, " " + PREFIX_EVENT + " " + PREFIX_NAME + "Zoo Excursion" + " "
                        + PREFIX_DATE + "10 October 2024",
                Date.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, " " + PREFIX_EVENT + " " + PREFIX_NAME + "Zoo Excursion?" + " "
                        + PREFIX_DATE + "10 October 2024",
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + " " + PREFIX_EVENT + PREFIX_NAME + "Zoo Excursion" + " " + PREFIX_DATE
                        + "10 October 2024",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateEventCommand.MESSAGE_USAGE));
    }
}
