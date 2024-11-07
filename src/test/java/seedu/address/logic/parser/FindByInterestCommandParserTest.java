package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;
import java.util.Collections;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindByInterestCommand;
import seedu.address.model.person.InterestContainsKeywordsPredicate;


public class FindByInterestCommandParserTest {

    private final FindByInterestCommandParser parser = new FindByInterestCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        // Test for empty input
        assertParseFailure(parser, " ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByInterestCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validSingleOrKeyword_returnsFindByInterestCommand() {
        // Test with valid single OR keyword input
        FindByInterestCommand expectedCommand = new FindByInterestCommand(
                new InterestContainsKeywordsPredicate(Collections.emptyList(), Collections.singletonList("Reading")));
        assertParseSuccess(parser, "i/Reading", expectedCommand);
    }

    @Test
    public void parse_validMultipleOrKeywords_returnsFindByInterestCommand() {
        // Test with multiple OR keywords
        FindByInterestCommand expectedCommand = new FindByInterestCommand(
                new InterestContainsKeywordsPredicate(Collections.emptyList(), Arrays.asList("Reading", "Writing")));
        assertParseSuccess(parser, "i/Reading i/Writing", expectedCommand);
    }

    @Test
    public void parse_validAndKeywords_returnsFindByInterestCommand() {
        // Test with AND keywords (comma-separated in a single "i/" group)
        FindByInterestCommand expectedCommand = new FindByInterestCommand(
                new InterestContainsKeywordsPredicate(Collections.singletonList(Arrays.asList("Reading", "Writing")),
                        Collections.emptyList()));
        assertParseSuccess(parser, "i/Reading,Writing", expectedCommand);
    }

    @Test
    public void parse_validAndOrCombination_returnsFindByInterestCommand() {
        // Test with a combination of AND and OR keywords
        FindByInterestCommand expectedCommand = new FindByInterestCommand(
                new InterestContainsKeywordsPredicate(
                        Collections.singletonList(Arrays.asList("Reading", "Writing")),
                        Arrays.asList("Sports", "Music")));
        assertParseSuccess(parser, "i/Reading,Writing i/Sports i/Music", expectedCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Test for missing prefix (e.g., 'i/')
        assertParseFailure(parser, "Reading", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FindByInterestCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_multipleSpaces_returnsTrimmedFindByInterestCommand() {
        // Test input with multiple spaces
        FindByInterestCommand expectedCommand = new FindByInterestCommand(
                new InterestContainsKeywordsPredicate(Collections.emptyList(),
                Collections.singletonList("Photography")));
        assertParseSuccess(parser, " i/   Photography    ", expectedCommand);
    }

    @Test
    public void parse_mixedComplexInput_returnsFindByInterestCommand() {
        // Test with complex input including multiple AND and OR conditions with extra spaces
        FindByInterestCommand expectedCommand = new FindByInterestCommand(
                new InterestContainsKeywordsPredicate(
                        Arrays.asList(
                                Arrays.asList("Photography", "Travel"),
                                Arrays.asList("Cooking", "Baking")
                        ),
                        Arrays.asList("Music", "Dancing") // OR group
                ));
        assertParseSuccess(parser, "i/Photography,Travel i/Music i/Dancing i/Cooking,Baking", expectedCommand);
    }
}
