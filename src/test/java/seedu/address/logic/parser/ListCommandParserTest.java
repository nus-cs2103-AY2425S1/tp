package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_emptyArg_returnsListCommand() {
        assertParseSuccess(parser, "", new ListCommand(null, false));
    }

    @Test
    public void parse_validArgs_returnsListCommand() {
        // sort by name
        assertParseSuccess(parser, " s/name", new ListCommand("name", false));

        // sort by email
        assertParseSuccess(parser, " s/email", new ListCommand("email", false));

        // sort by name with reverse
        assertParseSuccess(parser, " s/name r/", new ListCommand("name", true));

        // sort by email with reverse
        assertParseSuccess(parser, " s/email r/", new ListCommand("email", true));

    }

}
