package seedu.hireme.logic.parser;

import static seedu.hireme.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.hireme.logic.commands.CommandTestUtil.COMPANY_EMAIL_DESC_APPLE;
import static seedu.hireme.logic.commands.CommandTestUtil.COMPANY_EMAIL_DESC_BOFA;
import static seedu.hireme.logic.commands.CommandTestUtil.COMPANY_NAME_DESC_APPLE;
import static seedu.hireme.logic.commands.CommandTestUtil.COMPANY_NAME_DESC_BOFA;
import static seedu.hireme.logic.commands.CommandTestUtil.DATE_DESC_APPLE;
import static seedu.hireme.logic.commands.CommandTestUtil.DATE_DESC_BOFA;
import static seedu.hireme.logic.commands.CommandTestUtil.INVALID_COMPANY_NAME_DESC;
import static seedu.hireme.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.hireme.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.hireme.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.hireme.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.hireme.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.hireme.logic.commands.CommandTestUtil.ROLE_DESC_APPLE;
import static seedu.hireme.logic.commands.CommandTestUtil.ROLE_DESC_BOFA;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_COMPANY_EMAIL_BOFA;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_COMPANY_NAME_APPLE;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_COMPANY_NAME_BOFA;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_DATE_BOFA;
import static seedu.hireme.logic.commands.CommandTestUtil.VALID_ROLE_BOFA;
import static seedu.hireme.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.hireme.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.hireme.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.hireme.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.hireme.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.hireme.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.hireme.testutil.TypicalInternshipApplications.APPLE;

import org.junit.jupiter.api.Test;

import seedu.hireme.logic.Messages;
import seedu.hireme.logic.commands.AddCommand;
import seedu.hireme.model.internshipapplication.Date;
import seedu.hireme.model.internshipapplication.Email;
import seedu.hireme.model.internshipapplication.InternshipApplication;
import seedu.hireme.model.internshipapplication.Name;
import seedu.hireme.model.internshipapplication.Role;
import seedu.hireme.testutil.InternshipApplicationBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        InternshipApplication expectedApplication = new InternshipApplicationBuilder(APPLE).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + COMPANY_NAME_DESC_APPLE + ROLE_DESC_APPLE
                + COMPANY_EMAIL_DESC_APPLE + DATE_DESC_APPLE, new AddCommand(APPLE));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedInternshipApplicationString = COMPANY_NAME_DESC_BOFA + ROLE_DESC_BOFA
                + COMPANY_EMAIL_DESC_BOFA + DATE_DESC_BOFA;

        // multiple names
        assertParseFailure(parser, COMPANY_NAME_DESC_APPLE + validExpectedInternshipApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple roles
        assertParseFailure(parser, ROLE_DESC_APPLE + validExpectedInternshipApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ROLE));

        // multiple emails
        assertParseFailure(parser, COMPANY_EMAIL_DESC_APPLE + validExpectedInternshipApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple dates
        assertParseFailure(parser, DATE_DESC_APPLE + validExpectedInternshipApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedInternshipApplicationString + ROLE_DESC_APPLE + COMPANY_EMAIL_DESC_APPLE
                        + COMPANY_NAME_DESC_APPLE + DATE_DESC_APPLE + validExpectedInternshipApplicationString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ROLE, PREFIX_EMAIL, PREFIX_DATE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_COMPANY_NAME_DESC + validExpectedInternshipApplicationString,
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
        assertParseFailure(parser, validExpectedInternshipApplicationString + INVALID_COMPANY_NAME_DESC,
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
        assertParseSuccess(parser, COMPANY_NAME_DESC_APPLE + ROLE_DESC_APPLE + COMPANY_EMAIL_DESC_APPLE
                        + DATE_DESC_APPLE, new AddCommand(expectedApplication));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_COMPANY_NAME_APPLE + ROLE_DESC_APPLE + COMPANY_EMAIL_DESC_APPLE
                        + DATE_DESC_APPLE, expectedMessage);

        // missing role prefix
        assertParseFailure(parser, COMPANY_NAME_DESC_BOFA + VALID_ROLE_BOFA + COMPANY_EMAIL_DESC_BOFA
                        + DATE_DESC_BOFA, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, COMPANY_NAME_DESC_BOFA + ROLE_DESC_BOFA + VALID_COMPANY_EMAIL_BOFA
                        + DATE_DESC_BOFA, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, COMPANY_NAME_DESC_BOFA + ROLE_DESC_BOFA + COMPANY_EMAIL_DESC_BOFA
                        + VALID_DATE_BOFA, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_COMPANY_NAME_BOFA + VALID_ROLE_BOFA + VALID_COMPANY_EMAIL_BOFA
                        + VALID_DATE_BOFA, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_COMPANY_NAME_DESC + ROLE_DESC_BOFA
                + COMPANY_EMAIL_DESC_BOFA + DATE_DESC_BOFA, Name.MESSAGE_CONSTRAINTS);

        // invalid role
        assertParseFailure(parser, COMPANY_NAME_DESC_BOFA + INVALID_ROLE_DESC
                + COMPANY_EMAIL_DESC_BOFA + DATE_DESC_BOFA, Role.MESSAGE_CONSTRAINTS);

        // invalid email

        assertParseFailure(parser, COMPANY_NAME_DESC_BOFA + ROLE_DESC_BOFA
                + INVALID_EMAIL_DESC + DATE_DESC_BOFA, Email.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, COMPANY_NAME_DESC_BOFA + ROLE_DESC_BOFA
                + COMPANY_EMAIL_DESC_BOFA + INVALID_DATE_DESC, Date.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_COMPANY_NAME_DESC + ROLE_DESC_BOFA + INVALID_EMAIL_DESC
                        + DATE_DESC_BOFA, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + COMPANY_NAME_DESC_BOFA + ROLE_DESC_BOFA
                + COMPANY_EMAIL_DESC_BOFA + DATE_DESC_BOFA, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddCommand.MESSAGE_USAGE));
    }
}
