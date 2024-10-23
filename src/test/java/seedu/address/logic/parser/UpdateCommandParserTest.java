package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LESSON_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LEVEL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_SUBJECT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LESSON_TIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LEVEL_DESC_S4_NT;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.SUBJECT_DESC_MATH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LESSON_TIME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LEVEL_S4_NT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_ENGLISH;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SUBJECT_MATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LESSON_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBJECT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.UpdateCommand;
import seedu.address.logic.commands.UpdateCommand.UpdatePersonDescriptor;
import seedu.address.model.person.Address;
import seedu.address.model.person.LessonTime;
import seedu.address.model.person.Level;
import seedu.address.model.person.Name;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Subject;
import seedu.address.testutil.UpdatePersonDescriptorBuilder;

public class UpdateCommandParserTest {

    private static final String SUBJECT_EMPTY = " " + PREFIX_SUBJECT;
    private static final String LESSONTIME_EMPTY = " " + PREFIX_LESSON_TIME;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateCommand.MESSAGE_USAGE);

    private UpdateCommandParser parser = new UpdateCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no name specified
        assertParseFailure(parser, PHONE_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, VALID_NAME_AMY, UpdateCommand.MESSAGE_NOT_UPDATED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // invalid name being parsed as preamble
        assertParseFailure(parser, "1@asd" + NAME_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_NAME_DESC, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_PHONE_DESC, Phone.MESSAGE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_ADDRESS_DESC, Address.MESSAGE_CONSTRAINTS);

        // invalid subject
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_SUBJECT_DESC, Subject.MESSAGE_CONSTRAINTS);

        // invalid lesson time
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_LESSON_TIME_DESC, LessonTime.MESSAGE_CONSTRAINTS);

        // invalid level
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_LEVEL_DESC, Level.MESSAGE_CONSTRAINTS);

        // invalid phone followed by valid address
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_PHONE_DESC
                + VALID_ADDRESS_AMY, Phone.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_SUBJECT} alone will reset the Subjects of the {@code Person} being updated,
        // parsing it together with a valid Subject results in error
        assertParseFailure(parser, VALID_NAME_AMY + SUBJECT_DESC_ENGLISH
                + SUBJECT_DESC_MATH + SUBJECT_EMPTY, Subject.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_NAME_AMY + SUBJECT_DESC_ENGLISH
                + SUBJECT_EMPTY + SUBJECT_DESC_MATH, Subject.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_NAME_AMY + SUBJECT_EMPTY
                + SUBJECT_DESC_ENGLISH + SUBJECT_DESC_MATH, Subject.MESSAGE_CONSTRAINTS);

        // while parsing {@code PREFIX_LESSON_TIME} alone will reset the
        // LessonTimes of the {@code Person} being updated,
        // parsing it together with a valid LessonTime results in error
        assertParseFailure(parser, VALID_NAME_AMY + LESSON_TIME_DESC
                + LESSONTIME_EMPTY, LessonTime.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, VALID_NAME_AMY + LESSONTIME_EMPTY
                + LESSON_TIME_DESC, LessonTime.MESSAGE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, VALID_NAME_AMY + INVALID_NAME_DESC + VALID_ADDRESS_AMY + VALID_PHONE_AMY,
                Name.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        String userInput = VALID_NAME_AMY + PHONE_DESC_BOB + SUBJECT_DESC_MATH
                + ADDRESS_DESC_AMY + NAME_DESC_AMY + SUBJECT_DESC_ENGLISH + LESSON_TIME_DESC;

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_AMY)
                .withSubjects(VALID_SUBJECT_MATH, VALID_SUBJECT_ENGLISH).withLessonTimes(VALID_LESSON_TIME).build();
        UpdateCommand expectedCommand = new UpdateCommand(new Name(VALID_NAME_AMY), descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Name targetName = new Name(VALID_NAME_AMY);
        String userInput = targetName + PHONE_DESC_BOB;

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder().withPhone(VALID_PHONE_BOB)
                .build();
        UpdateCommand expectedCommand = new UpdateCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Name targetName = new Name(VALID_NAME_BOB);
        String userInput = targetName + NAME_DESC_AMY;
        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder().withName(VALID_NAME_AMY).build();
        UpdateCommand expectedCommand = new UpdateCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // phone
        userInput = targetName + PHONE_DESC_AMY;
        descriptor = new UpdatePersonDescriptorBuilder().withPhone(VALID_PHONE_AMY).build();
        expectedCommand = new UpdateCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetName + ADDRESS_DESC_AMY;
        descriptor = new UpdatePersonDescriptorBuilder().withAddress(VALID_ADDRESS_AMY).build();
        expectedCommand = new UpdateCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // subjects
        userInput = targetName + SUBJECT_DESC_ENGLISH;
        descriptor = new UpdatePersonDescriptorBuilder().withSubjects(VALID_SUBJECT_ENGLISH).build();
        expectedCommand = new UpdateCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // lesson times
        userInput = targetName + LESSON_TIME_DESC;
        descriptor = new UpdatePersonDescriptorBuilder().withLessonTimes(VALID_LESSON_TIME).build();
        expectedCommand = new UpdateCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // level
        userInput = targetName + LEVEL_DESC_S4_NT;
        descriptor = new UpdatePersonDescriptorBuilder().withLevel(VALID_LEVEL_S4_NT).build();
        expectedCommand = new UpdateCommand(targetName, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_failure() {
        // More extensive testing of duplicate parameter detections is done in
        // AddCommandParserTest#parse_repeatedNonSubjectValue_failure()

        // valid followed by invalid
        Name targetName = new Name(VALID_NAME_AMY);
        String userInput = targetName + INVALID_PHONE_DESC + PHONE_DESC_BOB;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // invalid followed by valid
        userInput = targetName + PHONE_DESC_BOB + INVALID_PHONE_DESC;

        assertParseFailure(parser, userInput, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple valid fields repeated
        userInput = targetName + PHONE_DESC_AMY + ADDRESS_DESC_AMY
                + SUBJECT_DESC_ENGLISH + PHONE_DESC_AMY + ADDRESS_DESC_AMY + SUBJECT_DESC_ENGLISH
                + PHONE_DESC_BOB + ADDRESS_DESC_BOB + SUBJECT_DESC_MATH;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_ADDRESS));

        // multiple invalid values
        userInput = targetName + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC
                + INVALID_PHONE_DESC + INVALID_ADDRESS_DESC;

        assertParseFailure(parser, userInput,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE, PREFIX_ADDRESS));
    }

    @Test
    public void parse_resetSubjects_success() {
        Name targetName = new Name(VALID_NAME_AMY);
        String userInput = targetName + SUBJECT_EMPTY;

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder().withSubjects().build();
        UpdateCommand expectedCommand = new UpdateCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_resetLessonTimes_success() {
        Name targetName = new Name(VALID_NAME_AMY);
        String userInput = targetName + LESSONTIME_EMPTY;

        UpdatePersonDescriptor descriptor = new UpdatePersonDescriptorBuilder().withLessonTimes().build();
        UpdateCommand expectedCommand = new UpdateCommand(targetName, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
