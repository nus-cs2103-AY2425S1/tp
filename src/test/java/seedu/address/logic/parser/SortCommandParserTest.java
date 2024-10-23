package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_ASC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARAMETER_APPOINTMENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARAMETER_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARAMETER_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARAMETER_PAYDATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        assertParseSuccess(parser, "n/ asc", new SortCommand(VALID_PARAMETER_NAME, VALID_ORDER_ASC));
        assertParseSuccess(parser, "n/ desc", new SortCommand(VALID_PARAMETER_NAME, VALID_ORDER_DESC));
        assertParseSuccess(parser, "appt/ asc", new SortCommand(VALID_PARAMETER_APPOINTMENT, VALID_ORDER_ASC));
        assertParseSuccess(parser, "appt/ desc", new SortCommand(VALID_PARAMETER_APPOINTMENT, VALID_ORDER_DESC));
        assertParseSuccess(parser, "b/ asc", new SortCommand(VALID_PARAMETER_BIRTHDAY, VALID_ORDER_ASC));
        assertParseSuccess(parser, "b/ desc", new SortCommand(VALID_PARAMETER_BIRTHDAY, VALID_ORDER_DESC));
        assertParseSuccess(parser, "paydate/ asc", new SortCommand(VALID_PARAMETER_PAYDATE, VALID_ORDER_ASC));
        assertParseSuccess(parser, "paydate/ desc", new SortCommand(VALID_PARAMETER_PAYDATE, VALID_ORDER_DESC));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "invalid/ asc", SortCommand.MESSAGE_INVALID_PARAMETER);
        assertParseFailure(parser, "n/ invalid", SortCommand.MESSAGE_INVALID_ORDER);
    }

    @Test
    public void parse_invalidNumberOfArgs_throwsParseException() {
        assertParseFailure(parser, "n/", SortCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "n/ asc extra", SortCommand.MESSAGE_USAGE);
    }
}
