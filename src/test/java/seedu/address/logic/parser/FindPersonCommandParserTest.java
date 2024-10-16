package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindPersonCommand;

public class FindPersonCommandParserTest {

    private final FindPersonCommandParser parser = new FindPersonCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser,
                "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPrefixes_throwsParseException() {
        assertParseFailure(parser,
                "user input",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArgs_returnsFindPersonCommand() {
        // no leading and trailing whitespaces
        String userInput1 = generateUserInput(PREFIX_NAME + "Alice Bob");
        assertDoesNotThrow(() -> parser.parse(userInput1), "Parse exception thrown");

        // multiple whitespaces between keywords
        String userInput2 = generateUserInput(PREFIX_NAME + " \n Alice \n \t Bob  \t");
        assertDoesNotThrow(() -> parser.parse(userInput2), "Parse exception thrown");
    }

    @Test
    public void parse_validRoleArg_returnsFindPersonCommand() {
        // no leading and trailing whitespaces
        String userInput1 = generateUserInput(PREFIX_ROLE + "organiser");
        assertDoesNotThrow(() -> parser.parse(userInput1), "Parse exception thrown");

        // leading and trailing whitespaces
        String userInput2 = generateUserInput(PREFIX_ROLE + "  organiser    ");
        assertDoesNotThrow(() -> parser.parse(userInput2), "Parse exception thrown");
    }

    @Test
    public void parse_validNameAndRoleArgs_returnsFindPersonCommand() {
        String userInput = generateUserInput(PREFIX_NAME + "Alice Bob " + PREFIX_ROLE + "organiser");
        assertDoesNotThrow(() -> parser.parse(userInput), "Parse exception thrown");
    }

    /**
     * Prepends FindPersonCommand command word before userInput.
     *
     * @param input String without FindPersonCommand command word
     * @return String representing the user input for FindPersonCommand
     */
    private String generateUserInput(String input) {
        return FindPersonCommand.COMMAND_WORD + " " + input;
    }
}
