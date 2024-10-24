package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SearchTagCommand;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class SearchTagCommandParserTest {

    private SearchTagCommandParser parser = new SearchTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        SearchTagCommand expectedFindCommand =
                new SearchTagCommand(new TagContainsKeywordsPredicate(Arrays.asList("NewCustomer", "NonEnglishSpeaker")));
        assertParseSuccess(parser, "NewCustomer NonEnglishSpeaker", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n NewCustomer \n \t NonEnglishSpeaker  \t", expectedFindCommand);
    }

}
