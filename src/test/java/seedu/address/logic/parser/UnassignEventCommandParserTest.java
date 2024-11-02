package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnassignEventByPersonIndexEventIndexCommand;
import seedu.address.logic.commands.UnassignEventByPersonIndexEventNameCommand;
import seedu.address.logic.commands.UnassignEventByPersonNameEventIndexCommand;
import seedu.address.logic.commands.UnassignEventByPersonNameEventNameCommand;
import seedu.address.logic.commands.UnassignEventCommand;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Name;

public class UnassignEventCommandParserTest {

    private final UnassignEventCommandParser parser = new UnassignEventCommandParser();

    @Test
    public void parse_validPersonIndexEventIndex_success() {
        String userInput = " " + PREFIX_PERSON + "1 " + PREFIX_EVENT + "2";
        UnassignEventByPersonIndexEventIndexCommand expectedCommand =
                new UnassignEventByPersonIndexEventIndexCommand(Index.fromOneBased(1), Index.fromOneBased(2));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validPersonNameEventName_success() {
        String userInput = " " + PREFIX_PERSON + VALID_NAME_AMY + " " + PREFIX_EVENT + VALID_EVENT_NAME_MEETING;
        UnassignEventByPersonNameEventNameCommand expectedCommand =
                new UnassignEventByPersonNameEventNameCommand(
                    new Name(VALID_NAME_AMY), new EventName(VALID_EVENT_NAME_MEETING));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validPersonNameEventIndex_success() {
        String userInput = " " + PREFIX_PERSON + VALID_NAME_AMY + " " + PREFIX_EVENT + "2";
        UnassignEventByPersonNameEventIndexCommand expectedCommand =
                new UnassignEventByPersonNameEventIndexCommand(new Name(VALID_NAME_AMY), Index.fromOneBased(2));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validPersonIndexEventName_success() {
        String userInput = " " + PREFIX_PERSON + "1 " + PREFIX_EVENT + VALID_EVENT_NAME_MEETING;
        UnassignEventByPersonIndexEventNameCommand expectedCommand =
                new UnassignEventByPersonIndexEventNameCommand(
                    Index.fromOneBased(1), new EventName(VALID_EVENT_NAME_MEETING));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignEventCommand.MESSAGE_USAGE);

        // zero index for Person
        String userInput = " " + PREFIX_PERSON + "0 " + PREFIX_EVENT + "2";
        assertParseFailure(parser, userInput, expectedMessage);

        // zero index for Event
        userInput = " " + PREFIX_PERSON + "1 " + PREFIX_EVENT + "0";
        assertParseFailure(parser, userInput, expectedMessage);

        // negative index for Person
        userInput = " " + PREFIX_PERSON + "-1 " + PREFIX_EVENT + "2";
        assertParseFailure(parser, userInput, expectedMessage);

        // negative index for Event
        userInput = " " + PREFIX_PERSON + "1 " + PREFIX_EVENT + "-2";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidPersonName_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignEventCommand.MESSAGE_USAGE);

        // Invalid name for Person
        String userInput = " " + PREFIX_PERSON + "Invalid&Name " + PREFIX_EVENT + VALID_EVENT_NAME_MEETING;
        assertParseFailure(parser, userInput, expectedMessage);

        // Invalid name for Event
        userInput = " " + PREFIX_PERSON + VALID_NAME_AMY + " " + PREFIX_EVENT + "Invalid&Event";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingArgs_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnassignEventCommand.MESSAGE_USAGE);

        // Missing person prefix
        String userInput = " 1 " + PREFIX_EVENT + "2";
        assertParseFailure(parser, userInput, expectedMessage);

        // Missing event prefix
        userInput = " " + PREFIX_PERSON + "1 2";
        assertParseFailure(parser, userInput, expectedMessage);

        // Missing both prefixes
        userInput = " 1 2";
        assertParseFailure(parser, userInput, expectedMessage);

        // Empty input
        userInput = " ";
        assertParseFailure(parser, userInput, expectedMessage);
    }
}
