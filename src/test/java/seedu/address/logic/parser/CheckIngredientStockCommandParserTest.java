package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.commands.CheckIngredientStockCommand.MESSAGE_USAGE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CheckIngredientStockCommand;
/**
 * Contains tests for {@code CheckIngredientStockCommandParser}.
 */
public class CheckIngredientStockCommandParserTest {

    private final CheckIngredientStockCommandParser parser = new CheckIngredientStockCommandParser();

    private static final String VALID_INGREDIENT_NAME = "Flour";
    private static final String WHITESPACE_ONLY = "   ";
    private static final String EMPTY_INPUT = "";
    private static final String SPECIAL_CHARACTERS_INPUT = "@!$%^";

    @Test
    public void parse_validArgs_returnsCheckIngredientStockCommand() {
        // Prepare the expected command
        CheckIngredientStockCommand expectedCommand = new CheckIngredientStockCommand(VALID_INGREDIENT_NAME);

        // Verify that valid input produces the expected command
        assertParseSuccess(parser, VALID_INGREDIENT_NAME, expectedCommand);
    }

    @Test
    public void parse_whitespaceOnly_throwsParseException() {
        // Verify that input with only whitespace throws a ParseException
        assertParseFailure(parser, WHITESPACE_ONLY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyInput_throwsParseException() {
        // Verify that empty input throws a ParseException
        assertParseFailure(parser, EMPTY_INPUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_specialCharacters_throwsParseException() {
        // Verify that input with special characters throws a ParseException
        assertParseFailure(parser, SPECIAL_CHARACTERS_INPUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_extraSpaces_validCommand() {
        // Prepare the expected command
        CheckIngredientStockCommand expectedCommand = new CheckIngredientStockCommand(VALID_INGREDIENT_NAME);

        // Verify that valid input with extra spaces produces the correct command
        assertParseSuccess(parser, "   " + VALID_INGREDIENT_NAME + "   ", expectedCommand);
    }
}

