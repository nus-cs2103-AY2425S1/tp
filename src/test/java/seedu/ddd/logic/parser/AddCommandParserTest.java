package seedu.ddd.logic.parser;

import static seedu.ddd.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.DATE_DESC_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.ddd.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.ddd.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.ddd.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.ddd.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.ddd.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.ddd.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.ddd.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.ddd.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ddd.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ddd.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ddd.testutil.TypicalContacts.AMY;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.Messages;
import seedu.ddd.logic.commands.AddCommand;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.testutil.ClientBuilder;

public class AddCommandParserTest {

    private AddContactCommandParser parser = new AddContactCommandParser();

    @Test
    public void parse_clientAllFieldsPresent_success() {

        Client expectedClient = new ClientBuilder(AMY).build();
        assertParseSuccess(parser, " client" + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY + TAG_DESC_FRIEND, new AddCommand(expectedClient));


        // multiple tags - all accepted
        Contact expectedPersonMultipleTags = new ClientBuilder(AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, " client" + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                        + ADDRESS_DESC_AMY + DATE_DESC_AMY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedContactString = NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + DATE_DESC_AMY + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, " client" + NAME_DESC_AMY + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, ADDRESS_DESC_AMY + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedContactString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY + ADDRESS_DESC_AMY
                        + validExpectedContactString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));
    }
}
