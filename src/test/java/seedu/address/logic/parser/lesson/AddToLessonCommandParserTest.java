package seedu.address.logic.parser.lesson;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.lesson.AddToLessonCommand;
import seedu.address.model.student.Name;

public class AddToLessonCommandParserTest {

    private AddToLessonCommandParser parser = new AddToLessonCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Index index = Index.fromOneBased(1);
        List<Name> expectedNames = List.of(new Name("John Doe"), new Name("Harry Ng"));

        // no whitespaces
        assertParseSuccess(parser, "1 n/John Doe n/Harry Ng",
                new AddToLessonCommand(index, expectedNames));

        // random whitespaces
        assertParseSuccess(parser, "  \n \t 1 \n n/John Doe  \t n/Harry Ng  ",
                new AddToLessonCommand(index, expectedNames));
    }

    @Test
    public void parse_missingNames_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToLessonCommand.MESSAGE_USAGE);

        // missing student names
        assertParseFailure(parser, "1", expectedMessage);

        // missing index and student names
        assertParseFailure(parser, " ", expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddToLessonCommand.MESSAGE_USAGE);

        // invalid index
        assertParseFailure(parser, "-1 n/John Doe", expectedMessage);

        // non-numeric index
        assertParseFailure(parser, "abc n/John Doe", expectedMessage);
    }

    @Test
    public void parse_invalidName_failure() {
        // invalid name (empty name)
        assertParseFailure(parser, "1 n/", Name.MESSAGE_CONSTRAINTS);

        // invalid name (numbers in name)
        assertParseFailure(parser, "1 n/1234", Name.MESSAGE_CONSTRAINTS);
    }
}
