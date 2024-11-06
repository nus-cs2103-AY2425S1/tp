package seedu.academyassist.logic.parser;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static seedu.academyassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.academyassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.academyassist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.academyassist.testutil.TypicalStudentIds.STUDENT_ID_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.academyassist.logic.commands.DetailCommand;

/**
 * Contains helper methods for testing command parsers.
 */
public class DetailCommandParserTest {

    private DetailCommandParser parser = new DetailCommandParser();

    // Test for valid input
    @Test
    public void parse_validArgs_returnsDetailCommand() {
        assertParseSuccess(parser, STUDENT_ID_FIRST_PERSON.value, new DetailCommand(STUDENT_ID_FIRST_PERSON));
    }

    // Test for invalid input
    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DetailCommand.MESSAGE_USAGE));
    }

    // Test for null input
    @Test
    public void parse_nullArgs_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> parser.parse(null));
    }
}
