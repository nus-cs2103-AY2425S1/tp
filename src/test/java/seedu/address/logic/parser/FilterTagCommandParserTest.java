package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterTagCommand;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class FilterTagCommandParserTest {

    private FilterTagCommandParser parser = new FilterTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FilterTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterTagCommand() {
        // No leading and trailing whitespaces
        FilterTagCommand expectedFilterTagCommand =
                new FilterTagCommand(new TagContainsKeywordsPredicate(Arrays.asList("Sponsor", "Friend")));
        assertParseSuccess(parser, "Sponsor Friend", expectedFilterTagCommand);

        // Multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Sponsor \n \t Friend  \t", expectedFilterTagCommand);
    }

    @Test
    public void parse_duplicateArgs_throwsParseException() {
        // Expect an error due to duplicate keywords
        String duplicateKeywords = "Sponsor Friend Sponsor";
        assertParseFailure(parser, duplicateKeywords, FilterTagCommandParser.MESSAGE_DUPLICATE_KEYWORDS);
    }
}
