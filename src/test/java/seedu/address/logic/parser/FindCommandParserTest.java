package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.CommandTestUtil;
import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.StudentId;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside the FindCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the FindCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        StudentId validStudentId = new StudentId(CommandTestUtil.VALID_STUDENTID_BOB);
        assertParseSuccess(parser, validStudentId.toString(), new FindCommand(validStudentId));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // multiple arguments
        assertParseFailure(parser, "11111111 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // invalid studentId
        assertParseFailure(parser, "1", StudentId.MESSAGE_CONSTRAINTS);
    }
}
