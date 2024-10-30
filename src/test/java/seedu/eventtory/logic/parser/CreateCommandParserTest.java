package seedu.eventtory.logic.parser;

import static seedu.eventtory.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventtory.logic.commands.CommandTestUtil.DATE_DESC_BIRTHDAY;
import static seedu.eventtory.logic.commands.CommandTestUtil.DATE_DESC_WEDDING;
import static seedu.eventtory.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BOB;
import static seedu.eventtory.logic.commands.CommandTestUtil.NAME_DESC_BIRTHDAY;
import static seedu.eventtory.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.eventtory.logic.commands.CommandTestUtil.NAME_DESC_WEDDING;
import static seedu.eventtory.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.eventtory.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.eventtory.logic.commands.CommandTestUtil.TAG_DESC_CHARITY;
import static seedu.eventtory.logic.commands.CommandTestUtil.TAG_DESC_CONFERENCE;
import static seedu.eventtory.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_TAG_CHARITY;
import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_TAG_CONFERENCE;
import static seedu.eventtory.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_VENDOR;
import static seedu.eventtory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.eventtory.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.eventtory.testutil.TypicalEvents.BIRTHDAY;
import static seedu.eventtory.testutil.TypicalEvents.WEDDING;
import static seedu.eventtory.testutil.TypicalVendors.BOB;

import org.junit.jupiter.api.Test;

import seedu.eventtory.logic.commands.CreateCommand;
import seedu.eventtory.logic.commands.CreateEventCommand;
import seedu.eventtory.logic.commands.CreateVendorCommand;
import seedu.eventtory.model.event.Event;
import seedu.eventtory.model.vendor.Vendor;
import seedu.eventtory.testutil.EventBuilder;
import seedu.eventtory.testutil.VendorBuilder;

public class CreateCommandParserTest {
    private CreateCommandParser parser = new CreateCommandParser();

    @Test
    public void parse_noPrefixSpecified_throwsParseException() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_eventAndVendorPrefixesSpecified_throwsParseException() {
        assertParseFailure(parser, " v/ e/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_eventPrefixSpecifiedWithNoEventParameters_throwsParseException() {
        assertParseFailure(parser, " e/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_eventPrefixSpecifiedWithValidEventParameters_success() {
        Event expectedEvent = new EventBuilder(BIRTHDAY).withTags(VALID_TAG_CONFERENCE).build();
        assertParseSuccess(parser, " " + PREFIX_EVENT + NAME_DESC_BIRTHDAY + DATE_DESC_BIRTHDAY
                        + TAG_DESC_CONFERENCE,
                new CreateEventCommand(expectedEvent));
    }

    @Test
    public void parse_eventPrefixContainsParameterValueWithValidEventParameters_success() {
        Event expectedEvent = new EventBuilder(WEDDING).withTags(VALID_TAG_CHARITY).build();
        assertParseSuccess(parser, " " + PREFIX_EVENT + "Some event parameter" + NAME_DESC_WEDDING
                        + DATE_DESC_WEDDING + TAG_DESC_CHARITY,
                new CreateEventCommand(expectedEvent));
    }

    @Test
    public void parse_vendorPrefixSpecifiedWithNoVendorParameters_throwsParseException() {
        assertParseFailure(parser, " v/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateVendorCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_vendorPrefixSpecifiedWithValidVendorParameters_success() {
        Vendor expectedVendor = new VendorBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " " + PREFIX_VENDOR + NAME_DESC_BOB + PHONE_DESC_BOB
                        + DESCRIPTION_DESC_BOB + TAG_DESC_FRIEND,
                new CreateVendorCommand(expectedVendor));
    }

    @Test
    public void parse_vendorPrefixContainsParameterValueWithValidVendorParameters_success() {
        Vendor expectedVendor = new VendorBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " " + PREFIX_VENDOR + "Some event parameter"
                        + NAME_DESC_BOB + PHONE_DESC_BOB + DESCRIPTION_DESC_BOB + TAG_DESC_FRIEND,
                new CreateVendorCommand(expectedVendor));
    }

}
