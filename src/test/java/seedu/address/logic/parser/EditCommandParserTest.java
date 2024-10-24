package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_WEDDING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_VENDOR;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_VENDOR;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_VENDOR;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.logic.commands.EditVendorCommand;
import seedu.address.logic.commands.EditVendorCommand.EditVendorDescriptor;
//import seedu.address.model.event.Date;
//import seedu.address.model.event.Name;
import seedu.address.model.tag.Tag;
import seedu.address.model.vendor.Description;
import seedu.address.model.vendor.Name;
import seedu.address.model.vendor.Phone;
import seedu.address.testutil.EditEventDescriptorBuilder;
import seedu.address.testutil.EditVendorDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String EDIT_MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditCommand.MESSAGE_USAGE);
    private static final String VENDOR_MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditVendorCommand.MESSAGE_USAGE);
    private static final String EVENT_MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditEventCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // no prefix
        assertParseFailure(parser, " a", EDIT_MESSAGE_INVALID_FORMAT);

        // multiple prefixes
        assertParseFailure(parser, " v/1 e/1", EDIT_MESSAGE_INVALID_FORMAT);

        // invalid index - vendor
        assertParseFailure(parser, " v/1abc", VENDOR_MESSAGE_INVALID_FORMAT);

        // invalid index - event
        assertParseFailure(parser, " e/1abc", EVENT_MESSAGE_INVALID_FORMAT);

        // no index specified
        assertParseFailure(parser, " v/ " + VALID_NAME_AMY, VENDOR_MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, " v/1", EditCommand.MESSAGE_NOT_EDITED);

        // empty edit command
        assertParseFailure(parser, "", EDIT_MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidVendorPreamble_failure() {
        // negative index
        assertParseFailure(parser, " v/-5" + NAME_DESC_AMY, VENDOR_MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, " v/0" + NAME_DESC_AMY, VENDOR_MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, " v/1 some random string", VENDOR_MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, " v/1 i/ string", VENDOR_MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidEventPreamble_failure() {
        // negative index
        assertParseFailure(parser, " e/-5" + NAME_DESC_WEDDING, EVENT_MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, " e/0" + NAME_DESC_WEDDING, EVENT_MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, " e/1 some random string", EVENT_MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, " e/1 i/ string", EVENT_MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidVendorValue_failure() {
        assertParseFailure(parser, " v/1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, " v/1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        // invalid description
        assertParseFailure(parser, " v/1" + INVALID_DESCRIPTION_DESC, Description.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " v/1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone
        assertParseFailure(parser, " v/1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the
        // {@code Vendor} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, " v/1" + TAG_DESC_FRIEND + TAG_DESC_HUSBAND + TAG_EMPTY, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " v/1" + TAG_DESC_FRIEND + TAG_EMPTY + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, " v/1" + TAG_EMPTY + TAG_DESC_FRIEND + TAG_DESC_HUSBAND, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser,
                " v/1" + INVALID_NAME_DESC + VALID_DESCRIPTION_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_vendorAllFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_VENDOR;
        String userInput = " " + PREFIX_VENDOR + targetIndex.getOneBased() + PHONE_DESC_BOB + TAG_DESC_HUSBAND
                + DESCRIPTION_DESC_AMY + NAME_DESC_AMY + TAG_DESC_FRIEND;

        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withDescription(VALID_DESCRIPTION_AMY)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        EditVendorCommand expectedCommand = new EditVendorCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_eventAllFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EVENT;
        String userInput = " " + PREFIX_EVENT + targetIndex.getOneBased() + DATE_DESC_WEDDING + NAME_DESC_WEDDING;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_NAME_WEDDING)
                .withDate(VALID_DATE_WEDDING).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_vendorSomeFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_VENDOR;
        String userInput = " " + PREFIX_VENDOR + targetIndex.getOneBased() + PHONE_DESC_BOB;

        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withPhone(VALID_PHONE_BOB).build();
        EditVendorCommand expectedCommand = new EditVendorCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_eventOneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_EVENT;
        String userInput = " " + PREFIX_EVENT + targetIndex.getOneBased() + NAME_DESC_BIRTHDAY;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_NAME_BIRTHDAY).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = " " + PREFIX_EVENT + targetIndex.getOneBased() + DATE_DESC_BIRTHDAY;
        descriptor = new EditEventDescriptorBuilder().withDate(VALID_DATE_BIRTHDAY).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_vendorOneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_VENDOR;
        String userInput = " " + PREFIX_VENDOR + targetIndex.getOneBased() + NAME_DESC_AMY;
        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditVendorCommand expectedCommand = new EditVendorCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = " " + PREFIX_VENDOR + targetIndex.getOneBased() + PHONE_DESC_AMY;
        descriptor = new EditVendorDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new EditVendorCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = " " + PREFIX_VENDOR + targetIndex.getOneBased() + DESCRIPTION_DESC_AMY;
        descriptor = new EditVendorDescriptorBuilder().withDescription(VALID_DESCRIPTION_AMY).build();
        expectedCommand = new EditVendorCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = " " + PREFIX_VENDOR + targetIndex.getOneBased() + TAG_DESC_FRIEND;
        descriptor = new EditVendorDescriptorBuilder().withTags(VALID_TAG_FRIEND).build();
        expectedCommand = new EditVendorCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_vendorMultipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_VENDOR;
        String userInput = " " + PREFIX_VENDOR + targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = " " + PREFIX_VENDOR + targetIndex.getOneBased() + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid fields repeated
        userInput = " " + PREFIX_VENDOR + targetIndex.getOneBased() + PHONE_DESC_AMY + DESCRIPTION_DESC_AMY
                + TAG_DESC_FRIEND + PHONE_DESC_AMY + DESCRIPTION_DESC_AMY + TAG_DESC_FRIEND
                + PHONE_DESC_BOB + DESCRIPTION_DESC_BOB + TAG_DESC_HUSBAND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_DESCRIPTION));

        // multiple invalid values
        userInput = " " + PREFIX_VENDOR + targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_DESCRIPTION_DESC
                + INVALID_PHONE_DESC + INVALID_DESCRIPTION_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_DESCRIPTION));
    }

    @Test
    public void parse_eventMultipleRepeatedFields_failure() {
        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = " " + PREFIX_EVENT + targetIndex.getOneBased() + INVALID_DATE_DESC + DATE_DESC_WEDDING;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // invalid followed by valid
        userInput = " " + PREFIX_EVENT + targetIndex.getOneBased() + DATE_DESC_WEDDING + INVALID_DATE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_DATE));

        // multiple valid fields repeated
        userInput = " " + PREFIX_EVENT + targetIndex.getOneBased() + NAME_DESC_WEDDING + NAME_DESC_BIRTHDAY
                + DATE_DESC_WEDDING + DATE_DESC_BIRTHDAY;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_DATE));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_VENDOR;
        String userInput = " " + PREFIX_VENDOR + targetIndex.getOneBased() + TAG_EMPTY;

        EditVendorDescriptor descriptor = new EditVendorDescriptorBuilder().withTags().build();
        EditVendorCommand expectedCommand = new EditVendorCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
