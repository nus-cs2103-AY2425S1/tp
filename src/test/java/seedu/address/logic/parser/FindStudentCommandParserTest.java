package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUERY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.findcommands.FindStudentCommand;
import seedu.address.logic.parser.findcommands.FindStudentCommandParser;
import seedu.address.model.student.StudentMatchesQueryPredicate;

//@@author gho7sie

public class FindStudentCommandParserTest {

    private FindStudentCommandParser parser = new FindStudentCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "          ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FindStudentCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindStudentCommand() {
        FindStudentCommand expectedFindStudentCommand = new FindStudentCommand(new StudentMatchesQueryPredicate(
            List.of("Meier")));
        assertParseSuccess(parser, " " + PREFIX_QUERY + "Meier", expectedFindStudentCommand);
    }
}
