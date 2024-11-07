package seedu.address.logic.parser.lesson;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_ATTENDANCE;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.logic.parser.lesson.MarkLessonAttendanceCommandParser.MESSAGE_TOO_MANY_ATTENDANCE_ARGUMENTS;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.lesson.MarkLessonAttendanceCommand;
import seedu.address.model.student.Name;

public class MarkLessonAttendanceCommandParserTest {
    private static final Index VALID_INDEX = Index.fromOneBased(1);
    private static final List<Name> VALID_NAMES = List.of(new Name("Alice Tan"), new Name("Benson Son"));
    private MarkLessonAttendanceCommandParser parser = new MarkLessonAttendanceCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        MarkLessonAttendanceCommand expectedCommand = new MarkLessonAttendanceCommand(
                VALID_INDEX, VALID_NAMES, true);

        assertParseSuccess(parser, " 1 n/Alice Tan n/Benson Son a/y", expectedCommand);
        assertParseSuccess(parser, " 1 n/Alice Tan n/Benson Son a/Y", expectedCommand);
        assertParseSuccess(parser, " 1 n/Alice Tan n/Benson Son a/1", expectedCommand);

        MarkLessonAttendanceCommand expectedCommand2 = new MarkLessonAttendanceCommand(
                VALID_INDEX, VALID_NAMES, false);
        assertParseSuccess(parser, " 1 n/Alice Tan n/Benson Son a/n", expectedCommand2);
        assertParseSuccess(parser, " 1 n/Alice Tan n/Benson Son a/N", expectedCommand2);
        assertParseSuccess(parser, " 1 n/Alice Tan n/Benson Son a/0", expectedCommand2);
    }

    @Test
    public void parse_missingFields_failure() {
        String expectedMessage = String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, MarkLessonAttendanceCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " ", expectedMessage);
        assertParseFailure(parser, " n/Alice Tan n/Benson Son a/y", expectedMessage);
        assertParseFailure(parser, " 1 a/y", expectedMessage);
        assertParseFailure(parser, " 1 n/Alice Tan n/Benson Son", expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = MESSAGE_INVALID_INDEX;
        assertParseFailure(parser, " 0 n/Alice Tan n/Benson Son a/1", expectedMessage);
        assertParseFailure(parser, " -1 n/Alice Tan n/Benson Son a/1", expectedMessage);
        assertParseFailure(parser, " 1 2 n/Alice Tan n/Benson Son a/1", expectedMessage);
        assertParseFailure(parser, " a n/Alice Tan n/Benson Son a/1", expectedMessage);
    }

    @Test
    public void parse_invalidName_failure() {
        String expectedMessage = Name.MESSAGE_CONSTRAINTS;
        assertParseFailure(parser, " 1 n/Alic*@f n/Benson Son a/1", expectedMessage);
        // note that use of ; is not yet supported
        assertParseFailure(parser, " 1 n/Alice Tan;Benson Son a/1", expectedMessage);
    }

    @Test
    public void parse_invalidAttendance_failure() {
        String expectedMessage = MESSAGE_INVALID_ATTENDANCE;
        assertParseFailure(parser, " 1 n/Alice Tan n/Benson Son a/", expectedMessage);
        assertParseFailure(parser, " 1 n/Alice Tan n/Benson Son a/false", expectedMessage);
        assertParseFailure(parser, " 1 n/Alice Tan n/Benson Son a/-1", expectedMessage);
    }

    @Test
    public void parse_tooManyAttendanceFields_failure() {
        String expectedMessage = MESSAGE_TOO_MANY_ATTENDANCE_ARGUMENTS;
        assertParseFailure(parser, " 1 n/Alice Tan n/Benson Son a/1 a/0", expectedMessage);
        assertParseFailure(parser, " 1 n/Alice Tan n/Benson Son a/1 a/1", expectedMessage);
        assertParseFailure(parser, " 1 n/Alice Tan n/Benson Son a/0 a/0", expectedMessage);
    }
}
