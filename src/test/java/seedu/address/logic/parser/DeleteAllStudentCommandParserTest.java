package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteAllStudentsCommand;

public class DeleteAllStudentCommandParserTest {

    @Test
    public void parse_validArgs_returnsDeleteAllStudentCommand() {
        // no leading and trailing whitespaces
        DeleteAllStudentsCommandParser parser = new DeleteAllStudentsCommandParser();
        assertParseSuccess(parser, "", new DeleteAllStudentsCommand());

        // leading and trailing whitespaces
        assertParseSuccess(parser, "  ", new DeleteAllStudentsCommand());
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // non-empty args
        DeleteAllStudentsCommandParser parser = new DeleteAllStudentsCommandParser();
        assertParseFailure(parser, "1", DeleteAllStudentsCommand.MESSAGE_USAGE);
    }
}
