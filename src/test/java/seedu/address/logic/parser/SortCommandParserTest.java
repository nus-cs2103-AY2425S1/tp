package seedu.address.logic.parser;


import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;


public class SortCommandParserTest {

    private final SortCommandParser parser = new SortCommandParser();

    @Test
    public void parse_noCriteria_success() {
        SortCommand expectedCommand = new SortCommand(new ArrayList<>());
        assertParseSuccess(parser, "", expectedCommand);
    }

    @Test
    public void parse_oneCriteria_success() {
        SortCommand expectedCommand = new SortCommand(new ArrayList<>(Arrays.asList(PREFIX_TAG)));
        assertParseSuccess(parser, " t/ ", expectedCommand);
    }

    @Test
    public void parse_twoCriteria_success() {
        SortCommand expectedCommand = new SortCommand(new ArrayList<>(Arrays.asList(PREFIX_TAG, PREFIX_EMAIL)));
        assertParseSuccess(parser, " t/  e/", expectedCommand);
    }
}
