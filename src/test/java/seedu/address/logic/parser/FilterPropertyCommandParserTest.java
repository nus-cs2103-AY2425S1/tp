package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterPropertyCommand;
import seedu.address.model.property.MatchingPrice;
import seedu.address.model.property.Type;

public class FilterPropertyCommandParserTest {
    private FilterPropertyCommandParser parser = new FilterPropertyCommandParser();
    private final String type = "CONDO";
    private final String lte = "60000";
    private final String gte = "60000";

    @Test
    public void parse_nameSpecified_success() {
        // Test for valid phone number
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_TYPE + type;
        assertParseSuccess(parser, userInput, new FilterPropertyCommand(new Type(type), null, null));
    }

    @Test
    public void parse_lteSpecified_success() {
        // Test for valid phone number
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_TYPE + type + " " + PREFIX_LTE + lte;
        assertParseSuccess(parser, userInput, new FilterPropertyCommand(new Type(type), new MatchingPrice(lte),
                null));
    }

    @Test
    public void parse_gteSpecified_success() {
        // Test for valid phone number
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_TYPE + type + " " + PREFIX_GTE + gte;
        assertParseSuccess(parser, userInput, new FilterPropertyCommand(new Type(type), null,
                new MatchingPrice(gte)));
    }

    @Test
    public void parse_allSpecified_success() {
        // Test for valid phone number
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_TYPE + type + " " + PREFIX_LTE + lte
                + " " + PREFIX_GTE + gte;
        assertParseSuccess(parser, userInput, new FilterPropertyCommand(new Type(type), null, null));
    }

    @Test
    public void parse_emptyType_failure() {
        // Test for empty phone number
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_TYPE + "";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyGte_failure() {
        // Test for empty phone number
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_GTE + "";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyLte_failure() {
        // Test for empty phone number
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_LTE + "";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE);
        // no parameters
        assertParseFailure(parser, FilterPropertyCommand.COMMAND_WORD, expectedMessage);
    }
}
