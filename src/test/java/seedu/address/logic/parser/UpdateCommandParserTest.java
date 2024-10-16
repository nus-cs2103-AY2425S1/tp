package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GENDER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GENDER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NRIC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpdateCommand;
import seedu.address.model.person.Name;
import seedu.address.model.person.Nric;
import seedu.address.testutil.UpdatePersonDescriptorBuilder;

public class UpdateCommandParserTest {

    private UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_allFieldsPresent_success1() {
        // valid NRIC, all fields present
        UpdateCommand.UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND)
                .withGender(VALID_GENDER_AMY)
                .build();
        UpdateCommand expectedCommand = new UpdateCommand(new Nric(VALID_NRIC_AMY), descriptor);

        assertParseSuccess(parser, VALID_NRIC_AMY + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + GENDER_DESC_AMY + TAG_DESC_FRIEND, expectedCommand);

    }

    @Test
    public void parse_allFieldsPresent_success2() {
        // valid name, all fields present
        UpdateCommand.UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder()
                .withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND)
                .withGender(VALID_GENDER_AMY)
                .build();
        UpdateCommand expectedCommand = new UpdateCommand(new Name(VALID_NAME_AMY), descriptor);

        assertParseSuccess(parser, VALID_NAME_AMY + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + GENDER_DESC_AMY + TAG_DESC_FRIEND, expectedCommand);
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // no tags
        UpdateCommand.UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder()
                .withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY)
                .build();
        UpdateCommand expectedCommand = new UpdateCommand(new Name(VALID_NAME_AMY), descriptor);
        assertParseSuccess(parser, VALID_NAME_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY,
                expectedCommand);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing NRIC/Name
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);
        assertParseFailure(parser, PHONE_DESC_AMY + EMAIL_DESC_AMY, expectedMessage);

        // missing NRIC/Name and other fields
        assertParseFailure(parser, "", expectedMessage);
    }

    @Test
    public void parse_noFieldsEdited_failure() {
        // valid NRIC or name but no other fields
        String expectedMessage = UpdateCommand.MESSAGE_NOT_EDITED;

        assertParseFailure(parser, VALID_NRIC_AMY, expectedMessage);

        assertParseFailure(parser, VALID_NAME_AMY, expectedMessage);
    }
}
