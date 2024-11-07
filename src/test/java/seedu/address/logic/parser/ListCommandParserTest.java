package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
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

    @Test
    public void parse_multipleSortFields_throwsParseException() {
        String expectedMessage = ListCommandParser.MESSAGE_MULTIPLE_SORT_FIELDS;

        // two sort fields
        assertParseFailure(parser, " s/name s/email", expectedMessage);

        // three sort fields
        assertParseFailure(parser, " s/name s/email s/age", expectedMessage);

        // multiple sort fields with reverse
        assertParseFailure(parser, " s/name s/email r/", expectedMessage);
    }

    @Test
    public void parse_multipleReverseFlags_throwsParseException() {
        String expectedMessage = ListCommandParser.MESSAGE_MULTIPLE_REVERSE;

        // two reverse flags
        assertParseFailure(parser, " s/name r/ r/", expectedMessage);

        // three reverse flags
        assertParseFailure(parser, " s/name r/ r/ r/", expectedMessage);

        // multiple reverse flags without sort field
        assertParseFailure(parser, " r/ r/", expectedMessage);
    }

    @Test
    public void parse_invalidNonPrefixedArgs_throwsParseException() {
        String expectedMessage = String.format(ListCommandParser.MESSAGE_INVALID_FORMAT, ListCommand.MESSAGE_USAGE);

        // single invalid argument
        assertParseFailure(parser, " name", expectedMessage);

        // multiple invalid arguments
        assertParseFailure(parser, " name email", expectedMessage);

        // mix of valid and invalid arguments
        assertParseFailure(parser, " name s/email", expectedMessage);

        // invalid arguments with reverse flag
        assertParseFailure(parser, " name r/", expectedMessage);
    }

    @Test
    public void parse_invalidSortField_throwsParseException() {
        String expectedMessage = ListCommand.MESSAGE_INVALID_SORT_FIELD;

        // invalid sort field
        assertParseFailure(parser, " s/invalid", expectedMessage);

        // invalid sort field with reverse
        assertParseFailure(parser, " s/invalid r/", expectedMessage);
    }
}
