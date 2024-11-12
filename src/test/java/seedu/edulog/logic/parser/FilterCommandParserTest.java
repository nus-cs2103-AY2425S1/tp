package seedu.edulog.logic.parser;

import static seedu.edulog.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.edulog.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.edulog.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.edulog.logic.commands.FilterCommand;
import seedu.edulog.model.student.StudentHasPaidPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArg_throwsParseException() {
        assertParseFailure(parser, "v", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsTrueFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new StudentHasPaidPredicate(true));
        assertParseSuccess(parser, "p", expectedFilterCommand);
    }

    @Test
    public void parse_validArgs_returnsFalseFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
            new FilterCommand(new StudentHasPaidPredicate(false));
        assertParseSuccess(parser, "u", expectedFilterCommand);
    }

    @Test
    public void parse_validArgsWithExtra_returnsTrueFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
            new FilterCommand(new StudentHasPaidPredicate(true));
        assertParseSuccess(parser, "paid", expectedFilterCommand);
    }

    @Test
    public void parse_validArgsWithExtra_returnsFalseFilterCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
            new FilterCommand(new StudentHasPaidPredicate(false));
        assertParseSuccess(parser, "unpaid", expectedFilterCommand);
    }

}
