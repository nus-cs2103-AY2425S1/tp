package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_BIGTECH;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_COMPANY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_MICROSOFT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_TESLA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_BIGTECH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_COMPANY;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditCompanyDescriptor;
import seedu.address.model.company.Address;
import seedu.address.model.company.Email;
import seedu.address.model.company.Name;
import seedu.address.model.company.Phone;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.EditCompanyDescriptorBuilder;

public class EditCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_TESLA, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_TESLA, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_TESLA, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS); // invalid address
        assertParseFailure(parser, "1" + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS); // invalid tag

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC + EMAIL_DESC_TESLA, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code
        // Company} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + TAG_DESC_COMPANY + TAG_DESC_BIGTECH
                + INVALID_TAG_DESC, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + TAG_DESC_COMPANY + INVALID_TAG_DESC
                + TAG_DESC_BIGTECH, Tag.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + INVALID_TAG_DESC + TAG_DESC_COMPANY
                + TAG_DESC_BIGTECH, Tag.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_ADDRESS_TESLA
                        + VALID_PHONE_TESLA,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_COMPANY;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_MICROSOFT + TAG_DESC_BIGTECH
                + EMAIL_DESC_TESLA + ADDRESS_DESC_TESLA + NAME_DESC_TESLA + TAG_DESC_COMPANY;

        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder().withName(VALID_NAME_TESLA)
                .withPhone(VALID_PHONE_MICROSOFT).withEmail(VALID_EMAIL_TESLA).withAddress(VALID_ADDRESS_TESLA)
                .withTags(VALID_TAG_BIGTECH, VALID_TAG_COMPANY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_COMPANY;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_MICROSOFT + EMAIL_DESC_TESLA;

        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder().withPhone(VALID_PHONE_MICROSOFT)
                .withEmail(VALID_EMAIL_TESLA).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_COMPANY;
        String userInput = targetIndex.getOneBased() + NAME_DESC_TESLA;
        EditCommand.EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder()
                .withName(VALID_NAME_TESLA).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_TESLA;
        descriptor = new EditCompanyDescriptorBuilder().withPhone(VALID_PHONE_TESLA).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_TESLA;
        descriptor = new EditCompanyDescriptorBuilder().withEmail(VALID_EMAIL_TESLA).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + ADDRESS_DESC_TESLA;
        descriptor = new EditCompanyDescriptorBuilder().withAddress(VALID_ADDRESS_TESLA).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + TAG_DESC_COMPANY;
        descriptor = new EditCompanyDescriptorBuilder().withTags(VALID_TAG_COMPANY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonTagValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_COMPANY;
        String userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_MICROSOFT;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + PHONE_DESC_MICROSOFT + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + PHONE_DESC_TESLA + ADDRESS_DESC_TESLA + EMAIL_DESC_TESLA
                + TAG_DESC_COMPANY + PHONE_DESC_TESLA + ADDRESS_DESC_TESLA + EMAIL_DESC_TESLA + TAG_DESC_COMPANY
                + PHONE_DESC_MICROSOFT + ADDRESS_DESC_MICROSOFT + EMAIL_DESC_MICROSOFT + TAG_DESC_BIGTECH;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC
                + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS));
    }

    @Test
    public void parse_resetTags_success() {
        Index targetIndex = INDEX_THIRD_COMPANY;
        String userInput = targetIndex.getOneBased() + TAG_EMPTY;

        EditCompanyDescriptor descriptor = new EditCompanyDescriptorBuilder().withTags().build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
