package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AddIngredientCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.AddIngredientCommand;

/**
 * Contains tests for {@code AddIngredientCommandParser}.
 */
public class AddIngredientCommandParserTest {
    private final AddIngredientCommandParser parser = new AddIngredientCommandParser();

    private static final String VALID_NAME = "Flour";
    private static final double VALID_COST = 1.50;
    private static final String INVALID_COST = "abc"; // Non-numeric cost
    private static final String MISSING_COST = "Flour"; // Only name provided

    @Test
    public void parse_validArgs_returnsAddIngredientCommand() {
        // Prepare expected AddIngredientCommand
        AddIngredientCommand expectedCommand = new AddIngredientCommand(VALID_NAME, VALID_COST);

        // Verify that valid input produces the expected command
        assertParseSuccess(parser, VALID_NAME + " " + VALID_COST, expectedCommand);
    }

    @Test
    public void parse_invalidCost_throwsParseException() {
        // Verify that an invalid (non-numeric) cost throws a ParseException
        assertParseFailure(parser, VALID_NAME + " " + INVALID_COST, "The cost must be a valid number.");
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        // Verify that missing arguments (only name provided) throws a ParseException
        assertParseFailure(parser, MISSING_COST,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArgs_throwsParseException() {
        // Verify that extra arguments throw a ParseException
        assertParseFailure(parser, VALID_NAME + " " + VALID_COST + " extra",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        // Verify that empty input throws a ParseException
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
