package seedu.academyassist.logic.parser;

import static seedu.academyassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.academyassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.academyassist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.academyassist.testutil.TypicalStudentIds.STUDENT_ID_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.academyassist.logic.commands.DeleteCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private DeleteCommandParser parser = new DeleteCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, STUDENT_ID_FIRST_PERSON.value, new DeleteCommand(STUDENT_ID_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
    }
}
