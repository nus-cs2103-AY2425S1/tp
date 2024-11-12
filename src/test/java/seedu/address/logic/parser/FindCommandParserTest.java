package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;
import seedu.address.model.tag.Tag;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")),
                new TagContainsKeywordsPredicate(Set.of()));
        assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);
    }

    @Test
    public void parse_validTagArgs_returnsFindCommand() {
        // Prepare tag keywords
        Set<Tag> tags = Set.of(new Tag("friend"), new Tag("isRich"));
        FindCommand expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("")),
                new TagContainsKeywordsPredicate(tags)
        );
        assertParseSuccess(parser, " t/friend t/isRich", expectedFindCommand);

        assertParseSuccess(parser, "\n t/friend \n \t t/isRich \t", expectedFindCommand);
    }

    @Test
    public void parse_validNameAndTagArgs_returnsFindCommand() {

        // Expected command
        FindCommand expectedFindCommand = new FindCommand(
                new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")),
                new TagContainsKeywordsPredicate(Set.of(new Tag("friend"), new Tag("isRich")))
        );

        // No extra spaces
        assertParseSuccess(parser, "Alice Bob t/friend t/isRich", expectedFindCommand);

        // With extra spaces and newlines
        assertParseSuccess(parser, " \n Alice \n Bob \t t/friend \n t/isRich", expectedFindCommand);
    }
}
