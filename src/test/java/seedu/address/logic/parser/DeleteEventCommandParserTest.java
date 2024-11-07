package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteEventCommand;

public class DeleteEventCommandParserTest {

    private DeleteEventCommandParser parser = new DeleteEventCommandParser();

    @Test
    public void parse_validInputSingleEvent_success() {
        String userInput = " " + PREFIX_EVENT + "Event1";
        List<String> expectedEventNames = Arrays.asList("Event1");
        DeleteEventCommand expectedCommand = new DeleteEventCommand(expectedEventNames);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validInputMultipleEvents_success() {
        String userInput = " " + PREFIX_EVENT + "Event1 " + PREFIX_EVENT + "Event2 " + PREFIX_EVENT + "Event3";
        List<String> expectedEventNames = Arrays.asList("Event1", "Event2", "Event3");
        DeleteEventCommand expectedCommand = new DeleteEventCommand(expectedEventNames);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingEventPrefix_failure() {
        String userInput = " Event1";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteEventCommand.MESSAGE_USAGE);

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_duplicateEventNames_failure() {
        String userInput = " " + PREFIX_EVENT + "Event1 " + PREFIX_EVENT + "event1";
        String expectedMessage = "Duplicate event names detected.";

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_eventNameContainsSlash_failure() {
        String userInput = " " + PREFIX_EVENT + "Invalid/Event";
        String expectedMessage = "Event name cannot contain '/'.";

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_eventNameEmpty_failure() {
        String userInput = " " + PREFIX_EVENT;
        String expectedMessage = "Event name cannot be empty.";

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_eventNameOnlySpaces_failure() {
        String userInput = " " + PREFIX_EVENT + "   ";
        String expectedMessage = "Event name cannot be empty.";

        assertParseFailure(parser, userInput, expectedMessage);
    }
}
