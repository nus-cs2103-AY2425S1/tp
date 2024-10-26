package seedu.hireme.logic.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;

import seedu.hireme.logic.commands.Command;
import seedu.hireme.logic.parser.exceptions.ParseException;

/**
 * Contains helper methods for testing command parsers.
 */
public class CommandParserTestUtil {

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is successful and the command created
     * equals to {@code expectedCommand}.
     */
    public static void assertParseSuccess(Parser<? extends Command> parser, String userInput,
                                          Command expectedCommand) {
        try {
            Command command = parser.parse(userInput);
            assertEquals(expectedCommand, command);
        } catch (ParseException pe) {
            throw new IllegalArgumentException("Invalid userInput.", pe);
        }
    }

    /**
     * Asserts that the parsing of {@code userInput} by {@code parser} is unsuccessful and the error message
     * equals to {@code expectedMessage}.
     */
    public static void assertParseFailure(Parser<? extends Command> parser, String userInput,
                                          String expectedMessage) {
        try {
            parser.parse(userInput);
            throw new AssertionError("The expected ParseException was not thrown.");
        } catch (ParseException pe) {
            // Log both expected and actual messages for debugging purposes
            System.out.println("Expected: [" + expectedMessage + "]");
            System.out.println("Actual:   [" + pe.getMessage().trim() + "]");
            assertEquals(expectedMessage.trim(), pe.getMessage().trim());
        }
    }
}
