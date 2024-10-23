package tahub.contacts.logic.parser;

import static tahub.contacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tahub.contacts.logic.commands.CommandTestUtil.COURSE_CODE_DESC;
import static tahub.contacts.logic.commands.CommandTestUtil.COURSE_NAME_DESC;
import static tahub.contacts.logic.commands.CommandTestUtil.INVALID_COURSE_CODE_DESC;
import static tahub.contacts.logic.commands.CommandTestUtil.INVALID_COURSE_NAME_DESC;
import static tahub.contacts.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_COURSE_CODE;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_COURSE_NAME;
import static tahub.contacts.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tahub.contacts.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tahub.contacts.logic.commands.EditCourseCommand;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.CourseName;
import tahub.contacts.testutil.EditCourseDescriptorBuilder;

public class EditCourseCommandParserTest {

    private final EditCourseCommandParser parser = new EditCourseCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        CourseCode courseCode = new CourseCode(VALID_COURSE_CODE);
        EditCourseCommand.EditCourseDescriptor descriptor = new EditCourseDescriptorBuilder().withCourseName(VALID_COURSE_NAME).build();
        EditCourseCommand expectedCommand = new EditCourseCommand(courseCode, descriptor);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + COURSE_CODE_DESC + COURSE_NAME_DESC, expectedCommand);
    }

    @Test
    public void parse_missingParts_failure() {
        // no course code
        assertParseFailure(parser, COURSE_NAME_DESC, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCourseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid course code
        assertParseFailure(parser, INVALID_COURSE_CODE_DESC + COURSE_NAME_DESC, CourseCode.MESSAGE_CONSTRAINTS);

        // invalid course name
        assertParseFailure(parser, COURSE_CODE_DESC + INVALID_COURSE_NAME_DESC, CourseName.MESSAGE_CONSTRAINTS);
    }
}