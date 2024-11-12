package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.CAR_DESC_B;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMPTY_ISSUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ISSUE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ISSUE_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.ISSUE_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_MAKE_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_MODEL_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_VIN_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAR_VRN_B;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISSUE_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ISSUE_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MAKE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODEL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VIN;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VRN;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.AMY;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.testutil.TypicalPersons.BOB_WITH_CAR;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddClientCommand;
import seedu.address.model.car.CarMake;
import seedu.address.model.car.CarModel;
import seedu.address.model.car.Vin;
import seedu.address.model.car.Vrn;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.testutil.PersonBuilder;


public class AddClientCommandParserTest {
    private AddClientCommandParser parser = new AddClientCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withIssues(VALID_ISSUE_FRIEND).build();

        // whitespace only preamble
        assertParseFailure(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ISSUE_DESC_FRIEND, AddClientCommand.MESSAGE_NO_CAR_TO_ADD_ISSUES);

        // i/ but no issue
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
            + ADDRESS_DESC_BOB + EMPTY_ISSUE_DESC, AddClientCommand.MESSAGE_NO_CAR_TO_ADD_ISSUES);

        // multiple issues - all accepted
        Person expectedPersonMultipleIssues = new PersonBuilder(BOB).withIssues(VALID_ISSUE_FRIEND, VALID_ISSUE_HUSBAND)
                .build();
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ISSUE_DESC_HUSBAND + ISSUE_DESC_FRIEND,
                AddClientCommand.MESSAGE_NO_CAR_TO_ADD_ISSUES);
    }

    /*
    * Test add client command with car details
    */
    public void parseAllFieldsPresentWithCarSuccess() {
        Person expectedPerson = new PersonBuilder(BOB_WITH_CAR).withIssues(VALID_ISSUE_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ISSUE_DESC_FRIEND + CAR_DESC_B, new AddClientCommand(expectedPerson));


        // multiple issues - all accepted
        Person expectedPersonMultipleIssues = new PersonBuilder(BOB_WITH_CAR)
                .withIssues(VALID_ISSUE_FRIEND, VALID_ISSUE_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ISSUE_DESC_HUSBAND + ISSUE_DESC_FRIEND + CAR_DESC_B,
                new AddClientCommand(expectedPersonMultipleIssues));
    }

    /*
    * Test add client command with invalid car details
    */
    @Test
    public void parseAllFieldsPresentWithInvalidCarFailure() {
        // invalid vin
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ISSUE_DESC_FRIEND + " " + PREFIX_VIN + "0" + " " + PREFIX_VRN + VALID_CAR_VRN_B + " " + PREFIX_MAKE
                + VALID_CAR_MAKE_B + " " + PREFIX_MODEL + VALID_CAR_MODEL_B,
                Vin.MESSAGE_CONSTRAINTS);

        // invalid vrn
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ISSUE_DESC_FRIEND + " " + PREFIX_VIN + VALID_CAR_VIN_B + " " + PREFIX_VRN + "SHA 7891 A (Z)" + " "
                + PREFIX_MAKE + VALID_CAR_MAKE_B + " " + PREFIX_MODEL + VALID_CAR_MODEL_B,
                Vrn.MESSAGE_CONSTRAINTS);

        // invalid make
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ISSUE_DESC_FRIEND + " " + PREFIX_VIN + VALID_CAR_VIN_B + " " + PREFIX_VRN + VALID_CAR_VRN_B + " "
                + PREFIX_MAKE + "invalid_make" + " " + PREFIX_MODEL + VALID_CAR_MODEL_B,
                CarMake.MESSAGE_CONSTRAINTS);

        // invalid model
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ISSUE_DESC_FRIEND + " " + PREFIX_VIN + VALID_CAR_VIN_B + " " + PREFIX_VRN + VALID_CAR_VRN_B
                + " " + PREFIX_MAKE + VALID_CAR_MAKE_B + " " + PREFIX_MODEL + "invalid_model",
                CarModel.MESSAGE_CONSTRAINTS);
    }

    /*
    * Test add client command with missing car details
    */
    @Test
    public void parseAllFieldsPresentWithMissingCarDetailsFailure() {
        // missing vin
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ISSUE_DESC_FRIEND + " " + PREFIX_VRN + VALID_CAR_VRN_B + " " + PREFIX_MAKE
                + VALID_CAR_MAKE_B + " " + PREFIX_MODEL + VALID_CAR_MODEL_B,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClientCommand.VEHICLE_DETAILS_MISSING));

        // missing vrn
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ISSUE_DESC_FRIEND + " " + PREFIX_VIN + VALID_CAR_VIN_B + " " + PREFIX_MAKE
                + VALID_CAR_MAKE_B + " " + PREFIX_MODEL + VALID_CAR_MODEL_B,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClientCommand.VEHICLE_DETAILS_MISSING));

        // missing make
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ISSUE_DESC_FRIEND + " " + PREFIX_VIN + VALID_CAR_VIN_B + " " + PREFIX_VRN
                + VALID_CAR_VRN_B + " " + PREFIX_MODEL + VALID_CAR_MODEL_B,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClientCommand.VEHICLE_DETAILS_MISSING));

        // missing model
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ISSUE_DESC_FRIEND + " " + PREFIX_VIN + VALID_CAR_VIN_B + " " + PREFIX_VRN
                + VALID_CAR_VRN_B + " " + PREFIX_MAKE + VALID_CAR_MAKE_B,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClientCommand.VEHICLE_DETAILS_MISSING));
    }

    @Test
    public void parse_repeatedNonIssueValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ISSUE_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedPersonString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero issues
        Person expectedPerson = new PersonBuilder(AMY).withIssues().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddClientCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClientCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB + ADDRESS_DESC_BOB,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + VALID_ADDRESS_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB + VALID_ADDRESS_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ISSUE_DESC_HUSBAND + ISSUE_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + ISSUE_DESC_HUSBAND + ISSUE_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + ISSUE_DESC_HUSBAND + ISSUE_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + ISSUE_DESC_HUSBAND + ISSUE_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid issue
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_ISSUE_DESC + VALID_ISSUE_FRIEND, AddClientCommand.MESSAGE_NO_CAR_TO_ADD_ISSUES);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + ISSUE_DESC_HUSBAND + ISSUE_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddClientCommand.MESSAGE_USAGE));
    }
}
