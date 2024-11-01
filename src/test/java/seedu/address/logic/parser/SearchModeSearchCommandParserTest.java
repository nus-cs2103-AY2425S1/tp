package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.searchmode.SearchModeSearchCommand;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;



public class SearchModeSearchCommandParserTest {

    private SearchModeSearchCommandParser parser = new SearchModeSearchCommandParser();

    @Test
    public void parse_emptyInput_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SearchModeSearchCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validInput_success() {
        // whitespace only
        SearchModeSearchCommand expectedCommand = new SearchModeSearchCommand(
                new NameContainsKeywordsPredicate(Collections.singletonList("Amy")));

        assertParseSuccess(parser, " n/Amy", expectedCommand);

        // multiple whitespaces
        assertParseSuccess(parser, " n/  Amy  ", expectedCommand);

        // multiple keywords
        expectedCommand = new SearchModeSearchCommand(new NameContainsKeywordsPredicate(Arrays.asList("Amy", "Bob")));
        assertParseSuccess(parser, " n/Amy Bob", expectedCommand);

        // multiple keywords with leading and trailing whitespaces
        assertParseSuccess(parser, " n/  Amy Bob  ", expectedCommand);
    }
}
