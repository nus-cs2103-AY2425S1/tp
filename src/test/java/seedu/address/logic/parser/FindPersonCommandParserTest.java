package seedu.address.logic.parser;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindPersonCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

public class FindPersonCommandParserTest {

    @Test
    public void parse_noPrefix_failure() {
        FindPersonCommandParser parser = new FindPersonCommandParser();
        assertParseFailure(parser, " person John",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_unsupportedPrefix_failure() {
        FindPersonCommandParser parser = new FindPersonCommandParser();
        assertParseFailure(parser, " person p/81505050",
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindPersonCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_success() {
        FindPersonCommandParser parser = new FindPersonCommandParser();
        assertDoesNotThrow(() -> {
            FindPersonCommand command = parser.parse(" person n/John");
            assertNotNull(command);
            assertTrue(((NameContainsKeywordsPredicate) command.getPredicate()).getKeywords().contains("John"));
        });
    }

    @Test
    public void parse_validMultipleArgs_success() {
        FindPersonCommandParser parser = new FindPersonCommandParser();
        assertDoesNotThrow(() -> {
            FindPersonCommand command = parser.parse(" person n/John Doe");
            assertNotNull(command);
            assertTrue(((NameContainsKeywordsPredicate) command.getPredicate()).getKeywords().contains("John"));
            assertTrue(((NameContainsKeywordsPredicate) command.getPredicate()).getKeywords().contains("Doe"));
        });
    }
}
