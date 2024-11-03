package seedu.ddd.logic.parser.add;

import static seedu.ddd.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ddd.logic.commands.CommandTestUtil.EVENT_FLAG;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_DESC;
import static seedu.ddd.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.ddd.logic.parser.CommandParserTestUtil.INVALID_EVENT_CLIENT_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.INVALID_EVENT_DATE_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.INVALID_EVENT_DESC_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.INVALID_EVENT_NAME_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.INVALID_EVENT_VENDOR_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.INVALID_NON_EMPTY_PREAMBLE;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_EVENT_CLIENT_ID_ARGUMENT_FOUR;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_EVENT_CLIENT_ID_ARGUMENT_ONE;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_EVENT_CLIENT_ID_ARGUMENT_THREE;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_EVENT_CLIENT_ID_ARGUMENT_TWO;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_EVENT_DATE_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_EVENT_DESC_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_EVENT_NAME_ARGUMENT;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_EVENT_VENDOR_ID_ARGUMENT_FOUR;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_EVENT_VENDOR_ID_ARGUMENT_ONE;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_EVENT_VENDOR_ID_ARGUMENT_THREE;
import static seedu.ddd.logic.parser.CommandParserTestUtil.VALID_EVENT_VENDOR_ID_ARGUMENT_TWO;
import static seedu.ddd.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.ddd.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_CLIENT_CONTACT_ID_SET_MULTIPLE;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_CLIENT_CONTACT_ID_SET_SINGLE;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_DATE;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_DESCRIPTION;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_ID;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_NAME;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_VENDOR_CONTACT_ID_SET_MULTIPLE;
import static seedu.ddd.testutil.event.TypicalEventFields.DEFAULT_EVENT_VENDOR_CONTACT_ID_SET_SINGLE;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_DATE;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_DESCRIPTION_1;
import static seedu.ddd.testutil.event.TypicalEventFields.VALID_EVENT_NAME_1;

import org.junit.jupiter.api.Test;

import seedu.ddd.logic.Messages;
import seedu.ddd.logic.commands.add.AddEventCommand;
import seedu.ddd.logic.parser.CommandParserTestUtil;
import seedu.ddd.model.common.Id;
import seedu.ddd.model.common.Name;
import seedu.ddd.model.event.common.Date;
import seedu.ddd.model.event.common.Description;


public class AddEventCommandParserTest {

    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_eventAllFieldsPresent_success() {
        String arguments = CommandParserTestUtil.joinArguments(
            EVENT_FLAG, VALID_EVENT_NAME_ARGUMENT, VALID_EVENT_DESC_ARGUMENT, VALID_EVENT_DATE_ARGUMENT,
                VALID_EVENT_CLIENT_ID_ARGUMENT_ONE, VALID_EVENT_VENDOR_ID_ARGUMENT_ONE);
        assertParseSuccess(parser, arguments, new AddEventCommand(DEFAULT_EVENT_NAME, DEFAULT_EVENT_DESCRIPTION,
                DEFAULT_EVENT_DATE, DEFAULT_EVENT_CLIENT_CONTACT_ID_SET_SINGLE,
                DEFAULT_EVENT_VENDOR_CONTACT_ID_SET_SINGLE, DEFAULT_EVENT_ID));
    }

    @Test
    public void parse_eventAllFieldsPresentMultipleClients_success() {
        String arguments = CommandParserTestUtil.joinArguments(
                EVENT_FLAG, VALID_EVENT_NAME_ARGUMENT, VALID_EVENT_DESC_ARGUMENT, VALID_EVENT_DATE_ARGUMENT,
                VALID_EVENT_CLIENT_ID_ARGUMENT_ONE, VALID_EVENT_CLIENT_ID_ARGUMENT_TWO,
                VALID_EVENT_CLIENT_ID_ARGUMENT_THREE, VALID_EVENT_CLIENT_ID_ARGUMENT_FOUR,
                VALID_EVENT_VENDOR_ID_ARGUMENT_ONE);
        assertParseSuccess(parser, arguments, new AddEventCommand(DEFAULT_EVENT_NAME, DEFAULT_EVENT_DESCRIPTION,
                DEFAULT_EVENT_DATE, DEFAULT_EVENT_CLIENT_CONTACT_ID_SET_MULTIPLE,
                DEFAULT_EVENT_VENDOR_CONTACT_ID_SET_SINGLE, DEFAULT_EVENT_ID));
    }

    @Test
    public void parse_eventAllFieldsPresentMultipleVendors_success() {
        String arguments = CommandParserTestUtil.joinArguments(
                EVENT_FLAG, VALID_EVENT_NAME_ARGUMENT, VALID_EVENT_DESC_ARGUMENT, VALID_EVENT_DATE_ARGUMENT,
                VALID_EVENT_VENDOR_ID_ARGUMENT_ONE, VALID_EVENT_VENDOR_ID_ARGUMENT_TWO,
                VALID_EVENT_VENDOR_ID_ARGUMENT_THREE, VALID_EVENT_VENDOR_ID_ARGUMENT_FOUR,
                VALID_EVENT_CLIENT_ID_ARGUMENT_ONE);
        assertParseSuccess(parser, arguments, new AddEventCommand(DEFAULT_EVENT_NAME, DEFAULT_EVENT_DESCRIPTION,
                DEFAULT_EVENT_DATE, DEFAULT_EVENT_CLIENT_CONTACT_ID_SET_SINGLE,
                DEFAULT_EVENT_VENDOR_CONTACT_ID_SET_MULTIPLE, DEFAULT_EVENT_ID));
    }

    @Test
    public void parse_eventAllFieldsPresentMultipleVendorsMultipleClients_success() {
        String arguments = CommandParserTestUtil.joinArguments(
                EVENT_FLAG, VALID_EVENT_NAME_ARGUMENT, VALID_EVENT_DESC_ARGUMENT, VALID_EVENT_DATE_ARGUMENT,
                VALID_EVENT_VENDOR_ID_ARGUMENT_ONE, VALID_EVENT_VENDOR_ID_ARGUMENT_TWO,
                VALID_EVENT_VENDOR_ID_ARGUMENT_THREE, VALID_EVENT_VENDOR_ID_ARGUMENT_FOUR,
                VALID_EVENT_CLIENT_ID_ARGUMENT_ONE, VALID_EVENT_CLIENT_ID_ARGUMENT_TWO,
                VALID_EVENT_CLIENT_ID_ARGUMENT_THREE, VALID_EVENT_CLIENT_ID_ARGUMENT_FOUR);
        assertParseSuccess(parser, arguments, new AddEventCommand(DEFAULT_EVENT_NAME, DEFAULT_EVENT_DESCRIPTION,
                DEFAULT_EVENT_DATE, DEFAULT_EVENT_CLIENT_CONTACT_ID_SET_MULTIPLE,
                DEFAULT_EVENT_VENDOR_CONTACT_ID_SET_MULTIPLE, DEFAULT_EVENT_ID));
    }

    @Test
    public void parse_vendorValidRepeatedNonContactValues_failure() {
        String arguments = CommandParserTestUtil.joinArguments(
                EVENT_FLAG, VALID_EVENT_NAME_ARGUMENT, VALID_EVENT_DESC_ARGUMENT, VALID_EVENT_DATE_ARGUMENT,
                VALID_EVENT_CLIENT_ID_ARGUMENT_ONE, VALID_EVENT_VENDOR_ID_ARGUMENT_ONE);

        // multiple names
        assertParseFailure(parser, arguments + " " + VALID_EVENT_NAME_ARGUMENT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple descriptions
        assertParseFailure(parser, arguments + " " + VALID_EVENT_DESC_ARGUMENT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESC));

        // multiple dates
        assertParseFailure(parser, arguments + " " + VALID_EVENT_DATE_ARGUMENT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple fields repeated
        assertParseFailure(parser, arguments
                + " " + VALID_EVENT_NAME_ARGUMENT
                + " " + VALID_EVENT_DESC_ARGUMENT
                + " " + VALID_EVENT_DATE_ARGUMENT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_DESC, PREFIX_DATE));
    }

    @Test
    public void parse_vendorInvalidRepeatedNonContactValues_failure() {
        String arguments = CommandParserTestUtil.joinArguments(
                EVENT_FLAG, VALID_EVENT_NAME_ARGUMENT, VALID_EVENT_DESC_ARGUMENT, VALID_EVENT_DATE_ARGUMENT,
                VALID_EVENT_CLIENT_ID_ARGUMENT_ONE, VALID_EVENT_VENDOR_ID_ARGUMENT_ONE);

        // invalid value followed by valid value

        // multiple names
        assertParseFailure(parser, arguments + " " + INVALID_EVENT_NAME_ARGUMENT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple descriptions
        assertParseFailure(parser, arguments + " " + INVALID_EVENT_DESC_ARGUMENT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESC));

        // multiple dates
        assertParseFailure(parser, arguments + " " + INVALID_EVENT_DATE_ARGUMENT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple fields repeated
        assertParseFailure(parser, arguments
                + " " + INVALID_EVENT_NAME_ARGUMENT
                + " " + INVALID_EVENT_DESC_ARGUMENT
                + " " + INVALID_EVENT_DATE_ARGUMENT,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_DESC, PREFIX_DATE));

        // valid value followed by invalid value

        // multiple names
        assertParseFailure(parser, " " + INVALID_EVENT_NAME_ARGUMENT + arguments,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple descriptions
        assertParseFailure(parser, " " + INVALID_EVENT_DESC_ARGUMENT + arguments,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DESC));

        // multiple dates

        assertParseFailure(parser, " " + INVALID_EVENT_DATE_ARGUMENT + arguments,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple fields repeated
        assertParseFailure(parser,
                " " + INVALID_EVENT_NAME_ARGUMENT
                + " " + INVALID_EVENT_DESC_ARGUMENT
                + " " + INVALID_EVENT_DATE_ARGUMENT
                + arguments,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_DESC, PREFIX_DATE));
    }

    @Test
    public void parse_eventCompulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing name prefix
        String arguments = CommandParserTestUtil.joinArguments(
                EVENT_FLAG, VALID_EVENT_DESC_ARGUMENT, VALID_EVENT_DATE_ARGUMENT,
                VALID_EVENT_CLIENT_ID_ARGUMENT_ONE, VALID_EVENT_VENDOR_ID_ARGUMENT_ONE);
        assertParseFailure(parser, arguments, expectedMessage);

        // missing description prefix
        arguments = CommandParserTestUtil.joinArguments(
                EVENT_FLAG, VALID_EVENT_NAME_ARGUMENT, VALID_EVENT_DATE_ARGUMENT,
                VALID_EVENT_CLIENT_ID_ARGUMENT_ONE, VALID_EVENT_VENDOR_ID_ARGUMENT_ONE);
        assertParseFailure(parser, arguments, expectedMessage);

        // missing date prefix
        arguments = CommandParserTestUtil.joinArguments(
                EVENT_FLAG, VALID_EVENT_NAME_ARGUMENT, VALID_EVENT_DESC_ARGUMENT,
                VALID_EVENT_CLIENT_ID_ARGUMENT_ONE, VALID_EVENT_VENDOR_ID_ARGUMENT_ONE);
        assertParseFailure(parser, arguments, expectedMessage);

        // missing client prefix
        arguments = CommandParserTestUtil.joinArguments(
                EVENT_FLAG, VALID_EVENT_NAME_ARGUMENT, VALID_EVENT_DESC_ARGUMENT,
                VALID_EVENT_DATE_ARGUMENT, VALID_EVENT_VENDOR_ID_ARGUMENT_ONE);
        assertParseFailure(parser, arguments, expectedMessage);

        // missing vendor prefix
        arguments = CommandParserTestUtil.joinArguments(
                EVENT_FLAG, VALID_EVENT_NAME_ARGUMENT, VALID_EVENT_DESC_ARGUMENT,
                VALID_EVENT_DATE_ARGUMENT, VALID_EVENT_CLIENT_ID_ARGUMENT_ONE);
        assertParseFailure(parser, arguments, expectedMessage);

        // all prefixes missing
        arguments = CommandParserTestUtil.joinArguments(
            EVENT_FLAG, VALID_EVENT_NAME_1, VALID_EVENT_DESCRIPTION_1, VALID_EVENT_DATE);
        assertParseFailure(parser, arguments, expectedMessage);
    }

    @Test
    public void parse_eventInvalidValue_failure() {
        // invalid name
        String arguments = CommandParserTestUtil.joinArguments(
                EVENT_FLAG, INVALID_EVENT_NAME_ARGUMENT, VALID_EVENT_DESC_ARGUMENT, VALID_EVENT_DATE_ARGUMENT,
                VALID_EVENT_CLIENT_ID_ARGUMENT_ONE, VALID_EVENT_VENDOR_ID_ARGUMENT_ONE);
        assertParseFailure(parser, arguments, Name.MESSAGE_CONSTRAINTS);

        // invalid description
        arguments = CommandParserTestUtil.joinArguments(
                EVENT_FLAG, VALID_EVENT_NAME_ARGUMENT, INVALID_EVENT_DESC_ARGUMENT, VALID_EVENT_DATE_ARGUMENT,
                VALID_EVENT_CLIENT_ID_ARGUMENT_ONE, VALID_EVENT_VENDOR_ID_ARGUMENT_ONE);
        assertParseFailure(parser, arguments, Description.MESSAGE_CONSTRAINTS);

        // invalid date
        arguments = CommandParserTestUtil.joinArguments(
                EVENT_FLAG, VALID_EVENT_NAME_ARGUMENT, VALID_EVENT_DESC_ARGUMENT, INVALID_EVENT_DATE_ARGUMENT,
                VALID_EVENT_CLIENT_ID_ARGUMENT_ONE, VALID_EVENT_VENDOR_ID_ARGUMENT_ONE);
        assertParseFailure(parser, arguments, Date.MESSAGE_CONSTRAINTS);

        // invalid client
        arguments = CommandParserTestUtil.joinArguments(
                EVENT_FLAG, VALID_EVENT_NAME_ARGUMENT, VALID_EVENT_DESC_ARGUMENT, VALID_EVENT_DATE_ARGUMENT,
                INVALID_EVENT_CLIENT_ARGUMENT, VALID_EVENT_VENDOR_ID_ARGUMENT_ONE);
        assertParseFailure(parser, arguments, Id.MESSAGE_CONSTRAINTS);

        // invalid vendor
        arguments = CommandParserTestUtil.joinArguments(
                EVENT_FLAG, VALID_EVENT_NAME_ARGUMENT, VALID_EVENT_DESC_ARGUMENT, VALID_EVENT_DATE_ARGUMENT,
                VALID_EVENT_CLIENT_ID_ARGUMENT_ONE, INVALID_EVENT_VENDOR_ARGUMENT);
        assertParseFailure(parser, arguments, Id.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        arguments = CommandParserTestUtil.joinArguments(
                EVENT_FLAG, INVALID_EVENT_NAME_ARGUMENT, VALID_EVENT_DESC_ARGUMENT, INVALID_EVENT_DATE_ARGUMENT,
                VALID_EVENT_CLIENT_ID_ARGUMENT_ONE, VALID_EVENT_VENDOR_ID_ARGUMENT_ONE);
        assertParseFailure(parser, arguments, Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        arguments = CommandParserTestUtil.joinArguments(
            INVALID_NON_EMPTY_PREAMBLE, EVENT_FLAG, INVALID_EVENT_NAME_ARGUMENT, VALID_EVENT_DESC_ARGUMENT,
                INVALID_EVENT_DATE_ARGUMENT, VALID_EVENT_CLIENT_ID_ARGUMENT_ONE, VALID_EVENT_VENDOR_ID_ARGUMENT_ONE);
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);
        assertParseFailure(parser, arguments, expectedMessage);
    }

}
