package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterPaidCommand;
import seedu.address.model.predicate.StudentHasPaidPredicate;


public class FilterPaidCommandParserTest {
    private FilterPaidCommandParser parser = new FilterPaidCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPaidCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "bobo",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterPaidCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterPaidCommand() {
        FilterPaidCommand expectedCommand = new FilterPaidCommand(new StudentHasPaidPredicate(true));
        assertParseSuccess(parser, "true", expectedCommand);
        assertParseSuccess(parser, " true    ", expectedCommand);
        expectedCommand = new FilterPaidCommand(new StudentHasPaidPredicate(false));
        assertParseSuccess(parser, "false", expectedCommand);
        assertParseSuccess(parser, "     false ", expectedCommand);
    }
}
