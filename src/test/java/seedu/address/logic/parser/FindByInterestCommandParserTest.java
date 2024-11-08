package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindByInterestCommand;
import seedu.address.model.person.InterestContainsKeywordsPredicate;


public class FindByInterestCommandParserTest {

    private final FindByInterestCommandParser parser = new FindByInterestCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByInterestCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validSingleKeyword_returnsFindByInterestCommand() {
        FindByInterestCommand expectedCommand = new FindByInterestCommand(
                new InterestContainsKeywordsPredicate("Reading"));
        assertParseSuccess(parser, "i/Reading", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "Reading", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByInterestCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_multipleSpaces_returnsTrimmedFindByInterestCommand() {
        FindByInterestCommand expectedCommand = new FindByInterestCommand(
                new InterestContainsKeywordsPredicate("Photography"));
        assertParseSuccess(parser, " i/   Photography    ", expectedCommand);
    }

    @Test
    public void parse_multipleKeywordsWithoutComma_throwsParseException() {
        assertParseFailure(parser, "i/Reading i/Swimming", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByInterestCommand.MESSAGE_USAGE));
    }
}
