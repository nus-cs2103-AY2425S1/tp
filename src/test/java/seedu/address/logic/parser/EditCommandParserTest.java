package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ROLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENT_STATUS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TELEGRAM_HANDLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_ADMIN;
import static seedu.address.logic.commands.CommandTestUtil.ROLE_DESC_PRESIDENT;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_STATUS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_STATUS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.TELEGRAM_HANDLE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TELEGRAM_HANDLE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_ADMIN;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ROLE_PRESIDENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_STATUS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_HANDLE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TELEGRAM_HANDLE_BOB;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ROLE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM_HANDLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.StudentStatus;
import seedu.address.model.person.TelegramHandle;
import seedu.address.model.tag.Role;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditCommandParserTest {

    private static final String ROLE_EMPTY = " " + PREFIX_ROLE;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE);

    private EditCommandParser parser = new EditCommandParser();

    /*
    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }
    */

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // invalid name
        assertParseFailure(parser, "1"
                + INVALID_TELEGRAM_HANDLE_DESC, TelegramHandle.MESSAGE_CONSTRAINTS); // invalid phone
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, Email.MESSAGE_CONSTRAINTS); // invalid email

        // invalid student status
        assertParseFailure(parser, "1" + INVALID_STUDENT_STATUS_DESC, StudentStatus.MESSAGE_CONSTRAINTS);

        assertParseFailure(parser, "1" + INVALID_ROLE_DESC, Role.MESSAGE_CONSTRAINTS); // invalid role

        // invalid phone followed by valid email
        assertParseFailure(parser, "1" + INVALID_TELEGRAM_HANDLE_DESC
                + EMAIL_DESC_AMY, TelegramHandle.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Person} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + ROLE_DESC_ADMIN + ROLE_DESC_PRESIDENT + ROLE_EMPTY, Role.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ROLE_DESC_ADMIN + ROLE_EMPTY + ROLE_DESC_PRESIDENT, Role.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1" + ROLE_EMPTY + ROLE_DESC_ADMIN + ROLE_DESC_PRESIDENT, Role.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_STUDENT_STATUS_AMY
                + VALID_TELEGRAM_HANDLE_AMY, Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + TELEGRAM_HANDLE_DESC_BOB + ROLE_DESC_PRESIDENT
                + EMAIL_DESC_AMY + STUDENT_STATUS_DESC_AMY + NAME_DESC_AMY + ROLE_DESC_ADMIN;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withTelegramHandle(VALID_TELEGRAM_HANDLE_BOB)
                .withEmail(VALID_EMAIL_AMY).withStudentStatus(VALID_STUDENT_STATUS_AMY)
                .withRoles(VALID_ROLE_PRESIDENT, VALID_ROLE_ADMIN).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + TELEGRAM_HANDLE_DESC_BOB + EMAIL_DESC_AMY;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder()
                .withTelegramHandle(VALID_TELEGRAM_HANDLE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // telegramHandle
        userInput = targetIndex.getOneBased() + TELEGRAM_HANDLE_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withTelegramHandle(VALID_TELEGRAM_HANDLE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // student status
        userInput = targetIndex.getOneBased() + STUDENT_STATUS_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withStudentStatus(VALID_STUDENT_STATUS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + ROLE_DESC_ADMIN;
        descriptor = new EditPersonDescriptorBuilder().withRoles(VALID_ROLE_ADMIN).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonRoleValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_TELEGRAM_HANDLE_DESC + TELEGRAM_HANDLE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEGRAM_HANDLE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + TELEGRAM_HANDLE_DESC_BOB + INVALID_TELEGRAM_HANDLE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEGRAM_HANDLE));

        // mulltiple valid fields repeated
        userInput = targetIndex.getOneBased() + TELEGRAM_HANDLE_DESC_AMY + STUDENT_STATUS_DESC_AMY + EMAIL_DESC_AMY
                + ROLE_DESC_ADMIN + TELEGRAM_HANDLE_DESC_AMY
                + STUDENT_STATUS_DESC_AMY + EMAIL_DESC_AMY + ROLE_DESC_ADMIN
                + TELEGRAM_HANDLE_DESC_BOB + STUDENT_STATUS_DESC_BOB + EMAIL_DESC_BOB + ROLE_DESC_PRESIDENT;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELEGRAM_HANDLE,
                        PREFIX_EMAIL, PREFIX_STUDENT_STATUS));

        // multiple invalid values
        userInput = targetIndex.getOneBased() + INVALID_TELEGRAM_HANDLE_DESC
                + INVALID_STUDENT_STATUS_DESC + INVALID_EMAIL_DESC
                + INVALID_TELEGRAM_HANDLE_DESC + INVALID_STUDENT_STATUS_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(
                        PREFIX_TELEGRAM_HANDLE, PREFIX_EMAIL, PREFIX_STUDENT_STATUS));
    }

    @Test
    public void parse_resetRoles_failure() {
        String userInput = INDEX_THIRD_PERSON.getOneBased() + ROLE_EMPTY;

        assertParseFailure(parser, userInput, Role.MESSAGE_CONSTRAINTS);
    }
}
