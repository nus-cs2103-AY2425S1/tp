package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ASK_DESC_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.BID_DESC_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ASK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_BID_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_POSTALCODE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TYPE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_UNIT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.POSTALCODE_DESC_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.TYPE_DESC_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.UNIT_DESC_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ASK_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BID_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_POSTALCODE_ADMIRALTY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_CONDO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_UNIT_ADMIRALTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ASK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTALCODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TYPE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_UNITNUMBER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddPropertyCommand;
import seedu.address.model.property.Ask;
import seedu.address.model.property.Bid;
import seedu.address.model.property.PostalCode;
import seedu.address.model.property.Property;
import seedu.address.model.property.Type;
import seedu.address.model.property.Unit;
import seedu.address.testutil.PropertyBuilder;

public class AddPropertyCommandParserTest {

    private AddPropertyCommandParser parser = new AddPropertyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Property expectedProperty = new PropertyBuilder().withPostalCode(VALID_POSTALCODE_ADMIRALTY)
                .withUnit(VALID_UNIT_ADMIRALTY).withType(VALID_TYPE_CONDO).withAsk(VALID_ASK_ADMIRALTY)
                .withBid(VALID_BID_ADMIRALTY).build();

        // normal input with all fields
        assertParseSuccess(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY
                        + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                new AddPropertyCommand(expectedProperty));
    }

    @Test
    public void parse_missingFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPropertyCommand.MESSAGE_USAGE);

        // missing PostalCode prefix
        assertParseFailure(parser, UNIT_DESC_ADMIRALTY, expectedMessage);

        // missing unit prefix
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY, expectedMessage);

        // missing type prefix
        assertParseFailure(parser, TYPE_DESC_ADMIRALTY, expectedMessage);

        // missing ask prefix
        assertParseFailure(parser, ASK_DESC_ADMIRALTY, expectedMessage);

        // missing bid prefix
        assertParseFailure(parser, BID_DESC_ADMIRALTY, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid postal code
        assertParseFailure(parser, INVALID_POSTALCODE_DESC + UNIT_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY
                + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                PostalCode.MESSAGE_CONSTRAINTS);

        // invalid unit
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + INVALID_UNIT_DESC + TYPE_DESC_ADMIRALTY
                + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                Unit.MESSAGE_CONSTRAINTS);

        // invalid type
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + INVALID_TYPE_DESC
                + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                Type.MESSAGE_CONSTRAINTS);

        // invalid ask
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY
                        + INVALID_ASK_DESC + BID_DESC_ADMIRALTY,
                Ask.MESSAGE_CONSTRAINTS);

        // invalid bid
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY
                        + ASK_DESC_ADMIRALTY + INVALID_BID_DESC,
                Bid.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefixes_failure() {
        String expectedMessagePostalCode = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_POSTALCODE);
        String expectedMessageUnit = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_UNITNUMBER);
        String expectedMessageType = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TYPE);
        String expectedMessageAsk = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ASK);
        String expectedMessageBid = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BID);

        // duplicate postalcode prefix
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY
                + TYPE_DESC_ADMIRALTY + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                expectedMessagePostalCode);

        // duplicate unit prefix
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY
                + TYPE_DESC_ADMIRALTY + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                expectedMessageUnit);

        // duplicate type prefix
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY
                + TYPE_DESC_ADMIRALTY + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                expectedMessageType);

        // duplicate type ask
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY
                + ASK_DESC_ADMIRALTY + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                expectedMessageAsk);

        // duplicate type bid
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY
                + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                expectedMessageBid);
    }

    @Test
    public void parse_excessPrefixes_failure() {
        String excessPrefixErrorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddPropertyCommand.MESSAGE_USAGE);

        Prefix prefix = new Prefix("z/");

        // excess prefix before postalcode prefix
        assertParseFailure(parser, " " + prefix + POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY
                        + TYPE_DESC_ADMIRALTY + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                excessPrefixErrorMessage);

        // excess prefix before unit prefix
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + " " + prefix + UNIT_DESC_ADMIRALTY
                        + TYPE_DESC_ADMIRALTY + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                excessPrefixErrorMessage);

        // excess prefix before type prefix
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + " " + prefix
                        + TYPE_DESC_ADMIRALTY + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                excessPrefixErrorMessage);

        // excess prefix before type ask
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY
                        + " " + prefix + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                excessPrefixErrorMessage);

        // excess prefix before type bid
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY
                        + ASK_DESC_ADMIRALTY + " " + prefix + BID_DESC_ADMIRALTY,
                excessPrefixErrorMessage);

        // excess prefix after commands
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY
                        + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY + " " + prefix,
                excessPrefixErrorMessage);


        Prefix nonCommandPrefix = new Prefix("/");
        // excess invalid non-command prefix before PostalCode prefix ->
        // throws primary command specific error message because not command
        assertParseFailure(parser, " " + nonCommandPrefix + POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY
                        + TYPE_DESC_ADMIRALTY + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                excessPrefixErrorMessage);

        // excess invalid non-command prefix before unit prefix ->
        // throws prefix specific error message because not command
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + " " + nonCommandPrefix + UNIT_DESC_ADMIRALTY
                        + TYPE_DESC_ADMIRALTY + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                PostalCode.MESSAGE_CONSTRAINTS);

        // excess invalid non-command prefix before type prefix ->
        // throws prefix specific error message because not command
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + " " + nonCommandPrefix
                        + TYPE_DESC_ADMIRALTY + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                Unit.MESSAGE_CONSTRAINTS);

        // excess invalid non-command prefix before type ask -> throws prefix specific error message because not command
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY
                        + " " + nonCommandPrefix + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                Type.MESSAGE_CONSTRAINTS);

        // excess invalid non-command prefix before type bid -> throws prefix specific error message because not command
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY
                        + ASK_DESC_ADMIRALTY + " " + nonCommandPrefix + BID_DESC_ADMIRALTY,
                Ask.MESSAGE_CONSTRAINTS);

        // excess invalid non-command prefix after type bid -> throws prefix specific error message because not command
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY
                        + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY + " " + nonCommandPrefix,
                Bid.MESSAGE_CONSTRAINTS);



        // slotted invalid prefix in postal code
        assertParseFailure(parser, " " + PREFIX_POSTALCODE + "123 z/456" + UNIT_DESC_ADMIRALTY
                        + TYPE_DESC_ADMIRALTY + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                excessPrefixErrorMessage);

        // slotted invalid prefix in unit
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + " " + PREFIX_UNITNUMBER + "11 z/11"
                        + TYPE_DESC_ADMIRALTY + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                excessPrefixErrorMessage);

        // slotted invalid prefix in type
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY
                        + " " + PREFIX_TYPE + "H z/DB" + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                excessPrefixErrorMessage);

        // slotted invalid prefix in ask
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY
                        + " " + PREFIX_ASK + "200 z/00" + BID_DESC_ADMIRALTY,
                excessPrefixErrorMessage);

        // slotted invalid prefix in bid
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY
                        + ASK_DESC_ADMIRALTY + " " + PREFIX_BID + "200 z/00",
                excessPrefixErrorMessage);




        // slotted invalid non-command prefix in postal code -> throws prefix specific error message because not command
        assertParseFailure(parser, " " + PREFIX_POSTALCODE + "123 /456" + UNIT_DESC_ADMIRALTY
                        + TYPE_DESC_ADMIRALTY + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                PostalCode.MESSAGE_CONSTRAINTS);

        // slotted invalid non-command prefix in unit -> throws prefix specific error message because not command
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + " " + PREFIX_UNITNUMBER + "11 /11"
                        + TYPE_DESC_ADMIRALTY + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                Unit.MESSAGE_CONSTRAINTS);

        // slotted invalid non-command prefix in type -> throws prefix specific error message because not command
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY
                        + " " + PREFIX_TYPE + "H /DB" + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                Type.MESSAGE_CONSTRAINTS);

        // slotted invalid non-command prefix in ask -> throws prefix specific error message because not command
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY
                        + " " + PREFIX_ASK + "200 /00" + BID_DESC_ADMIRALTY,
                Ask.MESSAGE_CONSTRAINTS);

        // slotted invalid non-command prefix in bid -> throws prefix specific error message because not command
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY
                        + ASK_DESC_ADMIRALTY + " " + PREFIX_BID + "200 /00",
                Bid.MESSAGE_CONSTRAINTS);




        // invalid non-command prefix in postalcode param specifier
        // -> throws prefix specific error message because not command
        assertParseFailure(parser, " /" + PREFIX_POSTALCODE + "123456" + UNIT_DESC_ADMIRALTY
                        + TYPE_DESC_ADMIRALTY + ASK_DESC_ADMIRALTY + BID_DESC_ADMIRALTY,
                excessPrefixErrorMessage);

        // invalid non-command prefix in ask param specifier
        // -> throws prefix specific error message because not command
        assertParseFailure(parser, POSTALCODE_DESC_ADMIRALTY + UNIT_DESC_ADMIRALTY + TYPE_DESC_ADMIRALTY
                        + " /" + PREFIX_ASK + "20000" + BID_DESC_ADMIRALTY,
                excessPrefixErrorMessage);
    }
}
