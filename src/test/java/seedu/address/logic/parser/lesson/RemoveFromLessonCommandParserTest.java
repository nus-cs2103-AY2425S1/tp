package seedu.address.logic.parser.lesson;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.lesson.RemoveFromLessonCommand;
import seedu.address.model.student.Name;

public class RemoveFromLessonCommandParserTest {

    private RemoveFromLessonCommandParser parser = new RemoveFromLessonCommandParser();

    @Test
    public void parse_validArgs_returnsRemoveFromLessonCommand() {
        Index index = Index.fromOneBased(1);
        List<Name> expectedNames = List.of(new Name("Alex Yeoh"), new Name("Harry Ng"));

        assertParseSuccess(parser, "1 n/Alex Yeoh n/Harry Ng",
                new RemoveFromLessonCommand(index, expectedNames));
    }

    @Test
    public void parse_missingNames_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveFromLessonCommand.MESSAGE_USAGE);

        // Missing student names
        assertParseFailure(parser, "1", expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveFromLessonCommand.MESSAGE_USAGE);

        // Non-numeric index
        assertParseFailure(parser, "abc n/Alex Yeoh", expectedMessage);

        // Negative index
        assertParseFailure(parser, "-1 n/Alex Yeoh", expectedMessage);
    }

    @Test
    public void parse_invalidName_failure() {
        // Invalid name
        assertParseFailure(parser, "1 n/", Name.MESSAGE_CONSTRAINTS);
    }
}
