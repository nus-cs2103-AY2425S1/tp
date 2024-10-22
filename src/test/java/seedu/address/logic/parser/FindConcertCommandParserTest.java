package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static seedu.address.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindConcertCommand;
import seedu.address.model.commons.Name;

public class FindConcertCommandParserTest {

    private final FindConcertCommandParser parser = new FindConcertCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser,
                "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindConcertCommand.MESSAGE_USAGE));

        assertParseFailure(parser,
                "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindConcertCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyPrefixes_throwsParseException() {
        assertParseFailure(parser,
                "user input",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindConcertCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        assertParseFailure(parser,
                generateUserInput(PREFIX_NAME + "foo " + PREFIX_NAME + "bar"),
                MESSAGE_DUPLICATE_FIELDS + PREFIX_NAME);
    }

    @Test
    public void parse_inputWithPreamble_throwsParseException() {
        assertParseFailure(parser,
                generateUserInput("preamble " + PREFIX_NAME + " \n Alice \n \t Bob  \t"),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindConcertCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidNameArgs_throwsParseException() {
        // empty name field
        assertParseFailure(parser,
                generateUserInput(PREFIX_NAME + ""),
                String.format(Name.MESSAGE_CONSTRAINTS));

        // non-alphanumeric characters in name field
        assertParseFailure(parser,
                generateUserInput(PREFIX_NAME + "foo*"),
                String.format(Name.MESSAGE_CONSTRAINTS));
    }

    @Test
    public void parse_validNameArgs_returnsFindConcertCommand() {
        // no leading and trailing whitespaces
        String userInput1 = generateUserInput(PREFIX_NAME + "Alice Bob");
        assertDoesNotThrow(() -> parser.parse(userInput1), "Parse exception thrown");

        // multiple whitespaces between keywords
        String userInput2 = generateUserInput(PREFIX_NAME + " \n Alice \n \t Bob  \t");
        assertDoesNotThrow(() -> parser.parse(userInput2), "Parse exception thrown");
    }

    /**
     * Prepends FindConcertCommand command word before userInput.
     *
     * @param input String without FindConcertCommand command word
     * @return String representing the user input for FindConcertCommand
     */
    private String generateUserInput(String input) {
        return FindConcertCommand.COMMAND_WORD + " " + input;
    }
}

