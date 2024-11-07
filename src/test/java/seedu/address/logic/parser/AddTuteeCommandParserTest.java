package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_CLARA;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_DEACON;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_CLARA;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_DEACON;
import static seedu.address.logic.commands.CommandTestUtil.HOURS_DESC_CLARA;
import static seedu.address.logic.commands.CommandTestUtil.HOURS_DESC_DEACON;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HOURS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_CLARA;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_DEACON;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_CLARA;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_DEACON;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_DEACON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_DEACON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_DEACON;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_DEACON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.CLARA;
import static seedu.address.testutil.TypicalPersons.DEACON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddTuteeCommand;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Hours;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Tutee;
import seedu.address.testutil.TuteeBuilder;

public class AddTuteeCommandParserTest {
    private AddTuteeCommandParser parser = new AddTuteeCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Tutee expectedTutee = new TuteeBuilder(DEACON).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_DEACON + PHONE_DESC_DEACON + EMAIL_DESC_DEACON
                + ADDRESS_DESC_DEACON + HOURS_DESC_DEACON, new AddTuteeCommand(expectedTutee));


        Tutee expectedTuteeMultipleTags = new TuteeBuilder(DEACON).build();
        assertParseSuccess(parser,
                NAME_DESC_DEACON + PHONE_DESC_DEACON + EMAIL_DESC_DEACON + ADDRESS_DESC_DEACON + HOURS_DESC_DEACON,
                new AddTuteeCommand(expectedTuteeMultipleTags));
    }

    @Test
    public void parse_repeatedNonSubjectValue_failure() {
        String validExpectedPersonString = NAME_DESC_DEACON + PHONE_DESC_DEACON + EMAIL_DESC_DEACON
                + ADDRESS_DESC_DEACON + HOURS_DESC_DEACON;

        // multiple names
        assertParseFailure(parser, NAME_DESC_CLARA + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_CLARA + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_CLARA + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_CLARA + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple hours
        assertParseFailure(parser, HOURS_DESC_CLARA + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HOURS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_CLARA + EMAIL_DESC_CLARA + NAME_DESC_CLARA + ADDRESS_DESC_CLARA
                        + HOURS_DESC_CLARA + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_NAME, PREFIX_ADDRESS,
                        PREFIX_HOURS));

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

        // invalid hours
        assertParseFailure(parser, INVALID_HOURS_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HOURS));

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

        // invalid hours
        assertParseFailure(parser, validExpectedPersonString + INVALID_HOURS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_HOURS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero subjects
        Tutee expectedPerson = new TuteeBuilder(CLARA).build();
        assertParseSuccess(parser, NAME_DESC_CLARA + PHONE_DESC_CLARA + EMAIL_DESC_CLARA + ADDRESS_DESC_CLARA
                + HOURS_DESC_CLARA, new AddTuteeCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_DEACON + PHONE_DESC_DEACON + EMAIL_DESC_DEACON + ADDRESS_DESC_DEACON,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_DEACON + VALID_PHONE_DEACON + EMAIL_DESC_DEACON + ADDRESS_DESC_DEACON,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_DEACON + PHONE_DESC_DEACON + VALID_EMAIL_DEACON + ADDRESS_DESC_DEACON,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_DEACON + PHONE_DESC_DEACON + EMAIL_DESC_DEACON + VALID_ADDRESS_DEACON,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_DEACON + VALID_PHONE_DEACON + VALID_EMAIL_DEACON + VALID_ADDRESS_DEACON,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_DEACON + EMAIL_DESC_DEACON + ADDRESS_DESC_DEACON
                + HOURS_DESC_DEACON, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_DEACON + INVALID_PHONE_DESC + EMAIL_DESC_DEACON + ADDRESS_DESC_DEACON
                + HOURS_DESC_DEACON, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_DEACON + PHONE_DESC_DEACON + INVALID_EMAIL_DESC + ADDRESS_DESC_DEACON
                + HOURS_DESC_DEACON, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_DEACON + PHONE_DESC_DEACON + EMAIL_DESC_DEACON + INVALID_ADDRESS_DESC
                + HOURS_DESC_DEACON, Address.MESSAGE_CONSTRAINTS);

        // invalid hours
        assertParseFailure(parser, NAME_DESC_DEACON + PHONE_DESC_DEACON + EMAIL_DESC_DEACON + INVALID_ADDRESS_DESC
                + INVALID_HOURS_DESC, Hours.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_DEACON + EMAIL_DESC_DEACON + INVALID_ADDRESS_DESC
                + HOURS_DESC_DEACON, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_DEACON + PHONE_DESC_DEACON + EMAIL_DESC_DEACON
                        + ADDRESS_DESC_DEACON + HOURS_DESC_DEACON,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddTuteeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noHoursSpecified_success() {
        Tutee expectedTutee = new TuteeBuilder(DEACON).withHours("0").build();
        assertParseSuccess(parser, NAME_DESC_DEACON + PHONE_DESC_DEACON + EMAIL_DESC_DEACON + ADDRESS_DESC_DEACON,
                new AddTuteeCommand(expectedTutee));
    }
}
