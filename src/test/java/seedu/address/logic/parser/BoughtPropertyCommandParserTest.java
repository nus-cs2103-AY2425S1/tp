package seedu.address.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.address.logic.commands.BoughtPropertyCommand;
import seedu.address.model.person.Price;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.*;

import java.util.Optional;

public class BoughtPropertyCommandParserTest {

    private final BoughtPropertyCommandParser parser = new BoughtPropertyCommandParser();

    @Test
    public void parse_validArgs_returnBoughtCommandWithUpdatedPrice_success() {
        String priceString = "2000000";
        Optional<Price> actualPrice = Optional.of(new Price(priceString));

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
    public void parse_validArgs_returnBoughtCommandWithUpdatedPriceAndBigGapInArguments_success() {
        String priceString = "2000000";
        Optional<Price> actualPrice = Optional.of(new Price(priceString));

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
    public void parse_validArgs_returnBoughtCommandWithNoUpdatedPrice_success() {
        Optional<Price> actualPrice = Optional.of(new Price("-1"));

        String input = INDEX_FIRST_PERSON.getOneBased()
                + " "
                + INDEX_FIRST_PROPERTY.getOneBased();

        BoughtPropertyCommand expectedCommand = new BoughtPropertyCommand(
                INDEX_FIRST_PERSON,
                INDEX_FIRST_PROPERTY,
                actualPrice);

        assertParseSuccess(parser, input, expectedCommand);
    }

    @Test
    public void parse_missingArgs_returnBoughtCommandFailure() {
        String input = INDEX_FIRST_PERSON.getOneBased()
                + " ";

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BoughtPropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, input, expectedMessage);
    }

    @Test
    public void parse_emptyArgs_returnBoughtCommandFailure() {
        String input = " ";

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BoughtPropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, input, expectedMessage);
    }

    @Test
    public void parse_tooManyArgs_returnBoughtCommandFailure() {
        String input = INDEX_FIRST_PERSON.getOneBased()
                + " "
                + INDEX_FIRST_PROPERTY.getOneBased()
                + " "
                + INDEX_SECOND_PROPERTY.getOneBased();

        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, BoughtPropertyCommand.MESSAGE_USAGE);

        assertParseFailure(parser, input, expectedMessage);
    }
}

