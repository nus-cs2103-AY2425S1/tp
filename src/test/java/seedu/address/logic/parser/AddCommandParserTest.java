package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
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
import static seedu.address.logic.commands.CommandTestUtil.REMARK_DESC_VALID;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BIGTECH;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_COMPANY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK; // New prefix for remark
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalCompanies.MICROSOFT;
import static seedu.address.testutil.TypicalCompanies.TESLA;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCommand;
import seedu.address.model.company.Address;
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
    public void parse_allFieldsPresent_success() {
        Company expectedCompany = new CompanyBuilder(MICROSOFT).withTags(VALID_TAG_COMPANY)
                .withRemark(VALID_REMARK).build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT
                        + EMAIL_DESC_MICROSOFT + ADDRESS_DESC_MICROSOFT + REMARK_DESC_VALID + TAG_DESC_COMPANY,
                new AddCommand(expectedCompany));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT
                + EMAIL_DESC_MICROSOFT
                + ADDRESS_DESC_MICROSOFT + REMARK_DESC_VALID + TAG_DESC_COMPANY;

        // multiple names
        assertParseFailure(parser, NAME_DESC_TESLA + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_TESLA + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_TESLA + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_TESLA + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple remarks
        assertParseFailure(parser, REMARK_DESC_VALID + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_REMARK));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags, career page URL, and remark provided
        Company expectedCompany = new CompanyBuilder(TESLA).withTags().withRemark("No remark").build();
        assertParseSuccess(parser, NAME_DESC_TESLA + PHONE_DESC_TESLA + EMAIL_DESC_TESLA + ADDRESS_DESC_TESLA,
                new AddCommand(expectedCompany));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, VALID_NAME_MICROSOFT + PHONE_DESC_MICROSOFT + EMAIL_DESC_MICROSOFT
                        + ADDRESS_DESC_MICROSOFT + REMARK_DESC_VALID,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_MICROSOFT + VALID_PHONE_MICROSOFT + EMAIL_DESC_MICROSOFT
                        + ADDRESS_DESC_MICROSOFT + REMARK_DESC_VALID,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT + VALID_EMAIL_MICROSOFT
                        + ADDRESS_DESC_MICROSOFT + REMARK_DESC_VALID,
                expectedMessage);

        // missing address prefix
        assertParseFailure(parser, NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT + EMAIL_DESC_MICROSOFT
                        + VALID_ADDRESS_MICROSOFT + REMARK_DESC_VALID,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_MICROSOFT + VALID_PHONE_MICROSOFT + VALID_EMAIL_MICROSOFT
                        + VALID_ADDRESS_MICROSOFT + VALID_REMARK,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_MICROSOFT + EMAIL_DESC_MICROSOFT
                + ADDRESS_DESC_MICROSOFT
                + REMARK_DESC_VALID + TAG_DESC_BIGTECH + TAG_DESC_COMPANY, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_MICROSOFT + INVALID_PHONE_DESC + EMAIL_DESC_MICROSOFT
                + ADDRESS_DESC_MICROSOFT
                + REMARK_DESC_VALID + TAG_DESC_BIGTECH + TAG_DESC_COMPANY, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT
                + INVALID_EMAIL_DESC + ADDRESS_DESC_MICROSOFT
                + REMARK_DESC_VALID + TAG_DESC_BIGTECH + TAG_DESC_COMPANY, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT
                + EMAIL_DESC_MICROSOFT + INVALID_ADDRESS_DESC
                + REMARK_DESC_VALID + TAG_DESC_BIGTECH + TAG_DESC_COMPANY, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT
                + EMAIL_DESC_MICROSOFT + ADDRESS_DESC_MICROSOFT
                + REMARK_DESC_VALID + INVALID_TAG_DESC + VALID_TAG_COMPANY, Tag.MESSAGE_CONSTRAINTS);

        // invalid remark
        assertParseFailure(parser, NAME_DESC_MICROSOFT + PHONE_DESC_MICROSOFT
                + EMAIL_DESC_MICROSOFT + ADDRESS_DESC_MICROSOFT
                + INVALID_REMARK_DESC + TAG_DESC_BIGTECH + TAG_DESC_COMPANY, Remark.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_MICROSOFT
                        + EMAIL_DESC_MICROSOFT + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_MICROSOFT
                        + PHONE_DESC_MICROSOFT + EMAIL_DESC_MICROSOFT
                        + ADDRESS_DESC_MICROSOFT + REMARK_DESC_VALID + TAG_DESC_BIGTECH + TAG_DESC_COMPANY,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
