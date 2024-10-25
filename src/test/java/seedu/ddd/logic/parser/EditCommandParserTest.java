package seedu.ddd.logic.parser;

import static seedu.ddd.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.ddd.logic.parser.CliSyntax.*;
import static seedu.ddd.logic.parser.CommandParserTestUtil.*;
import static seedu.ddd.testutil.contact.TypicalContactFields.*;
import static seedu.ddd.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static seedu.ddd.testutil.TypicalIndexes.INDEX_SECOND_CONTACT;
import static seedu.ddd.testutil.TypicalIndexes.INDEX_THIRD_CONTACT;

import org.junit.jupiter.api.Test;

import seedu.ddd.commons.core.index.Index;
import seedu.ddd.logic.Messages;
import seedu.ddd.logic.commands.EditCommand;
import seedu.ddd.logic.commands.EditCommand.EditContactDescriptor;
import seedu.ddd.model.common.Name;
import seedu.ddd.model.common.Tag;
import seedu.ddd.model.contact.common.Address;
import seedu.ddd.model.contact.common.Email;
import seedu.ddd.model.contact.common.Phone;
import seedu.ddd.testutil.EditContactDescriptorBuilder;
import seedu.ddd.testutil.EditVendorDescriptorBuilder;

public class EditCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private final EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_CLIENT_NAME, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_CLIENT_NAME_ARGUMENT, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + VALID_CLIENT_NAME_ARGUMENT, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1 " + INVALID_CLIENT_NAME_ARGUMENT, Name.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1 " + INVALID_CLIENT_PHONE_ARGUMENT, Phone.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1 " + INVALID_CLIENT_EMAIL_ARGUMENT, Email.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1 " + INVALID_CLIENT_ADDRESS_ARGUMENT, Address.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1 " + INVALID_TAG_ARGUMENT, Tag.MESSAGE_CONSTRAINTS);

        // invalid phone followed by valid email
        assertParseFailure(parser, "1 " + INVALID_CLIENT_PHONE_ARGUMENT + " " + VALID_CLIENT_EMAIL_ARGUMENT, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Contact} being edited,
        // parsing it together with a valid tag results in error
        String arguments = CommandParserTestUtil.joinArguments(
            "1",
            VALID_TAG_ARGUMENT_1,
            VALID_TAG_ARGUMENT_2,
            VALID_EMPTY_TAG_ARGUMENT
        );
        assertParseFailure(parser, arguments, Tag.MESSAGE_CONSTRAINTS);
        arguments = CommandParserTestUtil.joinArguments(
            "1",
            VALID_TAG_ARGUMENT_1,
            VALID_EMPTY_TAG_ARGUMENT,
            VALID_TAG_ARGUMENT_2
        );
        assertParseFailure(parser, arguments, Tag.MESSAGE_CONSTRAINTS);
        arguments = CommandParserTestUtil.joinArguments(
            "1",
            VALID_EMPTY_TAG_ARGUMENT,
            VALID_TAG_ARGUMENT_1,
            VALID_TAG_ARGUMENT_2
        );
        assertParseFailure(parser, arguments, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        arguments = CommandParserTestUtil.joinArguments(
            "1",
            INVALID_CLIENT_NAME_ARGUMENT,
            VALID_CLIENT_PHONE_ARGUMENT,
            INVALID_CLIENT_EMAIL_ARGUMENT,
            VALID_CLIENT_ADDRESS_ARGUMENT
        );
        assertParseFailure(parser, arguments, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        // edit 1 contact field
        Index targetIndex = INDEX_FIRST_CONTACT;
        String arguments = CommandParserTestUtil.joinArguments(
            String.valueOf(targetIndex.getOneBased()),
            VALID_EDITED_CONTACT_NAME_ARGUMENT,
            VALID_CLIENT_PHONE_ARGUMENT,
            VALID_CLIENT_EMAIL_ARGUMENT,
            VALID_CLIENT_ADDRESS_ARGUMENT,
            VALID_TAG_ARGUMENT_1,
            VALID_TAG_ARGUMENT_2
        );
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder()
                .withName(VALID_EDITED_CONTACT_NAME)
                .withPhone(VALID_CLIENT_PHONE)
                .withEmail(VALID_CLIENT_EMAIL)
                .withAddress(VALID_CLIENT_ADDRESS)
                .withTags(VALID_TAG_1, VALID_TAG_2)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, arguments, expectedCommand);

        // edit 1 vendor field
        arguments = CommandParserTestUtil.joinArguments(
            String.valueOf(targetIndex.getOneBased()),
            VALID_VENDOR_NAME_ARGUMENT,
            VALID_VENDOR_PHONE_ARGUMENT,
            VALID_VENDOR_EMAIL_ARGUMENT,
            VALID_VENDOR_ADDRESS_ARGUMENT,
            VALID_VENDOR_SERVICE_ARGUMENT,
            VALID_TAG_ARGUMENT_1,
            VALID_TAG_ARGUMENT_2
        );
        descriptor = new EditVendorDescriptorBuilder()
                .withName(VALID_VENDOR_NAME)
                .withPhone(VALID_VENDOR_PHONE)
                .withEmail(VALID_VENDOR_EMAIL)
                .withAddress(VALID_VENDOR_ADDRESS)
                .withService(VALID_VENDOR_SERVICE_1)
                .withTags(VALID_TAG_1, VALID_TAG_2)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, arguments, expectedCommand);

        // edit multiple fields
        arguments = CommandParserTestUtil.joinArguments(
            String.valueOf(targetIndex.getOneBased()),
            VALID_EDITED_CONTACT_NAME_ARGUMENT,
            VALID_EDITED_CONTACT_PHONE_ARGUMENT,
            VALID_CLIENT_EMAIL_ARGUMENT,
            VALID_CLIENT_ADDRESS_ARGUMENT,
            VALID_TAG_ARGUMENT_1,
            VALID_TAG_ARGUMENT_2
        );
        descriptor = new EditContactDescriptorBuilder()
                .withName(VALID_EDITED_CONTACT_NAME)
                .withPhone(VALID_EDITED_CONTACT_PHONE)
                .withEmail(VALID_CLIENT_EMAIL)
                .withAddress(VALID_CLIENT_ADDRESS)
                .withTags(VALID_TAG_1, VALID_TAG_2)
                .build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, arguments, expectedCommand);    
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_CONTACT;
        String arguments = CommandParserTestUtil.joinArguments(
            String.valueOf(targetIndex.getOneBased()),
            VALID_EDITED_CONTACT_NAME_ARGUMENT,
            VALID_CLIENT_PHONE_ARGUMENT
        );
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder()
                .withName(VALID_EDITED_CONTACT_NAME)
                .withPhone(VALID_CLIENT_PHONE)
                .build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, arguments, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_CONTACT;
        String arguments = targetIndex.getOneBased() + " " + VALID_CLIENT_NAME_ARGUMENT;
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_CLIENT_NAME).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, arguments, expectedCommand);

        // phone
        arguments = targetIndex.getOneBased() + " " + VALID_CLIENT_PHONE_ARGUMENT;
        descriptor = new EditContactDescriptorBuilder().withPhone(VALID_CLIENT_PHONE).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, arguments, expectedCommand);

        // email
        arguments = targetIndex.getOneBased() + " " + VALID_CLIENT_EMAIL_ARGUMENT;
        descriptor = new EditContactDescriptorBuilder().withEmail(VALID_CLIENT_EMAIL).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, arguments, expectedCommand);

        // address
        arguments = targetIndex.getOneBased() + " " + VALID_CLIENT_ADDRESS_ARGUMENT;
        descriptor = new EditContactDescriptorBuilder().withAddress(VALID_CLIENT_ADDRESS).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, arguments, expectedCommand);

        // tags
        arguments = targetIndex.getOneBased() + " " + VALID_TAG_ARGUMENT_1;
        descriptor = new EditContactDescriptorBuilder().withTags(VALID_TAG_1).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, arguments, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_CONTACT;
        String arguments = CommandParserTestUtil.joinArguments(
            String.valueOf(targetIndex.getOneBased()),
            VALID_CLIENT_PHONE_ARGUMENT,
            INVALID_CLIENT_PHONE_ARGUMENT
        );
        assertParseFailure(parser, arguments, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        arguments = CommandParserTestUtil.joinArguments(
            String.valueOf(targetIndex.getOneBased()),
            INVALID_CLIENT_PHONE_ARGUMENT,
            VALID_CLIENT_PHONE_ARGUMENT
        );
        assertParseFailure(parser, arguments, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        arguments = CommandParserTestUtil.joinArguments(
            String.valueOf(targetIndex.getOneBased()),
            VALID_CLIENT_PHONE_ARGUMENT,
            VALID_CLIENT_ADDRESS_ARGUMENT,
            VALID_CLIENT_EMAIL_ARGUMENT,
            VALID_TAG_ARGUMENT_1,
            VALID_CLIENT_PHONE_ARGUMENT,
            VALID_CLIENT_ADDRESS_ARGUMENT,
            VALID_CLIENT_EMAIL_ARGUMENT,
            VALID_TAG_ARGUMENT_1,
            VALID_VENDOR_PHONE_ARGUMENT,
            VALID_VENDOR_ADDRESS_ARGUMENT,
            VALID_VENDOR_EMAIL_ARGUMENT,
            VALID_TAG_ARGUMENT_2
        );
        assertParseFailure(parser, arguments,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));

        // multiple invalid values
        arguments = CommandParserTestUtil.joinArguments(
            String.valueOf(targetIndex.getOneBased()),
            INVALID_CLIENT_PHONE_ARGUMENT,
            INVALID_CLIENT_ADDRESS_ARGUMENT,
            INVALID_CLIENT_EMAIL_ARGUMENT,
            INVALID_CLIENT_PHONE_ARGUMENT,
            INVALID_CLIENT_ADDRESS_ARGUMENT,
            INVALID_CLIENT_EMAIL_ARGUMENT
        );
        assertParseFailure(parser, arguments,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_CONTACT;
        String arguments = targetIndex.getOneBased() + " " + VALID_EMPTY_TAG_ARGUMENT;
        EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, arguments, expectedCommand);
    }
}
