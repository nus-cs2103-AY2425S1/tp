package seedu.ddd.logic.parser.add;

import static seedu.ddd.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ddd.logic.commands.CommandTestUtil.CLIENT_FLAG;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.ddd.logic.parser.CommandParserTestUtil.INVALID_CLIENT_ADDRESS_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.INVALID_CLIENT_EMAIL_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.INVALID_CLIENT_NAME_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.INVALID_CLIENT_PHONE_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.INVALID_NON_EMPTY_PREAMBLE;
import static seedu.ddd.logic.parser.CommandParserTestUtil.INVALID_TAG_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_CLIENT_ADDRESS_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_CLIENT_EMAIL_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_CLIENT_NAME_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_CLIENT_PHONE_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_TAG_ARGUMENT_1;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_TAG_ARGUMENT_2;
import static seedu.ddd.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ddd.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_ADDRESS;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_EMAIL;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_NAME;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_CLIENT_PHONE;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_1;
import static seedu.ddd.testutil.contact.TypicalContactFields.VALID_TAG_2;
import static seedu.ddd.testutil.contact.TypicalContacts.VALID_CLIENT;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.Messages;
import seedu.ddd.logic.commands.add.AddContactCommand;
import seedu.ddd.logic.parser.CommandParserTestUtil;
import seedu.ddd.model.common.Name;
import seedu.ddd.model.common.Tag;
import seedu.ddd.model.contact.client.Client;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.Contact;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.testutil.contact.ClientBuilder;


public class AddClientCommandParserTest {

    private AddClientCommandParser parser = new AddClientCommandParser();

    @Test
    public void parse_clientAllFieldsPresentSingleTag_success() {
        Client expectedClient = new ClientBuilder(VALID_CLIENT)
                .withTags(VALID_TAG_1)
                .build();
        String arguments = CommandParserTestUtil.joinArguments(
            CLIENT_FLAG,
            VALID_CLIENT_NAME_ARGUMENT,
            VALID_CLIENT_PHONE_ARGUMENT,
            VALID_CLIENT_EMAIL_ARGUMENT,
            VALID_CLIENT_ADDRESS_ARGUMENT,
            VALID_TAG_ARGUMENT_1
        );
        assertParseSuccess(parser, arguments, new AddContactCommand(expectedClient));
    }

    @Test
    public void parse_clientAllFieldsPresentMultipleTags_success() {
        Client expectedClient = new ClientBuilder(VALID_CLIENT)
                .withTags(VALID_TAG_1, VALID_TAG_2)
                .build();
        String arguments = CommandParserTestUtil.joinArguments(
            CLIENT_FLAG,
            VALID_CLIENT_NAME_ARGUMENT,
            VALID_CLIENT_PHONE_ARGUMENT,
            VALID_CLIENT_EMAIL_ARGUMENT,
            VALID_CLIENT_ADDRESS_ARGUMENT,
            VALID_TAG_ARGUMENT_1,
            VALID_TAG_ARGUMENT_2
        );
        assertParseSuccess(parser, arguments, new AddContactCommand(expectedClient));
    }

    @Test
    public void parse_clientValidRepeatedNonTagValue_failure() {
        String arguments = CommandParserTestUtil.joinArguments(
            CLIENT_FLAG,
            VALID_CLIENT_NAME_ARGUMENT,
            VALID_CLIENT_PHONE_ARGUMENT,
            VALID_CLIENT_EMAIL_ARGUMENT,
            VALID_CLIENT_ADDRESS_ARGUMENT,
            VALID_TAG_ARGUMENT_1
        );

        // multiple names
        assertParseFailure(parser, arguments + " " + VALID_CLIENT_NAME_ARGUMENT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, arguments + " " + VALID_CLIENT_PHONE_ARGUMENT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, arguments + " " + VALID_CLIENT_EMAIL_ARGUMENT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, arguments + " " + VALID_CLIENT_ADDRESS_ARGUMENT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser, arguments
                + " " + VALID_CLIENT_PHONE_ARGUMENT
                + " " + VALID_CLIENT_EMAIL_ARGUMENT
                + " " + VALID_CLIENT_NAME_ARGUMENT
                + " " + VALID_CLIENT_ADDRESS_ARGUMENT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));
    }


    @Test
    public void parse_clientInvalidRepeatedNonTagValue_failure() {
        String arguments = CommandParserTestUtil.joinArguments(
            CLIENT_FLAG,
            VALID_CLIENT_NAME_ARGUMENT,
            VALID_CLIENT_PHONE_ARGUMENT,
            VALID_CLIENT_EMAIL_ARGUMENT,
            VALID_CLIENT_ADDRESS_ARGUMENT,
            VALID_TAG_ARGUMENT_1
        );

        // valid value followed by invalid value

        // multiple names
        assertParseFailure(parser, arguments + " " + INVALID_CLIENT_NAME_ARGUMENT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, arguments + " " + INVALID_CLIENT_PHONE_ARGUMENT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, arguments + " " + INVALID_CLIENT_EMAIL_ARGUMENT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, arguments + " " + INVALID_CLIENT_ADDRESS_ARGUMENT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser, arguments
                + " " + INVALID_CLIENT_PHONE_ARGUMENT
                + " " + INVALID_CLIENT_EMAIL_ARGUMENT
                + " " + INVALID_CLIENT_NAME_ARGUMENT
                + " " + INVALID_CLIENT_ADDRESS_ARGUMENT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));

        // invalid value followed by invalid value

        // multiple names
        assertParseFailure(parser, " " + INVALID_CLIENT_NAME_ARGUMENT + arguments,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, " " + INVALID_CLIENT_PHONE_ARGUMENT + arguments,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, " " + INVALID_CLIENT_EMAIL_ARGUMENT + arguments,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple addresses
        assertParseFailure(parser, " " + INVALID_CLIENT_ADDRESS_ARGUMENT + arguments,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_ADDRESS));

        // multiple fields repeated
        assertParseFailure(parser, " " + INVALID_CLIENT_PHONE_ARGUMENT
                + " " + INVALID_CLIENT_EMAIL_ARGUMENT
                + " " + INVALID_CLIENT_NAME_ARGUMENT
                + " " + INVALID_CLIENT_ADDRESS_ARGUMENT
                + arguments,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_ADDRESS, PREFIX_EMAIL, PREFIX_PHONE));
    }

    @Test
    public void parse_clientOptionalFieldsMissing_success() {
        // zero tags
        Contact expectedClient = new ClientBuilder(VALID_CLIENT)
                .withTags()
                .build();

        String arguments = CommandParserTestUtil.joinArguments(
            CLIENT_FLAG,
            VALID_CLIENT_NAME_ARGUMENT,
            VALID_CLIENT_PHONE_ARGUMENT,
            VALID_CLIENT_EMAIL_ARGUMENT,
            VALID_CLIENT_ADDRESS_ARGUMENT
        );
        assertParseSuccess(parser, arguments, new AddContactCommand(expectedClient));
    }

    @Test
    public void parse_clientCompulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.CLIENT_MESSAGE_USAGE);

        // missing name prefix
        String arguments = CommandParserTestUtil.joinArguments(
            CLIENT_FLAG,
            VALID_CLIENT_PHONE_ARGUMENT,
            VALID_CLIENT_EMAIL_ARGUMENT,
            VALID_CLIENT_ADDRESS_ARGUMENT
        );
        assertParseFailure(parser, arguments, expectedMessage);

        // missing phone prefix
        arguments = CommandParserTestUtil.joinArguments(
            CLIENT_FLAG,
            VALID_CLIENT_NAME_ARGUMENT,
            VALID_CLIENT_EMAIL_ARGUMENT,
            VALID_CLIENT_ADDRESS_ARGUMENT
        );
        assertParseFailure(parser, arguments, expectedMessage);

        // missing email prefix
        arguments = CommandParserTestUtil.joinArguments(
            CLIENT_FLAG,
            VALID_CLIENT_NAME_ARGUMENT,
            VALID_CLIENT_PHONE_ARGUMENT,
            VALID_CLIENT_ADDRESS_ARGUMENT
        );
        assertParseFailure(parser, arguments, expectedMessage);

        // missing address prefix
        arguments = CommandParserTestUtil.joinArguments(
            CLIENT_FLAG,
            VALID_CLIENT_NAME_ARGUMENT,
            VALID_CLIENT_PHONE_ARGUMENT,
            VALID_CLIENT_EMAIL_ARGUMENT
        );
        assertParseFailure(parser, arguments, expectedMessage);

        // all prefixes missing
        arguments = CommandParserTestUtil.joinArguments(
            CLIENT_FLAG,
            VALID_CLIENT_NAME,
            VALID_CLIENT_PHONE,
            VALID_CLIENT_EMAIL,
            VALID_CLIENT_ADDRESS
        );
        assertParseFailure(parser, arguments, expectedMessage);
    }

    @Test
    public void parse_clientInvalidValue_failure() {
        // invalid name
        String arguments = CommandParserTestUtil.joinArguments(
            CLIENT_FLAG,
            INVALID_CLIENT_NAME_ARGUMENT,
            VALID_CLIENT_PHONE_ARGUMENT,
            VALID_CLIENT_EMAIL_ARGUMENT,
            VALID_CLIENT_ADDRESS_ARGUMENT
        );
        assertParseFailure(parser, arguments, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        arguments = CommandParserTestUtil.joinArguments(
            CLIENT_FLAG,
            VALID_CLIENT_NAME_ARGUMENT,
            INVALID_CLIENT_PHONE_ARGUMENT,
            VALID_CLIENT_EMAIL_ARGUMENT,
            VALID_CLIENT_ADDRESS_ARGUMENT
        );
        assertParseFailure(parser, arguments, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        arguments = CommandParserTestUtil.joinArguments(
            CLIENT_FLAG,
            VALID_CLIENT_NAME_ARGUMENT,
            VALID_CLIENT_PHONE_ARGUMENT,
            INVALID_CLIENT_EMAIL_ARGUMENT,
            VALID_CLIENT_ADDRESS_ARGUMENT
        );
        assertParseFailure(parser, arguments, Email.MESSAGE_CONSTRAINTS);

        // invalid address
        arguments = CommandParserTestUtil.joinArguments(
            CLIENT_FLAG,
            VALID_CLIENT_NAME_ARGUMENT,
            VALID_CLIENT_PHONE_ARGUMENT,
            VALID_CLIENT_EMAIL_ARGUMENT,
            INVALID_CLIENT_ADDRESS_ARGUMENT
        );
        assertParseFailure(parser, arguments, Address.MESSAGE_CONSTRAINTS);

        // invalid tag
        arguments = CommandParserTestUtil.joinArguments(
            CLIENT_FLAG,
            VALID_CLIENT_NAME_ARGUMENT,
            VALID_CLIENT_PHONE_ARGUMENT,
            VALID_CLIENT_EMAIL_ARGUMENT,
            VALID_CLIENT_ADDRESS_ARGUMENT,
            INVALID_TAG_ARGUMENT
        );
        assertParseFailure(parser, arguments, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        arguments = CommandParserTestUtil.joinArguments(
            CLIENT_FLAG,
            INVALID_CLIENT_NAME_ARGUMENT,
            VALID_CLIENT_PHONE_ARGUMENT,
            VALID_CLIENT_EMAIL_ARGUMENT,
            INVALID_CLIENT_ADDRESS_ARGUMENT
        );
        assertParseFailure(parser, arguments, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        arguments = CommandParserTestUtil.joinArguments(
            INVALID_NON_EMPTY_PREAMBLE,
            CLIENT_FLAG,
            VALID_CLIENT_NAME_ARGUMENT,
            VALID_CLIENT_PHONE_ARGUMENT,
            VALID_CLIENT_EMAIL_ARGUMENT,
            VALID_CLIENT_ADDRESS_ARGUMENT,
            VALID_TAG_ARGUMENT_1,
            VALID_TAG_ARGUMENT_2
        );
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddContactCommand.CLIENT_MESSAGE_USAGE);
        assertParseFailure(parser, arguments, expectedMessage);
    }

}
