package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.DeleteStudentCommand;
import seedu.address.model.person.Name;
import seedu.address.model.student.StudentNumber;

public class DeleteStudentCommandParserTest {
    private DeleteStudentCommandParser parser = new DeleteStudentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "$",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_noPreamble_throwsParseException() {
        assertParseFailure(parser, "n/Hugh",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteStudentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validName_returnsDeleteStudentCommand() {
        // no leading and trailing whitespaces
        DeleteStudentCommand expectedDeleteStudentCommand =
                new DeleteStudentCommand(new Name("Alice Pauline"));
        assertParseSuccess(parser, " n/Alice Pauline", expectedDeleteStudentCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice Pauline \n \t", expectedDeleteStudentCommand);
    }

    @Test
    public void parse_validNameAndStudentNumber_returnsDeleteStudentCommand() {
        // no leading and trailing whitespaces
        DeleteStudentCommand expectedDeleteStudentCommand =
                new DeleteStudentCommand(new Name("Alice Pauline"), new StudentNumber("A1234567X"));
        assertParseSuccess(parser, " n/Alice Pauline sn/A1234567X", expectedDeleteStudentCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/Alice Pauline \n \t sn/A1234567X", expectedDeleteStudentCommand);
    }

}
