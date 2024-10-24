package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteCommand;
import seedu.address.model.person.Module;
import seedu.address.model.person.StudentId;

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
        StudentId validStudentId = new StudentId("12345678");
        String input = "" + validStudentId;
        assertParseSuccess(parser, input, new DeleteCommand(validStudentId));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", StudentId.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_validArgsWithModule_returnsDeleteCommand() {
        StudentId validStudentId = new StudentId("12345678");
        Module validModule = new Module("CS2103T");
        String input = "12345678 m/CS2103T";

        assertParseSuccess(parser, input, new DeleteCommand(validStudentId, validModule));
    }

    @Test
    public void parse_validArgsWithoutModule_returnsDeleteCommand() {
        StudentId validStudentId = new StudentId("12345678");
        String input = "12345678";

        assertParseSuccess(parser, input, new DeleteCommand(validStudentId));
    }

    @Test
    public void parse_invalidModuleFormat_throwsParseException() {
        String input = "12345678 m/ Invalid_Module!";
        assertParseFailure(parser, input, Module.MESSAGE_CONSTRAINTS);
    }
}
