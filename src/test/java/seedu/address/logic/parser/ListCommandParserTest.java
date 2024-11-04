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

        // sort by income
        assertParseSuccess(parser, " s/income", new ListCommand("income", false));

        // sort by age
        assertParseSuccess(parser, " s/age", new ListCommand("age", false));

        // sort by name with reverse
        assertParseSuccess(parser, " s/name r/", new ListCommand("name", true));

        // sort by email with reverse
        assertParseSuccess(parser, " s/email r/", new ListCommand("email", true));

        // sort by income with reverse
        assertParseSuccess(parser, " s/income r/", new ListCommand("income", true));

        // sort by age with reverse
        assertParseSuccess(parser, " s/age r/", new ListCommand("age", true));

    }

}
