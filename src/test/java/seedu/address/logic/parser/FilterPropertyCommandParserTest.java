package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GTE_DESC_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.LTE_DESC_BEDOK;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_ADMIRALTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LTE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FilterPropertyCommand;
import seedu.address.model.property.MatchingPrice;
import seedu.address.model.property.Type;

public class FilterPropertyCommandParserTest {
    private FilterPropertyCommandParser parser = new FilterPropertyCommandParser();
    private final String type = "CONDO";
    private final String lte = "60000";
    private final String gte = "50000";

    private final String invalidType = "PUBLIC";
    private final String invalidLte = "$60000";
    private final String invalidGte = "$50000";

    @Test
    public void parse_nameSpecified_success() {
        String userInput = " " + PREFIX_TYPE + type;
        assertParseSuccess(parser, userInput, new FilterPropertyCommand(new Type(type), null, null));
    }

    @Test
    public void parse_lteSpecified_success() {
        String userInput = " " + PREFIX_TYPE + type + " " + PREFIX_LTE + lte;
        assertParseSuccess(parser, userInput, new FilterPropertyCommand(new Type(type), new MatchingPrice(lte),
                null));
    }

    @Test
    public void parse_gteSpecified_success() {
        String userInput = " " + PREFIX_TYPE + type + " " + PREFIX_GTE + gte;
        assertParseSuccess(parser, userInput, new FilterPropertyCommand(new Type(type), null,
                new MatchingPrice(gte)));
    }

    @Test
    public void parse_allFieldsPresent_success() {
        String userInput = " " + PREFIX_TYPE + type + " " + PREFIX_LTE + lte
                + " " + PREFIX_GTE + gte;
        assertParseSuccess(parser, userInput, new FilterPropertyCommand(new Type(type),
                new MatchingPrice(lte), new MatchingPrice(gte)));
    }

    @Test
    public void parse_emptyType_failure() {
        String userInput = " " + PREFIX_TYPE + "";
        assertParseFailure(parser, userInput, Type.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptyGte_failure() {
        String userInput = " " + PREFIX_GTE + "";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, MatchingPrice.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptyLteTwoParams_failure() {
        String userInput = " " + PREFIX_TYPE + type + " " + PREFIX_LTE + "";
        assertParseFailure(parser, userInput, MatchingPrice.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_emptyLte_failure() {
        String userInput = " " + PREFIX_LTE + "";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, MatchingPrice.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_invalidType_failure() {
        String userInput = " " + PREFIX_TYPE + invalidType;
        String expectedMessage = String.format(Type.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidGte_failure() {
        String userInput = " " + PREFIX_GTE + invalidGte;
        String expectedMessage = String.format(MatchingPrice.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidLte_failure() {
        String userInput = " " + PREFIX_LTE + invalidLte;
        String expectedMessage = String.format(MatchingPrice.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE);
        assertParseFailure(parser, FilterPropertyCommand.COMMAND_WORD, expectedMessage);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String expectedMessageType = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TYPE);
        String expectedMessageLte = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_LTE);
        String expectedMessageGte = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_GTE);

        // duplicate type prefix
        assertParseFailure(parser, TYPE_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY + LTE_DESC_BEDOK,
                expectedMessageType);

        // duplicate lte prefix
        assertParseFailure(parser, TYPE_DESC_ADMIRALTY + LTE_DESC_BEDOK + LTE_DESC_BEDOK,
                expectedMessageLte);

        // duplicate gte prefix
        assertParseFailure(parser, TYPE_DESC_ADMIRALTY + GTE_DESC_BEDOK + GTE_DESC_BEDOK,
                expectedMessageGte);
    }

    @Test
    public void parse_excessPrefixes_failure() {
        String excessPrefixErrorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterPropertyCommand.MESSAGE_USAGE);

        String prefix = " z/";
        String first = " " + PREFIX_TYPE + type;
        String second = " " + PREFIX_LTE + lte;
        String third = " " + PREFIX_GTE + gte;

        // excess prefix before type prefix
        assertParseFailure(parser, prefix + first + second + third,
                excessPrefixErrorMessage);

        // excess prefix before LTE prefix
        assertParseFailure(parser, first + prefix + second + third,
                excessPrefixErrorMessage);

        // excess prefix before GTE prefix
        assertParseFailure(parser, first + second + prefix + third,
                excessPrefixErrorMessage);

        // excess prefix after GTE ask
        assertParseFailure(parser, first + second + third + prefix,
                excessPrefixErrorMessage);

        // excess prefix after LTE ask with one less param
        assertParseFailure(parser, first + second + prefix,
                excessPrefixErrorMessage);

        // excess prefix after type ask with one less param
        assertParseFailure(parser, first + prefix + second,
                excessPrefixErrorMessage);


        String nonCommandPrefix = " /";
        // excess invalid non-command prefix before PostalCode prefix ->
        // throws primary command specific error message because not command
        assertParseFailure(parser, nonCommandPrefix + first + second + third,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPropertyCommand.MESSAGE_USAGE));

        // excess invalid non-command prefix after type prefix ->
        // throws prefix specific error message because not command
        assertParseFailure(parser, first + nonCommandPrefix + second + third,
                Type.MESSAGE_CONSTRAINTS);

        // excess invalid non-command prefix after LTE prefix ->
        // throws prefix specific error message because not command
        assertParseFailure(parser, first + second + nonCommandPrefix + third,
                MatchingPrice.MESSAGE_CONSTRAINTS);

        // excess invalid non-command prefix after GTE prefix ->
        // throws prefix specific error message because not command
        assertParseFailure(parser, first + second + third + nonCommandPrefix,
                MatchingPrice.MESSAGE_CONSTRAINTS);

        // excess invalid non-command prefix after LTE prefix with less param ->
        // throws prefix specific error message because not command
        assertParseFailure(parser, first + second + nonCommandPrefix,
                MatchingPrice.MESSAGE_CONSTRAINTS);

        // excess invalid non-command prefix after GTE prefix with less param->
        // throws prefix specific error message because not command
        assertParseFailure(parser, first + nonCommandPrefix + second + nonCommandPrefix,
                Type.MESSAGE_CONSTRAINTS);



        // slotted invalid prefix in postal code
        assertParseFailure(parser, " " + PREFIX_TYPE + "H z/DB" + second + third,
                excessPrefixErrorMessage);

        // slotted invalid prefix in unit
        assertParseFailure(parser, first + " " + PREFIX_LTE + "10 z/00" + third,
                excessPrefixErrorMessage);

        // slotted invalid prefix in type
        assertParseFailure(parser, first + second + " " + PREFIX_GTE + "10 z/00",
                excessPrefixErrorMessage);



        // slotted invalid non-command prefix in unit -> throws prefix specific error message because not command
        assertParseFailure(parser, " " + PREFIX_TYPE + "H /DB" + second + third,
                Type.MESSAGE_CONSTRAINTS);

        // slotted invalid non-command prefix in type -> throws prefix specific error message because not command
        assertParseFailure(parser, first + " " + PREFIX_LTE + "10 /00" + third,
                MatchingPrice.MESSAGE_CONSTRAINTS);

        // slotted invalid non-command prefix in ask -> throws prefix specific error message because not command
        assertParseFailure(parser, first + second + " " + PREFIX_GTE + "10 /00",
                MatchingPrice.MESSAGE_CONSTRAINTS);



        // invalid non-command prefix in gte param specifier -> throws prefix specific error message because not command
        assertParseFailure(parser, first + second + " /" + PREFIX_GTE + "1000",
                excessPrefixErrorMessage);

        // invalid non-command prefix in gte param specifier and fewer params
        // -> throws prefix specific error message because not command
        assertParseFailure(parser, first + " /" + PREFIX_GTE + "1000",
                excessPrefixErrorMessage);

        // invalid non-command prefix in gte param specifier -> throws prefix specific error message because not command
        assertParseFailure(parser, " /" + PREFIX_TYPE + "HDB" + second + third,
                excessPrefixErrorMessage);
    }
}
