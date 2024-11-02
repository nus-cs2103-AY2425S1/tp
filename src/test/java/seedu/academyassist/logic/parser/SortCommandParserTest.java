package seedu.academyassist.logic.parser;

import static seedu.academyassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.academyassist.logic.parser.CliSyntax.PREFIX_SORT_PARAM;
import static seedu.academyassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.academyassist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.academyassist.model.sort.SortParam.MESSAGE_CONSTRAINTS;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import seedu.academyassist.logic.commands.SortCommand;
import seedu.academyassist.model.sort.SortParam;

/**
 * Integration tests for SortCommandParser
 */
public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @ParameterizedTest
    @ValueSource(strings = {"name", "class", "studentId"})
    public void parse_validArgs_returnsSortCommand(String param) {
        SortCommand expectedCommand = new SortCommand(new SortParam(param));
        assertParseSuccess(parser, " " + PREFIX_SORT_PARAM + param, expectedCommand);
    }

    @ParameterizedTest
    @ValueSource(strings = { "invalid", "Name", "CLASS", "student_id", "", " " })
    public void parse_invalidArgs_throwsParseException(String param) {
        assertParseFailure(parser, " " + PREFIX_SORT_PARAM + param,
                MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_missingPrefix_throwsParseException() {
        assertParseFailure(parser, "name",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_extraArguments_throwsParseException() {
        assertParseFailure(parser, " " + PREFIX_SORT_PARAM + "name extra",
                MESSAGE_CONSTRAINTS);
    }
}
