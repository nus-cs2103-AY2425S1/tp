package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.model.person.TagsContainsKeywordsPredicate;

public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test with special characters or invalid inputs
        assertParseFailure(parser, "!!!@@@###", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            FilterCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "123456", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFilterCommand =
                new FilterCommand(new TagsContainsKeywordsPredicate(Arrays.asList("Vegan", "Vegetarian")));
        assertParseSuccess(parser, "Vegan Vegetarian", expectedFilterCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Vegan \n \t Vegetarian  \t", expectedFilterCommand);
    }
}
