package seedu.address.logic.parser.lesson;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.lesson.AddLessonCommand;
import seedu.address.model.datetime.Date;
import seedu.address.model.datetime.Time;
import seedu.address.model.lesson.Lesson;

public class AddLessonCommandParserTest {
    private AddLessonCommandParser parser = new AddLessonCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        AddLessonCommand expectedCommand = new AddLessonCommand(
                new Lesson(new Date("2024-10-20"), new Time("14:00"), List.of()));

        assertParseSuccess(parser, " d/2024-10-20 t/14:00", expectedCommand);
    }

    @Test
    public void parse_missingFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLessonCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " t/14:00", expectedMessage);
        assertParseFailure(parser, " d/2024-10-20", expectedMessage);
    }
}
