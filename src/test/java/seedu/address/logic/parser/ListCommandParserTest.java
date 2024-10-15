package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_SORT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.ListCommand;

public class ListCommandParserTest {

    private ListCommandParser parser = new ListCommandParser();

    @Test
    public void parse_noArgs_returnsListCommand() {
        assertParseSuccess(parser, "", new ListCommand());
    }

    @Test
    public void parse_validSortOption_returnsListCommand() {
        SortOption sortOption = new SortOption("alphabet");
        assertParseSuccess(parser, " s/alphabet", new ListCommand(sortOption));
    }

    @Test
    public void parse_multipleValidSortOptions_throwsParseException() {
        String expectedMessage = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SORT);
        assertParseFailure(parser, " s/alphabet s/age", expectedMessage);
    }

    @Test
    public void parse_emptySortOption_throwsParseException() {
        assertParseFailure(parser, " s/", "Sort option cannot be empty.");
    }

    @Test
    public void parse_invalidSortOption_throwsParseException() {
        SortOption sortOption = new SortOption("alphabet");
        assertParseFailure(parser, " s/name", SortOption.MESSAGE_CONSTRAINTS);
    }
}
