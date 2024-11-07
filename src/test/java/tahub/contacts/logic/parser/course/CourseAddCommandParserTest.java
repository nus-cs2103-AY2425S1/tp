package tahub.contacts.logic.parser.course;

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

import tahub.contacts.logic.commands.course.CourseAddCommand;
import tahub.contacts.model.course.Course;
import tahub.contacts.model.course.CourseCode;
import tahub.contacts.model.course.CourseName;

public class CourseAddCommandParserTest {

    private final CourseAddCommandParser parser = new CourseAddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Course expectedCourse = new Course(new CourseCode(VALID_COURSE_CODE), new CourseName(VALID_COURSE_NAME));

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + COURSE_CODE_DESC + COURSE_NAME_DESC,
                new CourseAddCommand(expectedCourse));
    }

    @Test
    public void parse_missingParts_failure() {
        // no course code
        assertParseFailure(parser, "Software Engineering",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CourseAddCommand.MESSAGE_USAGE));

        // no course description
        assertParseFailure(parser, "CS2103T",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CourseAddCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid course code
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + INVALID_COURSE_CODE_DESC + COURSE_NAME_DESC,
                CourseCode.MESSAGE_CONSTRAINTS);

        // invalid course description
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + COURSE_CODE_DESC + INVALID_COURSE_NAME_DESC,
                CourseName.MESSAGE_CONSTRAINTS);
    }
}
