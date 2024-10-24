package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_STUDENT_NUMBER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TUTORIAL_GROUP_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.STUDENT_NUMBER_DESC_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.TUTORIAL_GROUP_DESC_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STUDENT_NUMBER_HUGH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TUTORIAL_GROUP_HUGH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL_GROUP;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.EditStudentCommand;
import seedu.address.logic.commands.EditStudentCommand.EditStudentDescriptor;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.student.StudentNumber;
import seedu.address.model.student.TutorialGroup;
import seedu.address.testutil.EditStudentDescriptorBuilder;

public class EditStudentCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditStudentCommand.MESSAGE_USAGE);

    private EditStudentCommandParser parser = new EditStudentCommandParser();


    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_HUGH, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditStudentCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_HUGH, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_HUGH, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS); // empty name
        assertParseFailure(parser, "1" + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS); // empty phone
        assertParseFailure(parser, "1" + INVALID_STUDENT_NUMBER_DESC,
                StudentNumber.MESSAGE_CONSTRAINTS); // empty student number
        assertParseFailure(parser, "1" + INVALID_TUTORIAL_GROUP_DESC,
                TutorialGroup.MESSAGE_CONSTRAINTS); // empty tutorial group
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_HUGH + PHONE_DESC_HUGH
                + STUDENT_NUMBER_DESC_HUGH + TUTORIAL_GROUP_DESC_HUGH;

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_HUGH)
                .withPhone(VALID_PHONE_HUGH).withStudentNumber(VALID_STUDENT_NUMBER_HUGH)
                .withTutorialGroup(VALID_TUTORIAL_GROUP_HUGH).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_HUGH + TUTORIAL_GROUP_DESC_HUGH;

        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withPhone(VALID_PHONE_HUGH)
                .withTutorialGroup(VALID_TUTORIAL_GROUP_HUGH).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_HUGH;
        EditStudentDescriptor descriptor = new EditStudentDescriptorBuilder().withName(VALID_NAME_HUGH).build();
        EditStudentCommand expectedCommand = new EditStudentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetIndex.getOneBased() + PHONE_DESC_HUGH;
        descriptor = new EditStudentDescriptorBuilder().withPhone(VALID_PHONE_HUGH).build();
        expectedCommand = new EditStudentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // student number
        userInput = targetIndex.getOneBased() + STUDENT_NUMBER_DESC_HUGH;
        descriptor = new EditStudentDescriptorBuilder().withStudentNumber(VALID_STUDENT_NUMBER_HUGH).build();
        expectedCommand = new EditStudentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // tutorial group
        userInput = targetIndex.getOneBased() + TUTORIAL_GROUP_DESC_HUGH;
        descriptor = new EditStudentDescriptorBuilder().withTutorialGroup(VALID_TUTORIAL_GROUP_HUGH).build();
        expectedCommand = new EditStudentCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // valid followed by invalid
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + PHONE_DESC_HUGH + INVALID_PHONE_DESC;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + PHONE_DESC_HUGH;
        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid phones and tutorial groups fields
        userInput = targetIndex.getOneBased() + PHONE_DESC_HUGH + TUTORIAL_GROUP_DESC_HUGH
                + PHONE_DESC_HUGH + TUTORIAL_GROUP_DESC_HUGH + PHONE_DESC_HUGH + TUTORIAL_GROUP_DESC_HUGH;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_TUTORIAL_GROUP));

        // multiple invalid phones and tutorial groups fields
        userInput = targetIndex.getOneBased() + INVALID_PHONE_DESC + INVALID_TUTORIAL_GROUP_DESC
                + INVALID_PHONE_DESC + INVALID_TUTORIAL_GROUP_DESC + INVALID_PHONE_DESC + INVALID_TUTORIAL_GROUP_DESC;
        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_TUTORIAL_GROUP));
    }

}
