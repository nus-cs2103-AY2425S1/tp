package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalVendors.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CreateCommand;
import seedu.address.logic.commands.CreateEventCommand;
import seedu.address.logic.commands.CreateVendorCommand;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.name.Name;
import seedu.address.model.vendor.Vendor;
import seedu.address.testutil.VendorBuilder;

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
        assertParseSuccess(parser, " " + PREFIX_EVENT + " " + PREFIX_NAME + "Birthday Party" + " "
                        + PREFIX_DATE + "2024-10-10",
                new CreateEventCommand(new Event(new Name("Birthday Party"), new Date("2024-10-10"))));
    }

    @Test
    public void parse_eventPrefixContainsParameterValueWithValidEventParameters_success() {
        assertParseSuccess(parser, " " + PREFIX_EVENT + "Some event parameter" + " " + PREFIX_NAME
                        + "Birthday Party" + " " + PREFIX_DATE + "2024-10-10",
                new CreateEventCommand(new Event(new Name("Birthday Party"), new Date("2024-10-10"))));
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
