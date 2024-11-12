package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.ALICE;

import java.util.Optional;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.DeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "delete n/Alice Pauline p/94351253 e/alice@example.com",
                new DeleteCommand(Optional.of(ALICE.getName().toString()),
                        Optional.of(ALICE.getPhone().toString()), Optional.of(ALICE.getEmail().toString())));
        assertParseSuccess(parser, "delete n/Alice Pauline",
                new DeleteCommand(Optional.of(ALICE.getName().toString()), Optional.empty(), Optional.empty()));
    }

    @Test
    public void parse_invalidEmail_throwsParseException() {
        // Invalid email input
        String userInput = "delete n/Alice Pauline p/12345678 e/ @gmail.com";

        // Expected error message
        String expectedMessage = "ERROR: Invalid email format. Please provide a valid email address.";

        assertParseFailure(parser, userInput, expectedMessage);

    }

    @Test
    public void parse_invalidName_throwsParseException() {
        // Example input with an invalid name (assuming non-alphanumeric names are invalid)
        String userInput = "delete n/67";

        String expectedMessage = "ERROR: Invalid name format. Enter a valid name.";

        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidPhone_throwsParseException() {
        // Simulate an input with an invalid phone number (e.g., less than 8 digits)
        String userInput = "delete n/Alice Pauline p/1234"; // Invalid phone number

        // Expected error message when the phone number is invalid
        String expectedMessage = "ERROR: Invalid phone number format. Enter a valid 8 digit phone number.";

        // Call the parser and expect a ParseException with the specific message
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessage =
                String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_NO_ARGUMENTS_FOUND);
        assertParseFailure(parser, "a", expectedMessage);
    }

    @Test
    public void parse_invalidPrefix_failure() {
        assertParseFailure(parser, "delete n/Alice Pauline p/94351253 e/alice@example.com m/None",
              String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
