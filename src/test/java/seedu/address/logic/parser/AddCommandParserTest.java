package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.CAREER_PAGE_URL_DESC_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.CAREER_PAGE_URL_DESC_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CAREER_PAGE_URL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REMARK_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_COMPANY;
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_VALID;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BIGTECH;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_COMPANY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CAREER_PAGE_URL_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BIGTECH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CAREER_PAGE_URL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK; // New prefix for remark
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCompanies.MICROSOFT;
import static seedu.address.testutil.TypicalCompanies.TESLA;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.company.Address;
import seedu.address.model.company.CareerPageUrl;
import seedu.address.model.company.Company;
import seedu.address.model.company.Email;
import seedu.address.model.company.Name;
import seedu.address.model.company.Phone;
import seedu.address.model.company.Remark; // New import for remark
import seedu.address.model.tag.Tag;
import seedu.address.testutil.CompanyBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    @Disabled
    public void parse_allFieldsPresent_success() {
        Company expectedCompany = new CompanyBuilder(MICROSOFT)
                .withCareerPageUrl(VALID_CAREER_PAGE_URL_MICROSOFT)
                .withTags(VALID_TAG_COMPANY)
                .withRemark(VALID_REMARK)
                .build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT
                + EMAIL_DESC_MICROSOFT + ADDRESS_DESC_MICROSOFT + CAREER_PAGE_URL_DESC_MICROSOFT
                + REMARK_DESC_COMPANY + TAG_DESC_COMPANY, new AddCommand(expectedCompany));

        // multiple tags - all accepted
        Company expectedCompanyMultipleTags = new CompanyBuilder(MICROSOFT)
                .withCareerPageUrl(VALID_CAREER_PAGE_URL_MICROSOFT)
                .withTags(VALID_TAG_COMPANY, VALID_TAG_BIGTECH)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT + EMAIL_DESC_MICROSOFT + ADDRESS_DESC_MICROSOFT
                        + CAREER_PAGE_URL_DESC_MICROSOFT + TAG_DESC_BIGTECH + TAG_DESC_COMPANY,
                new AddCommand(expectedCompanyMultipleTags));
    }

    @Test
    @Disabled
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedCompanyString = NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT
                + EMAIL_DESC_MICROSOFT
                + ADDRESS_DESC_MICROSOFT + CAREER_PAGE_URL_DESC_MICROSOFT + REMARK_DESC_VALID + TAG_DESC_COMPANY;

        assertParseFailure(parser, NAME_DESC_TESLA + validExpectedCompanyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_TESLA + validExpectedCompanyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_TESLA + validExpectedCompanyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_TESLA + validExpectedCompanyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple career page URLs
        assertParseFailure(parser, CAREER_PAGE_URL_DESC_TESLA + validExpectedCompanyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CAREER_PAGE_URL));

        // multiple remarks
        assertParseFailure(parser, REMARK_DESC_VALID + validExpectedCompanyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARK));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedCompanyString + PHONE_DESC_TESLA + EMAIL_DESC_TESLA + NAME_DESC_TESLA
                        + ADDRESS_DESC_TESLA + CAREER_PAGE_URL_DESC_TESLA + validExpectedCompanyString,
                Messages.getErrorMessageForDuplicatePrefixes(
                        PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE, PREFIX_CAREER_PAGE_URL));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedCompanyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedCompanyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedCompanyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedCompanyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid career page URL
        assertParseFailure(parser, INVALID_CAREER_PAGE_URL_DESC + validExpectedCompanyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CAREER_PAGE_URL));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedCompanyString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedCompanyString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedCompanyString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedCompanyString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // invalid career page URL
        assertParseFailure(parser, validExpectedCompanyString + INVALID_CAREER_PAGE_URL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_CAREER_PAGE_URL));
    }

    @Test
    public void parse_optionalFieldsMissing_failure() {
        // Missing required field: CareerPageUrl
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        Company expectedCompany = new CompanyBuilder(TESLA).withTags().build();

        // Missing career page URL
        assertParseFailure(parser, NAME_DESC_TESLA + PHONE_DESC_TESLA + EMAIL_DESC_TESLA + ADDRESS_DESC_TESLA,
                expectedMessage);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_MICROSOFT + PHONE_DESC_MICROSOFT + EMAIL_DESC_MICROSOFT
                        + ADDRESS_DESC_MICROSOFT + CAREER_PAGE_URL_DESC_MICROSOFT,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_MICROSOFT + VALID_PHONE_MICROSOFT + EMAIL_DESC_MICROSOFT
                        + ADDRESS_DESC_MICROSOFT + CAREER_PAGE_URL_DESC_MICROSOFT,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT + VALID_EMAIL_MICROSOFT
                        + ADDRESS_DESC_MICROSOFT + CAREER_PAGE_URL_DESC_MICROSOFT,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT + EMAIL_DESC_MICROSOFT
                        + VALID_ADDRESS_MICROSOFT + CAREER_PAGE_URL_DESC_MICROSOFT,
                expectedMessage);

        // missing career page URL prefix
        assertParseFailure(parser, NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT + EMAIL_DESC_MICROSOFT
                        + ADDRESS_DESC_MICROSOFT + VALID_CAREER_PAGE_URL_MICROSOFT,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_MICROSOFT + VALID_PHONE_MICROSOFT + VALID_EMAIL_MICROSOFT
                        + VALID_ADDRESS_MICROSOFT + VALID_CAREER_PAGE_URL_MICROSOFT,
                expectedMessage);
    }

    @Test
    @Disabled
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_MICROSOFT + EMAIL_DESC_MICROSOFT
                + ADDRESS_DESC_MICROSOFT + CAREER_PAGE_URL_DESC_MICROSOFT
                + REMARK_DESC_VALID + TAG_DESC_BIGTECH + TAG_DESC_COMPANY, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_MICROSOFT + INVALID_PHONE_DESC + EMAIL_DESC_MICROSOFT
                + ADDRESS_DESC_MICROSOFT + CAREER_PAGE_URL_DESC_MICROSOFT
                + REMARK_DESC_VALID + TAG_DESC_BIGTECH + TAG_DESC_COMPANY, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT
                + INVALID_EMAIL_DESC + ADDRESS_DESC_MICROSOFT + CAREER_PAGE_URL_DESC_MICROSOFT
                + REMARK_DESC_VALID + TAG_DESC_BIGTECH + TAG_DESC_COMPANY, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT
                + EMAIL_DESC_MICROSOFT + INVALID_ADDRESS_DESC + CAREER_PAGE_URL_DESC_MICROSOFT
                + REMARK_DESC_VALID + TAG_DESC_BIGTECH + TAG_DESC_COMPANY, Address.MESSAGE_CONSTRAINTS);

        // invalid career page URL
        assertParseFailure(parser, NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT
                + EMAIL_DESC_MICROSOFT + ADDRESS_DESC_MICROSOFT + INVALID_CAREER_PAGE_URL_DESC
                + REMARK_DESC_VALID + TAG_DESC_BIGTECH + TAG_DESC_COMPANY, CareerPageUrl.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT
                + EMAIL_DESC_MICROSOFT + ADDRESS_DESC_MICROSOFT + VALID_CAREER_PAGE_URL_MICROSOFT
                + REMARK_DESC_VALID + INVALID_TAG_DESC + VALID_TAG_COMPANY, Tag.MESSAGE_CONSTRAINTS);

        // invalid remark
        assertParseFailure(parser, NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT
                + EMAIL_DESC_MICROSOFT + ADDRESS_DESC_MICROSOFT + VALID_CAREER_PAGE_URL_MICROSOFT
                + INVALID_REMARK_DESC + TAG_DESC_BIGTECH + TAG_DESC_COMPANY, Remark.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_MICROSOFT
                        + EMAIL_DESC_MICROSOFT + INVALID_ADDRESS_DESC + CAREER_PAGE_URL_DESC_MICROSOFT,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_MICROSOFT
                        + PHONE_DESC_MICROSOFT + EMAIL_DESC_MICROSOFT
                        + ADDRESS_DESC_MICROSOFT + CAREER_PAGE_URL_DESC_MICROSOFT
                        + REMARK_DESC_VALID + TAG_DESC_BIGTECH + TAG_DESC_COMPANY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
