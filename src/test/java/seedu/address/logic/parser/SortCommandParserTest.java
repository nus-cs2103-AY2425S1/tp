package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_ASC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ORDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PARAMETER_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_validArgs_returnsSortCommand() {
        assertParseSuccess(parser, "name asc", new SortCommand(VALID_PARAMETER_NAME, VALID_ORDER_ASC));
        assertParseSuccess(parser, "name desc", new SortCommand(VALID_PARAMETER_NAME, VALID_ORDER_DESC));
    }
}
