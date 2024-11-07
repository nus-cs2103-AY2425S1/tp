package seedu.sellsavvy.logic.parser;

import static seedu.sellsavvy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.EMAIL_DESC_AMY;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.EMAIL_DESC_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.INVALID_NAME_DESC;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.INVALID_PHONE_DESC;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.INVALID_TAG_DESC;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.NAME_DESC_AMY;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.NAME_DESC_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.PHONE_DESC_AMY;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.PHONE_DESC_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.TAG_DESC_FRIEND;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_EMAIL_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_NAME_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_PHONE_BOB;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_TAG_FRIEND;
import static seedu.sellsavvy.logic.commands.customercommands.CustomerCommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.sellsavvy.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.sellsavvy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.sellsavvy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.sellsavvy.testutil.TypicalCustomers.AMY;
import static seedu.sellsavvy.testutil.TypicalCustomers.BOB;

import org.junit.jupiter.api.Test;

import seedu.sellsavvy.logic.Messages;
import seedu.sellsavvy.logic.commands.customercommands.AddCustomerCommand;
import seedu.sellsavvy.model.customer.Address;
import seedu.sellsavvy.model.customer.Customer;
import seedu.sellsavvy.model.customer.Email;
import seedu.sellsavvy.model.customer.Name;
import seedu.sellsavvy.model.customer.Phone;
import seedu.sellsavvy.model.tag.Tag;
import seedu.sellsavvy.testutil.CustomerBuilder;

public class AddCustomerCommandParserTest {
    private AddCustomerCommandParser parser = new AddCustomerCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Customer expectedCustomer = new CustomerBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND, new AddCustomerCommand(expectedCustomer));


        // multiple tags - all accepted
        Customer expectedCustomerMultipleTags = new CustomerBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                        + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCustomerCommand(expectedCustomerMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedCustomerString = NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedCustomerString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, INVALID_ADDRESS_DESC + validExpectedCustomerString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedCustomerString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedCustomerString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedCustomerString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid address
        assertParseFailure(parser, validExpectedCustomerString + INVALID_ADDRESS_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Customer expectedCustomer = new CustomerBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                new AddCustomerCommand(expectedCustomer));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCustomerCommand.MESSAGE_USAGE);

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
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + INVALID_EMAIL_DESC + ADDRESS_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + EMAIL_DESC_BOB + INVALID_ADDRESS_DESC,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCustomerCommand.MESSAGE_USAGE));
    }
}
