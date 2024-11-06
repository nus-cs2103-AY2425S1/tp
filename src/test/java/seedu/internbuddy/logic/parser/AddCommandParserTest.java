package seedu.internbuddy.logic.parser;

import static seedu.internbuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.ADDRESS_DESC_GOOGLE;
import static seedu.internbuddy.logic.commands.CommandTestUtil.ADDRESS_DESC_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.EMAIL_DESC_GOOGLE;
import static seedu.internbuddy.logic.commands.CommandTestUtil.EMAIL_DESC_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.internbuddy.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.internbuddy.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.internbuddy.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.internbuddy.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.internbuddy.logic.commands.CommandTestUtil.NAME_DESC_GOOGLE;
import static seedu.internbuddy.logic.commands.CommandTestUtil.NAME_DESC_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.PHONE_DESC_GOOGLE;
import static seedu.internbuddy.logic.commands.CommandTestUtil.PHONE_DESC_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.internbuddy.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.internbuddy.logic.commands.CommandTestUtil.STATUS_DESC_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.TAG_DESC_SOFTWARE;
import static seedu.internbuddy.logic.commands.CommandTestUtil.TAG_DESC_TECH;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_ADDRESS_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_EMAIL_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_NAME_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_PHONE_MICROSOFT;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_TAG_SOFTWARE;
import static seedu.internbuddy.logic.commands.CommandTestUtil.VALID_TAG_TECH;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.internbuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.internbuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.internbuddy.testutil.TypicalCompanies.GOOGLE;
import static seedu.internbuddy.testutil.TypicalCompanies.MICROSOFT;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.logic.Messages;
import seedu.internbuddy.logic.commands.AddCommand;
import seedu.internbuddy.model.company.Address;
import seedu.internbuddy.model.company.Company;
import seedu.internbuddy.model.company.Email;
import seedu.internbuddy.model.company.Phone;
import seedu.internbuddy.model.name.Name;
import seedu.internbuddy.model.tag.Tag;
import seedu.internbuddy.testutil.CompanyBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Company expectedcompany = new CompanyBuilder(MICROSOFT).withTags(VALID_TAG_TECH).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT
                        + EMAIL_DESC_MICROSOFT + ADDRESS_DESC_MICROSOFT + TAG_DESC_TECH,
                new AddCommand(expectedcompany));


        // multiple tags - all accepted
        Company expectedcompanyMultipleTags = new CompanyBuilder(MICROSOFT).withTags(VALID_TAG_TECH, VALID_TAG_SOFTWARE)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT + EMAIL_DESC_MICROSOFT + ADDRESS_DESC_MICROSOFT
                        + TAG_DESC_SOFTWARE + TAG_DESC_TECH, new AddCommand(expectedcompanyMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedcompaniestring = NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT + EMAIL_DESC_MICROSOFT
                + ADDRESS_DESC_MICROSOFT + TAG_DESC_TECH + STATUS_DESC_MICROSOFT;

        // multiple names
        assertParseFailure(parser, NAME_DESC_GOOGLE + validExpectedcompaniestring,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_GOOGLE + validExpectedcompaniestring,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_GOOGLE + validExpectedcompaniestring,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_GOOGLE + validExpectedcompaniestring,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedcompaniestring + PHONE_DESC_GOOGLE + EMAIL_DESC_GOOGLE + NAME_DESC_GOOGLE
                        + ADDRESS_DESC_GOOGLE + validExpectedcompaniestring,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedcompaniestring,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedcompaniestring,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedcompaniestring,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedcompaniestring,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedcompaniestring + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedcompaniestring + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedcompaniestring + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedcompaniestring + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // todo: add test for the other optional fields
        // zero tags
        Company expectedcompany = new CompanyBuilder(GOOGLE).withTags().withApplications()
                .withStatus("INTERESTED").build();
        assertParseSuccess(parser, NAME_DESC_GOOGLE + PHONE_DESC_GOOGLE + EMAIL_DESC_GOOGLE
                        + ADDRESS_DESC_GOOGLE, new AddCommand(expectedcompany));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_MICROSOFT + PHONE_DESC_MICROSOFT + EMAIL_DESC_MICROSOFT
                        + ADDRESS_DESC_MICROSOFT,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT + VALID_EMAIL_MICROSOFT
                        + ADDRESS_DESC_MICROSOFT,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_MICROSOFT + VALID_PHONE_MICROSOFT + VALID_EMAIL_MICROSOFT
                        + VALID_ADDRESS_MICROSOFT,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_MICROSOFT + EMAIL_DESC_MICROSOFT
                        + ADDRESS_DESC_MICROSOFT + TAG_DESC_SOFTWARE + TAG_DESC_TECH + STATUS_DESC_MICROSOFT,
                Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_MICROSOFT + INVALID_PHONE_DESC + EMAIL_DESC_MICROSOFT
                        + ADDRESS_DESC_MICROSOFT + TAG_DESC_SOFTWARE + TAG_DESC_TECH + STATUS_DESC_MICROSOFT,
                Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT + INVALID_EMAIL_DESC
                        + ADDRESS_DESC_MICROSOFT + TAG_DESC_SOFTWARE + TAG_DESC_TECH + STATUS_DESC_MICROSOFT,
                Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT + EMAIL_DESC_MICROSOFT
                        + INVALID_ADDRESS_DESC + TAG_DESC_SOFTWARE + TAG_DESC_TECH + STATUS_DESC_MICROSOFT,
                Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT + EMAIL_DESC_MICROSOFT
                        + ADDRESS_DESC_MICROSOFT + INVALID_TAG_DESC + VALID_TAG_TECH + STATUS_DESC_MICROSOFT,
                Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_MICROSOFT + EMAIL_DESC_MICROSOFT
                        + INVALID_ADDRESS_DESC + STATUS_DESC_MICROSOFT,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT
                        + EMAIL_DESC_MICROSOFT + ADDRESS_DESC_MICROSOFT + TAG_DESC_SOFTWARE + TAG_DESC_TECH
                        + STATUS_DESC_MICROSOFT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
