package tahub.contacts.logic.parser;

import static tahub.contacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tahub.contacts.logic.commands.CommandTestUtil.COURSE_CODE_DESC;
import static tahub.contacts.logic.commands.CommandTestUtil.COURSE_NAME_DESC;
import static tahub.contacts.logic.commands.CommandTestUtil.INVALID_COURSE_CODE_DESC;
import static tahub.contacts.logic.commands.CommandTestUtil.INVALID_COURSE_NAME_DESC;
import static tahub.contacts.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static tahub.contacts.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tahub.contacts.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tahub.contacts.logic.commands.CourseCommand;
import tahub.contacts.model.course.Course;

public class CourseCommandParserTest {

    private final CourseCommandParser parser = new CourseCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Course expectedCourse = new Course(COURSE_CODE_DESC, COURSE_NAME_DESC);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + COURSE_CODE_DESC + COURSE_NAME_DESC,
                new CourseCommand(expectedCourse));
    }

    @Test
    public void parse_missingParts_failure() {
        // no course code
        assertParseFailure(parser, "Software Engineering",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CourseCommand.MESSAGE_USAGE));

        // no course description
        assertParseFailure(parser, "CS2103T",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, CourseCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid course code
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + INVALID_COURSE_CODE_DESC + COURSE_NAME_DESC,
                Course.COURSE_CODE_MESSAGE_CONSTRAINTS);

        // invalid course description
        assertParseFailure(parser,
                PREAMBLE_WHITESPACE + COURSE_CODE_DESC + INVALID_COURSE_NAME_DESC,
                Course.COURSE_NAME_MESSAGE_CONSTRAINTS);
    }
}
