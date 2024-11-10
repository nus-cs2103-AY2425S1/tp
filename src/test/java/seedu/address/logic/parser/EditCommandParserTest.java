package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_NAME_FIELD_INPUT;
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
import static seedu.address.testutil.ContactBuilder.DEFAULT_NAME;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_CONTACT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CONTACT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_CONTACT;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditCommand;
import seedu.address.model.contact.Name;
import seedu.address.testutil.EditContactDescriptorBuilder;

public class EditCommandParserTest {
    private static final String VALID_NAME_ALEX = "Alex Yeoh";
    private static final String ROLE_EMPTY = " " + PREFIX_ROLE;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_COMMAND_FORMAT);

    private EditCommandParser parser = new EditCommandParser();
    // Format {edit 1 <desc>} example of <desc>: " n/James&"
    @Test
    public void parse_invalidParameter_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, ParserUtil.MESSAGE_INVALID_NAME_FIELD);
        // invalid name
        assertParseFailure(parser, "1"
                + INVALID_TELEGRAM_HANDLE_DESC,
                ParserUtil.MESSAGE_INVALID_TELEGRAM_HANDLE_FIELD); //
        // invalid handle
        assertParseFailure(parser, "1" + INVALID_EMAIL_DESC, ParserUtil.MESSAGE_INVALID_EMAIL_FIELD);
        // invalid email

        // invalid student status
        assertParseFailure(parser, "1" + INVALID_STUDENT_STATUS_DESC, // empty string
                ParserUtil.MESSAGE_STUDENT_STATUS_FIELD_CANNOT_BLANK);

        assertParseFailure(parser, "1" + INVALID_ROLE_DESC,
                ParserUtil.MESSAGE_INVALID_ROLE_FIELD); // invalid role

        // invalid telegram handle followed by valid email
        assertParseFailure(parser, "1" + INVALID_TELEGRAM_HANDLE_DESC
                + EMAIL_DESC_AMY, ParserUtil.MESSAGE_INVALID_TELEGRAM_HANDLE_FIELD);

        // while parsing {@code PREFIX_TAG} alone will reset the tags of the {@code Contact} being edited,
        // parsing it together with a valid tag results in error
        assertParseFailure(parser, "1" + ROLE_DESC_ADMIN + ROLE_DESC_PRESIDENT + ROLE_EMPTY,
                ParserUtil.MESSAGE_ROLE_FIELD_CANNOT_BLANK);
        assertParseFailure(parser, "1" + ROLE_DESC_ADMIN + ROLE_EMPTY + ROLE_DESC_PRESIDENT,
                ParserUtil.MESSAGE_ROLE_FIELD_CANNOT_BLANK);
        assertParseFailure(parser, "1" + ROLE_EMPTY + ROLE_DESC_ADMIN + ROLE_DESC_PRESIDENT,
                ParserUtil.MESSAGE_ROLE_FIELD_CANNOT_BLANK);

        // multiple invalid values, but only the first invalid value is captured (think first in code order)
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_EMAIL_DESC + VALID_STUDENT_STATUS_AMY
                + VALID_TELEGRAM_HANDLE_AMY, ParserUtil.MESSAGE_INVALID_NAME_FIELD);
    }

    // command entered: edit Alex Yeoh n/Amy Bee ...}
    // Parsing with name
    @Test
    public void parse_withNameToEdit_success() {
        String personToEdit = DEFAULT_NAME;
        Name name = new Name(personToEdit);
        String userInput = personToEdit + NAME_DESC_AMY + TELEGRAM_HANDLE_DESC_BOB + ROLE_DESC_PRESIDENT
                + EMAIL_DESC_AMY + STUDENT_STATUS_DESC_AMY + ROLE_DESC_ADMIN;
        EditCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_NAME_AMY)
                .withTelegramHandle(VALID_TELEGRAM_HANDLE_BOB)
                .withEmail(VALID_EMAIL_AMY).withStudentStatus(VALID_STUDENT_STATUS_AMY)
                .withRoles(VALID_ROLE_PRESIDENT, VALID_ROLE_ADMIN).build();

        EditCommand expectedCommand = new EditCommand(name, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    // command entered: edit Alex Yeoh n/Amy Bee ...
    @Test
    public void parse_invalidNameToEdit_failure() {
        String userInput = DEFAULT_NAME + "&" + NAME_DESC_AMY;
        assertParseFailure(parser, userInput,
                MESSAGE_INVALID_NAME_FIELD_INPUT);
    }

    @Test
    public void parse_noFieldsEdited_failure() {
        String userInput = DEFAULT_NAME;
        assertParseFailure(parser, userInput, EditCommand.MESSAGE_MISSING_PREFIX);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_CONTACT;
        String userInput = targetIndex.getOneBased() + TELEGRAM_HANDLE_DESC_BOB + ROLE_DESC_PRESIDENT
                + EMAIL_DESC_AMY + STUDENT_STATUS_DESC_AMY + NAME_DESC_AMY + ROLE_DESC_ADMIN;

        EditCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder().withName(VALID_NAME_AMY)
                .withTelegramHandle(VALID_TELEGRAM_HANDLE_BOB)
                .withEmail(VALID_EMAIL_AMY).withStudentStatus(VALID_STUDENT_STATUS_AMY)
                .withRoles(VALID_ROLE_PRESIDENT, VALID_ROLE_ADMIN).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_CONTACT;
        String userInput = targetIndex.getOneBased() + TELEGRAM_HANDLE_DESC_BOB + EMAIL_DESC_AMY;

        EditCommand.EditContactDescriptor descriptor = new EditContactDescriptorBuilder()
                .withTelegramHandle(VALID_TELEGRAM_HANDLE_BOB)
                .withEmail(VALID_EMAIL_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_attemptToEditByInvalidInt_throwsParseException() {
        // num too big
        assertParseFailure(parser, Integer.MAX_VALUE +  "1" + EMAIL_DESC_BOB, ParserUtil.MESSAGE_INVALID_INDEX);

        // negative num
        assertParseFailure(parser, "-1" + EMAIL_DESC_BOB, ParserUtil.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parse_missingIndexOrName_throwsParseException() {
        assertParseFailure(parser, EMAIL_DESC_AMY, EditCommand.MESSAGE_MISSING_INDEX_OR_FULL_NAME);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_CONTACT;
        String userInput = targetIndex.getOneBased() + NAME_DESC_AMY;
        EditCommand.EditContactDescriptor descriptor =
                new EditContactDescriptorBuilder().withName(VALID_NAME_AMY).build();
        EditCommand expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // telegramHandle
        userInput = targetIndex.getOneBased() + TELEGRAM_HANDLE_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withTelegramHandle(VALID_TELEGRAM_HANDLE_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // email
        userInput = targetIndex.getOneBased() + EMAIL_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withEmail(VALID_EMAIL_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // student status
        userInput = targetIndex.getOneBased() + STUDENT_STATUS_DESC_AMY;
        descriptor = new EditContactDescriptorBuilder().withStudentStatus(VALID_STUDENT_STATUS_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tags
        userInput = targetIndex.getOneBased() + ROLE_DESC_ADMIN;
        descriptor = new EditContactDescriptorBuilder().withRoles(VALID_ROLE_ADMIN).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonRoleValue_failure()

        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_CONTACT;
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
        String userInput = INDEX_THIRD_CONTACT.getOneBased() + ROLE_EMPTY;

        assertParseFailure(parser, userInput, ParserUtil.MESSAGE_ROLE_FIELD_CANNOT_BLANK);
    }
}
