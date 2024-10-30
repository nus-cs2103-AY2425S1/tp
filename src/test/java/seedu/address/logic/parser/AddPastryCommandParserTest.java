package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.commands.AddPastryCommand.MESSAGE_USAGE;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.AddPastryCommand;
import seedu.address.model.product.Ingredient;
import seedu.address.model.product.IngredientCatalogue;

/**
 * Contains tests for {@code AddPastryCommandParser}.
 */
public class AddPastryCommandParserTest {

    private final AddPastryCommandParser parser = new AddPastryCommandParser();

    private static final String VALID_NAME = "Croissant";
    private static final double VALID_COST = 3.50;
    private static final String INVALID_COST = "abc";
    private static final String VALID_INGREDIENTS = "Flour Sugar";
    private static final String INVALID_INGREDIENT = "GoldDust";

    @Test
    public void parse_validArgs_returnsAddPastryCommand() {
        // Prepare the expected AddPastryCommand
        IngredientCatalogue catalogue = new IngredientCatalogue();
        ArrayList<Ingredient> ingredients = new ArrayList<>(List.of(
                catalogue.getIngredientByName("Flour"),
                catalogue.getIngredientByName("Sugar")
        ));

        AddPastryCommand expectedCommand = new AddPastryCommand(VALID_NAME, VALID_COST, ingredients);

        // Verify the parser returns the expected command for valid input
        assertParseSuccess(parser, VALID_NAME + " " + VALID_COST + " " + VALID_INGREDIENTS, expectedCommand);
    }

    @Test
    public void parse_invalidCost_throwsParseException() {
        // Verify that a non-numeric cost throws a ParseException
        assertParseFailure(parser, VALID_NAME + " " + INVALID_COST + " " + VALID_INGREDIENTS,
                "The cost must be a valid number.");
    }

    @Test
    public void parse_invalidIngredient_throwsParseException() {
        // Verify that an invalid ingredient name throws a ParseException
        assertParseFailure(parser, VALID_NAME + " " + VALID_COST + " " + INVALID_INGREDIENT,
                "Invalid ingredient: " + INVALID_INGREDIENT);
    }

    @Test
    public void parse_missingArgs_throwsParseException() {
        // Verify that missing arguments throw a ParseException
        assertParseFailure(parser, VALID_NAME + " " + VALID_COST,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        // Verify that empty input throws a ParseException
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}

