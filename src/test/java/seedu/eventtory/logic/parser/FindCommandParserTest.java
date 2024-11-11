package seedu.eventtory.logic.parser;

import static seedu.eventtory.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventtory.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.eventtory.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import seedu.eventtory.logic.commands.FindCommand;
import seedu.eventtory.logic.commands.FindEventCommand;
import seedu.eventtory.logic.commands.FindVendorCommand;
import seedu.eventtory.model.event.EventContainsKeywordsPredicate;
import seedu.eventtory.model.vendor.VendorContainsKeywordsPredicate;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_invalidInput_throwsParseException() {
        // empty input
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));

        // no prefix
        assertParseFailure(parser, " Alice Bob", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_eventValidArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindEventCommand(new EventContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " e/ Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " e/ \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_vendorValidArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand =
                new FindVendorCommand(new VendorContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        assertParseSuccess(parser, " v/ Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " v/ \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_invalidPrefix_throwsParseException() {
        // invalid prefix
        assertParseFailure(parser, " i/ string", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FindCommand.MESSAGE_USAGE));

        // multiple valid prefixes
        assertParseFailure(parser, " e/ string v/ string", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));

        // multiple prefixes: with one valid prefix, one invalid prefix
        assertParseFailure(parser, " i/ string v/ string", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_emptyArgs_throwsParseException() {
        // invalid value
        assertParseFailure(parser, " e/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FindCommand.MESSAGE_USAGE));
        assertParseFailure(parser, " v/ ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
            FindCommand.MESSAGE_USAGE));
    }
}
