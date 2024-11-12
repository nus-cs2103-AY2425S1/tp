package tutorease.address.logic.parser;

import static tutorease.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static tutorease.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static tutorease.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import tutorease.address.logic.commands.FindLessonCommand;
import tutorease.address.model.lesson.LessonContainsNamesPredicate;

public class FindLessonCommandParserTest {

    private FindLessonCommandParser parser = new FindLessonCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_null_throwsParseException() {
        assertParseFailure(parser, null, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindLessonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindLessonCommand() {
        // no leading and trailing whitespaces
        FindLessonCommand expectedFindLessonCommand =
                new FindLessonCommand(new LessonContainsNamesPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, "Alice Bob", expectedFindLessonCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindLessonCommand);

        // allow numeric characters
        FindLessonCommand expectedFindLessonCommandNumeric =
                new FindLessonCommand(new LessonContainsNamesPredicate(Arrays.asList("1", "2")));
        assertParseSuccess(parser, "1 2", expectedFindLessonCommandNumeric);
    }

}
