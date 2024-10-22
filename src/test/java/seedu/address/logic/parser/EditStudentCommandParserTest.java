package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.editcommands.EditStudentCommand;
import seedu.address.logic.commands.editcommands.EditStudentCommand.EditPersonDescriptor;
import seedu.address.logic.parser.editcommands.EditStudentCommandParser;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.student.StudentNumber;
import seedu.address.testutil.EditPersonDescriptorBuilder;

public class EditStudentCommandParserTest {

    private EditStudentCommandParser parser = new EditStudentCommandParser();
    @Test
    public void parse_noFieldEdited_failure() {
        String userInput = STUDENT_NUMBER_DESC_AMY;
        assertThrows(ParseException.class, () -> parser.parse(userInput), EditStudentCommand.MESSAGE_NOT_EDITED);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = STUDENT_NUMBER_DESC_AMY + NAME_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND;

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
            .withEmail(VALID_EMAIL_AMY).withTags(VALID_TAG_FRIEND).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(new StudentNumber(VALID_STUDENT_NUMBER_AMY),
            descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        String userInput = STUDENT_NUMBER_DESC_AMY + NAME_DESC_BOB + EMAIL_DESC_BOB;
        StudentNumber validStudentNumber = new StudentNumber(VALID_STUDENT_NUMBER_AMY);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
            .withEmail(VALID_EMAIL_BOB).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(validStudentNumber, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        String userInput = STUDENT_NUMBER_DESC_AMY + INVALID_EMAIL_DESC + EMAIL_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid followed by valid
        userInput = STUDENT_NUMBER_DESC_AMY + EMAIL_DESC_BOB + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // mulltiple valid fields repeated
        userInput = STUDENT_NUMBER_DESC_AMY + EMAIL_DESC_AMY
                + TAG_DESC_FRIEND + EMAIL_DESC_AMY + TAG_DESC_FRIEND + EMAIL_DESC_BOB + TAG_DESC_FRIEND;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // multiple invalid values
        userInput = STUDENT_NUMBER_DESC_AMY + INVALID_EMAIL_DESC + INVALID_EMAIL_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));
    }

    @Test
    public void parse_resetTags_success() {
        String userInput = STUDENT_NUMBER_DESC_AMY + " " + PREFIX_TAG + "";

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withTags().build();
        EditStudentCommand expectedCommand = new EditStudentCommand(new StudentNumber(VALID_STUDENT_NUMBER_AMY),
            descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_invalidPrefix_failure() {
        String userInput = STUDENT_NUMBER_DESC_AMY + " " + PREFIX_TAG + " " + PREFIX_INDEX + "1";
        assertThrows(ParseException.class, ()-> parser.parse(userInput),
            String.format(Messages.MESSAGE_ILLEGAL_PREFIX_USED, EditStudentCommand.MESSAGE_USAGE));
    }
}
