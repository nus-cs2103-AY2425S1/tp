package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(
                parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFilterCommand() {
        String userInput = " n/alice bob charlie";
        List<String> expectedKeywords = Arrays.asList("alice", "bob", "charlie");

        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new NameContainsKeywordsPredicate(expectedKeywords));
        assertParseSuccess(parser, userInput, expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n n/alice \n \t bob  \t charlie", expectedFilterCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // invalid inputs
        assertParseFailure(parser, "invalid input",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " n/",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " n/    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " x/alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }
}
