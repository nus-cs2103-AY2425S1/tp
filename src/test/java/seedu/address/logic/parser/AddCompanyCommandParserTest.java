package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_ADDRESS_DESC_NUS;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_BILLING_DATE_DESC_NUS;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_NAME_DESC_NUS;
import static seedu.address.logic.commands.CommandTestUtil.COMPANY_PHONE_DESC_NUS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_BILLING_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_COMPANY_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BILLING_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.AddCompanyCommand;
import seedu.address.model.common.Address;
import seedu.address.model.common.Name;
import seedu.address.model.company.BillingDate;
import seedu.address.model.company.Company;
import seedu.address.model.person.Phone;
import seedu.address.testutil.CompanyBuilder;
public class AddCompanyCommandParserTest {
    private AddCompanyCommandParser parser = new AddCompanyCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Company expectedCompany = new CompanyBuilder().build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + COMPANY_NAME_DESC_NUS
                + COMPANY_ADDRESS_DESC_NUS + COMPANY_BILLING_DATE_DESC_NUS
                + COMPANY_PHONE_DESC_NUS, new AddCompanyCommand(expectedCompany));
    }

    @Test
    public void parse_repeatedValues_failure() {
        String validExpectedCompanyString = COMPANY_NAME_DESC_NUS + COMPANY_ADDRESS_DESC_NUS
                + COMPANY_BILLING_DATE_DESC_NUS + COMPANY_PHONE_DESC_NUS;

        // repeated name
        assertParseFailure(parser, COMPANY_NAME_DESC_NUS + validExpectedCompanyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // repeated address
        assertParseFailure(parser, COMPANY_ADDRESS_DESC_NUS + validExpectedCompanyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // repeated billing date
        assertParseFailure(parser, COMPANY_BILLING_DATE_DESC_NUS + validExpectedCompanyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_BILLING_DATE));

        // repeated phone
        assertParseFailure(parser, COMPANY_PHONE_DESC_NUS + validExpectedCompanyString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
    }

    @Test
    public void parse_missingValues_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCompanyCommand.MESSAGE_USAGE);

        // missing name
        assertParseFailure(parser, COMPANY_ADDRESS_DESC_NUS + COMPANY_BILLING_DATE_DESC_NUS
                + COMPANY_PHONE_DESC_NUS, expectedMessage);

        // missing address
        assertParseFailure(parser, COMPANY_NAME_DESC_NUS + COMPANY_BILLING_DATE_DESC_NUS
                + COMPANY_PHONE_DESC_NUS, expectedMessage);

        // missing billing date
        assertParseFailure(parser, COMPANY_NAME_DESC_NUS + COMPANY_ADDRESS_DESC_NUS
                + COMPANY_PHONE_DESC_NUS, expectedMessage);

        // missing phone
        assertParseFailure(parser, COMPANY_NAME_DESC_NUS + COMPANY_ADDRESS_DESC_NUS
                + COMPANY_BILLING_DATE_DESC_NUS, expectedMessage);
    }

    @Test
    public void parse_invalidValues_failure() {
        // invalid name
        assertParseFailure(parser, INVALID_COMPANY_NAME_DESC + COMPANY_ADDRESS_DESC_NUS
                + COMPANY_BILLING_DATE_DESC_NUS + COMPANY_PHONE_DESC_NUS, Name.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, COMPANY_NAME_DESC_NUS + INVALID_COMPANY_ADDRESS_DESC
                + COMPANY_BILLING_DATE_DESC_NUS + COMPANY_PHONE_DESC_NUS, Address.MESSAGE_CONSTRAINTS);

        // invalid billing date
        assertParseFailure(parser, COMPANY_NAME_DESC_NUS + COMPANY_ADDRESS_DESC_NUS
                + INVALID_COMPANY_BILLING_DATE_DESC + COMPANY_PHONE_DESC_NUS, BillingDate.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, COMPANY_NAME_DESC_NUS + COMPANY_ADDRESS_DESC_NUS
                + COMPANY_BILLING_DATE_DESC_NUS + INVALID_COMPANY_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);
    }
}
