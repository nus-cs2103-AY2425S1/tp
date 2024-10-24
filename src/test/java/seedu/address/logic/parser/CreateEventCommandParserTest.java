package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CHARITY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_CONFERENCE;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CHARITY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_CONFERENCE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_WEDDING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalEvents.BIRTHDAY;
import static seedu.address.testutil.TypicalEvents.WEDDING;
import static seedu.address.testutil.TypicalVendors.BOB;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CreateEventCommand;
import seedu.address.logic.commands.CreateVendorCommand;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.Name;
import seedu.address.model.vendor.Vendor;
import seedu.address.testutil.EventBuilder;
import seedu.address.testutil.VendorBuilder;


public class CreateEventCommandParserTest {

    private CreateEventCommandParser parser = new CreateEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder(WEDDING).withTags(VALID_TAG_WEDDING).build();

        // whitespace only preamble
        assertParseSuccess(parser,
                PREAMBLE_WHITESPACE + " " + PREFIX_EVENT + NAME_DESC_WEDDING + DATE_DESC_WEDDING
                        + TAG_DESC_WEDDING,
                new CreateEventCommand(expectedEvent));

        // multiple tags - all accepted
        Event expectedEventMultipleTags = new EventBuilder(BIRTHDAY).withTags(VALID_TAG_CONFERENCE, VALID_TAG_CHARITY)
                .build();
        assertParseSuccess(parser,
                " " + PREFIX_EVENT + NAME_DESC_BIRTHDAY + DATE_DESC_BIRTHDAY + TAG_DESC_CONFERENCE
                        + TAG_DESC_CHARITY,
                new CreateEventCommand(expectedEventMultipleTags));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateEventCommand.MESSAGE_USAGE);

        // missing event prefix
        assertParseFailure(parser, NAME_DESC_WEDDING + DATE_DESC_WEDDING,
                expectedMessage);

        // missing name prefix
        assertParseFailure(parser, " " + PREFIX_EVENT + " " + VALID_NAME_WEDDING + DATE_DESC_WEDDING,
                expectedMessage);

        // missing date prefix
        assertParseFailure(parser, " " + PREFIX_EVENT + NAME_DESC_WEDDING + " " + VALID_DATE_WEDDING,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, " " + PREFIX_EVENT + " " + VALID_NAME_BIRTHDAY + " " + VALID_DATE_BIRTHDAY,
                expectedMessage);
    }

    @Test
    public void parse_ignoresEventPrefixValue_success() {
        Event expectedEvent = new EventBuilder(WEDDING).withTags(VALID_TAG_WEDDING).build();

        // event prefix contains parameter value
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " " + PREFIX_EVENT + "some parameter" + " "
                        + NAME_DESC_WEDDING + DATE_DESC_WEDDING + TAG_DESC_WEDDING,
                new CreateEventCommand(expectedEvent));

        // event prefix contains no parameter value
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + " " + PREFIX_EVENT + NAME_DESC_WEDDING
                        + DATE_DESC_WEDDING + TAG_DESC_WEDDING,
                new CreateEventCommand(expectedEvent));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, " " + PREFIX_EVENT + INVALID_NAME_DESC + DATE_DESC_BIRTHDAY,
                Name.MESSAGE_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, " " + PREFIX_EVENT + " " + PREFIX_NAME + "Zoo Excursion" + " "
                        + PREFIX_DATE + "10 October 2024",
                Date.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, " " + PREFIX_EVENT + " " + PREFIX_NAME + "Zoo Excursion?" + " "
                        + PREFIX_DATE + "10 October 2024",
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser,
                PREAMBLE_NON_EMPTY + " " + PREFIX_EVENT + PREFIX_NAME + "Zoo Excursion" + " " + PREFIX_DATE
                        + "10 October 2024",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CreateEventCommand.MESSAGE_USAGE));
    }
}
