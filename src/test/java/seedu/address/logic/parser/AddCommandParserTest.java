package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BOFA;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOFA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOFA;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_BOFA;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COMPANYNAME_GOOGLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BOFA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOFA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOFA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_BOFA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalInternshipApplications.APPLE;
import static seedu.address.testutil.TypicalInternshipApplications.GOOGLE;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.internshipapplication.Address;
import seedu.address.model.internshipapplication.Date;
import seedu.address.model.internshipapplication.Email;
import seedu.address.model.internshipapplication.InternshipApplication;
import seedu.address.model.internshipapplication.Name;
import seedu.address.model.internshipapplication.Person;
import seedu.address.model.internshipapplication.Phone;
import seedu.address.model.internshipapplication.Role;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.InternshipApplicationBuilder;
import seedu.address.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        InternshipApplication expectedApplication = new InternshipApplicationBuilder(APPLE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_APPLE + ROLE_DESC_APPLE + EMAIL_DESC_APPLE
                + DATE_DESC_APPLE, new AddCommand(APPLE));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedInternshipApplicationString = NAME_DESC_BOFA + ROLE_DESC_BOFA
                + EMAIL_DESC_BOFA + DATE_DESC_BOFA;

        // multiple names
        assertParseFailure(parser, NAME_DESC_APPLE + validExpectedInternshipApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple roles
        assertParseFailure(parser, ROLE_DESC_APPLE + validExpectedInternshipApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_APPLE + validExpectedInternshipApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple dates
        assertParseFailure(parser, DATE_DESC_APPLE + validExpectedInternshipApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedInternshipApplicationString + ROLE_DESC_APPLE + EMAIL_DESC_APPLE + NAME_DESC_APPLE + DATE_DESC_APPLE
                        + validExpectedInternshipApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ROLE, PREFIX_EMAIL, PREFIX_DATE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedInternshipApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedInternshipApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid role
        assertParseFailure(parser, INVALID_ROLE_DESC + validExpectedInternshipApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // invalid date
        assertParseFailure(parser, INVALID_DATE_DESC + validExpectedInternshipApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedInternshipApplicationString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedInternshipApplicationString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid role
        assertParseFailure(parser, validExpectedInternshipApplicationString + INVALID_ROLE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // invalid date
        assertParseFailure(parser, validExpectedInternshipApplicationString + INVALID_DATE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        InternshipApplication expectedApplication = new InternshipApplicationBuilder(APPLE).build();
        assertParseSuccess(parser, NAME_DESC_APPLE + ROLE_DESC_APPLE + EMAIL_DESC_AMY + DATE_DESC_APPLE,
                new AddCommand(expectedApplication));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_APPLE + ROLE_DESC_APPLE + EMAIL_DESC_APPLE + DATE_DESC_APPLE,
                expectedMessage);

        // missing role prefix
        assertParseFailure(parser, NAME_DESC_BOFA + VALID_ROLE_BOFA + EMAIL_DESC_BOFA + DATE_DESC_BOFA,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOFA + ROLE_DESC_BOFA + VALID_EMAIL_BOFA + DATE_DESC_BOFA,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, NAME_DESC_BOFA + ROLE_DESC_BOFA + EMAIL_DESC_BOFA + VALID_DATE_BOFA,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOFA + VALID_ROLE_BOFA + VALID_EMAIL_BOFA + VALID_DATE_BOFA,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + ROLE_DESC_BOFA
                + EMAIL_DESC_BOFA + DATE_DESC_BOFA, Name.MESSAGE_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, NAME_DESC_BOFA + INVALID_ROLE_DESC
                + EMAIL_DESC_BOFA + DATE_DESC_BOFA, Role.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOFA + ROLE_DESC_BOFA
                + INVALID_EMAIL_DESC + DATE_DESC_BOFA, Email.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, NAME_DESC_BOFA + ROLE_DESC_BOFA
                + EMAIL_DESC_BOFA + DATE_DESC_BOFA, Date.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + ROLE_DESC_BOFA + EMAIL_DESC_BOFA + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOFA + ROLE_DESC_BOFA + EMAIL_DESC_BOFA
                + DATE_DESC_BOFA, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
