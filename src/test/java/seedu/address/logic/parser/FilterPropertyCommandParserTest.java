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

    private final String invalidType = "PUBLIC";
    private final String invalidLte = "$60000";
    private final String invalidGte = "$60000";

    @Test
    public void parse_nameSpecified_success() {
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_TYPE + type;
        assertParseSuccess(parser, userInput, new FilterPropertyCommand(new Type(type), null, null));
    }

    @Test
    public void parse_lteSpecified_success() {
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_TYPE + type + " " + PREFIX_LTE + lte;
        assertParseSuccess(parser, userInput, new FilterPropertyCommand(new Type(type), new MatchingPrice(lte),
                null));
    }

    @Test
    public void parse_gteSpecified_success() {
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_TYPE + type + " " + PREFIX_GTE + gte;
        assertParseSuccess(parser, userInput, new FilterPropertyCommand(new Type(type), null,
                new MatchingPrice(gte)));
    }

    @Test
    public void parse_allSpecified_success() {
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_TYPE + type + " " + PREFIX_LTE + lte
                + " " + PREFIX_GTE + gte;
        assertParseSuccess(parser, userInput, new FilterPropertyCommand(new Type(type), null, null));
    }

    @Test
    public void parse_emptyType_failure() {
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_TYPE + "";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyGte_failure() {
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_GTE + "";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_emptyLte_failure() {
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_LTE + "";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidType_failure() {
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_TYPE + invalidType;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, Type.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidGte_failure() {
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_GTE + invalidGte;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchingPrice.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidLte_failure() {
        String userInput = FilterPropertyCommand.COMMAND_WORD + " " + PREFIX_LTE + invalidLte;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MatchingPrice.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, FilterPropertyCommand.COMMAND_WORD, expectedMessage);
    }
}
