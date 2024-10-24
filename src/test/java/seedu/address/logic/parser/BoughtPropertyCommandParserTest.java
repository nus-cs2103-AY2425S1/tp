package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PROPERTY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PROPERTY;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.BoughtPropertyCommand;
import seedu.address.model.person.Price;

public class BoughtPropertyCommandParserTest {

    private final BoughtPropertyCommandParser parser = new BoughtPropertyCommandParser();

    @Test
    public void parseValidArgsReturnBoughtCommandWithUpdatedPrice_success() {
        String priceString = "2000000";
        Price actualPrice = new Price(priceString);

        String input = INDEX_FIRST_PERSON.getOneBased()
                + " "
                + INDEX_FIRST_PROPERTY.getOneBased()
                + " ap/" + priceString;

        BoughtPropertyCommand expectedCommand = new BoughtPropertyCommand(
                INDEX_FIRST_PERSON,
                INDEX_FIRST_PROPERTY,
                actualPrice);

        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parseValidArgsReturnBoughtCommandWithUpdatedPriceAndBigGapInArguments_success() {
        String priceString = "2000000";
        Price actualPrice = new Price(priceString);

        String input = INDEX_FIRST_PERSON.getOneBased()
                + "                              "
                + INDEX_FIRST_PROPERTY.getOneBased()
                + " ap/" + priceString;

        BoughtPropertyCommand expectedCommand = new BoughtPropertyCommand(
                INDEX_FIRST_PERSON,
                INDEX_FIRST_PROPERTY,
                actualPrice);

        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_missingArgsReturnBoughtCommandFailure() {
        String input = INDEX_FIRST_PERSON.getOneBased()
                + " ";

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BoughtPropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, input, expectedMessage);
    }

    @Test
    public void parse_emptyArgsReturnBoughtCommandFailure() {
        String input = " ";

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BoughtPropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, input, expectedMessage);
    }

    @Test
    public void parse_tooManyArgsReturnBoughtCommandFailure() {
        String input = INDEX_FIRST_PERSON.getOneBased()
                + " "
                + INDEX_FIRST_PROPERTY.getOneBased()
                + " "
                + INDEX_SECOND_PROPERTY.getOneBased();

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BoughtPropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, input, expectedMessage);
    }
}

