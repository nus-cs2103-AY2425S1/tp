package tahub.contacts.logic.parser;

import static tahub.contacts.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tahub.contacts.logic.commands.CommandTestUtil.COURSE_CODE_DESC;
import static tahub.contacts.logic.commands.CommandTestUtil.INVALID_COURSE_CODE_DESC;
import static tahub.contacts.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static tahub.contacts.logic.commands.CommandTestUtil.VALID_COURSE_CODE;
import static tahub.contacts.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tahub.contacts.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import tahub.contacts.logic.commands.DeleteCourseCommand;
import tahub.contacts.model.course.CourseCode;

public class DeleteCourseCommandParserTest {

    private final DeleteCourseCommandParser parser = new DeleteCourseCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCourseCommand() {
        CourseCode courseCode = new CourseCode(VALID_COURSE_CODE);
        DeleteCourseCommand expectedCommand = new DeleteCourseCommand(courseCode);

        // whitespace only preamble
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + COURSE_CODE_DESC, expectedCommand);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid course code
        assertParseFailure(parser, INVALID_COURSE_CODE_DESC, CourseCode.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingParts_failure() {
        // no course code
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteCourseCommand.MESSAGE_USAGE));
    }
}
