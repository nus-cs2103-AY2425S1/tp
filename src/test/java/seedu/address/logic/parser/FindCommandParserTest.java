package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_FIND_KEYWORD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ModuleRoleContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

public class FindCommandParserTest {

    private final FindCommandParser parser = new FindCommandParser();

    // Equivalence Partitions:

    // Name keyword
    // 1. empty string
    // 2. no prefix (-)
    // 3. prefix only (-)
    // 4. valid name keyword
    // 5. multiple name keywords

    // Module keyword
    // 1. empty string
    // 2. no prefix (-)
    // 3. prefix only (-)
    // 4. valid module keyword
    // 5. multiple module keywords

    // Preamble
    // 1. empty string
    // 2. "chained"
    // 3. other non-empty string (-)

    // Using the at least one strategy, no more than one heuristic and at least once heuristic to derive test cases:
    //    Name          Module          Preamble
    // negative cases:
    // 1. empty         empty           1/2
    // 2. no prefix     4/5             1/2
    // 3. 4/5           no prefix       1/2
    // 4. 4/5           4/5             other non-empty string
    // 5. prefix only   4/5             1/2
    // 6. 4/5           prefix only     1/2
    // positive cases:
    // 7. valid         empty           empty
    // 8. empty         valid           empty
    // 9. multiple      multiple        empty
    // 10.valid         valid           chained


    @Test
    public void parse_invalidArg_throwsParseException() {
        // Missing search keywords
        assertParseFailure(parser, "     ",
                Messages.getErrorMessageWithUsage(FindCommandParser.MESSAGE_MISSING_SEARCH_KEYWORD,
                        FindCommand.MESSAGE_USAGE));
        // Unexpected preamble(name)
        assertParseFailure(parser, "Alice " + PREFIX_MODULE + "CS2103T",
                Messages.getErrorMessageWithUsage(Messages.MESSAGE_UNEXPECTED_PREAMBLE, FindCommand.MESSAGE_USAGE));
        // Unexpected preamble(module)
        assertParseFailure(parser, "CS2103T " + PREFIX_NAME + "Alice",
                Messages.getErrorMessageWithUsage(Messages.MESSAGE_UNEXPECTED_PREAMBLE, FindCommand.MESSAGE_USAGE));
        // Unexpected preamble(tag)
        assertParseFailure(parser, "CS2103T " + PREFIX_TAG + "Alice",
            Messages.getErrorMessageWithUsage(Messages.MESSAGE_UNEXPECTED_PREAMBLE, FindCommand.MESSAGE_USAGE));
        // Unexpected preamble(random input)
        assertParseFailure(parser, "random string " + PREFIX_NAME + "Alice",
                Messages.getErrorMessageWithUsage(Messages.MESSAGE_UNEXPECTED_PREAMBLE, FindCommand.MESSAGE_USAGE));
        // Blank keywords for name
        assertParseFailure(parser, " " + PREFIX_NAME + " ",
                Messages.getErrorMessageWithUsage(MESSAGE_EMPTY_FIND_KEYWORD, FindCommand.MESSAGE_USAGE));
        // Blank keywords for module
        assertParseFailure(parser, " " + PREFIX_MODULE + " ",
                Messages.getErrorMessageWithUsage(MESSAGE_EMPTY_FIND_KEYWORD, FindCommand.MESSAGE_USAGE));
        // Blank keywords for tag
        assertParseFailure(parser, " " + PREFIX_TAG + " ",
            Messages.getErrorMessageWithUsage(MESSAGE_EMPTY_FIND_KEYWORD, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validNameArg_returnsFindCommand() throws ParseException {
        // Test with valid name keyword
        FindCommand expectedFindCommand =
                new FindCommand(new ArrayList<>(List.of(
                        new NameContainsKeywordsPredicate(List.of("Alice"))
                )));
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice", expectedFindCommand);

        // Test with multiple whitespaces between the name keywords
        assertParseSuccess(parser, " \n " + PREFIX_NAME + "Alice \n \t",
                expectedFindCommand);
    }

    @Test
    public void parse_validModuleRoleArg_returnsFindCommand() throws ParseException {
        // Test with valid module role keyword
        FindCommand expectedFindCommand =
                new FindCommand(new ArrayList<>(List.of(
                        new ModuleRoleContainsKeywordsPredicate(ParserUtil.parseModuleRolePairs(List.of("CS2103T")))
                )));
        assertParseSuccess(parser, " " + PREFIX_MODULE + "CS2103T", expectedFindCommand);

        // Test with multiple whitespaces between the module role keywords
        assertParseSuccess(parser, " \n " + PREFIX_MODULE + "CS2103T \n \t",
                expectedFindCommand);
    }
    @Test
    public void parse_validNameAndModuleRoleArgs_returnsFindCommand() throws ParseException {
        // Test with both name and module role keywords
        FindCommand expectedFindCommand =
                new FindCommand(new ArrayList<>(Arrays.asList(
                        new NameContainsKeywordsPredicate(List.of("Alice")),
                        new ModuleRoleContainsKeywordsPredicate(ParserUtil.parseModuleRolePairs(
                                List.of("CS2103T")))
                )));
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice " + PREFIX_MODULE + "CS2103T", expectedFindCommand);

        // Test with keywords and multiple whitespaces between keywords
        assertParseSuccess(parser, " \n " + PREFIX_NAME + "Alice \n \t " + PREFIX_MODULE + "CS2103T  \t",
                expectedFindCommand);

        // Test with multiple name and module role keywords
        expectedFindCommand = new FindCommand(new ArrayList<>(Arrays.asList(
                        new NameContainsKeywordsPredicate(List.of("Alice", "Bob")),
                        new ModuleRoleContainsKeywordsPredicate(ParserUtil.parseModuleRolePairs(
                                List.of("CS2101", "CS2103T-TA")))
                )));
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice " + PREFIX_NAME + "Bob " + PREFIX_MODULE
                + "CS2101 " + PREFIX_MODULE + "CS2103T-TA", expectedFindCommand);
    }

    @Test
    public void parse_chained_returnsFindCommand() throws ParseException {
        // Test with chained keyword
        FindCommand expectedFindCommand =
                new FindCommand(List.of(
                        new NameContainsKeywordsPredicate(List.of("Alice")),
                        new ModuleRoleContainsKeywordsPredicate(ParserUtil.parseModuleRolePairs(
                                List.of("CS2103T")))
                ), true);
        assertParseSuccess(parser, " " + FindCommand.CHAINED + " " + PREFIX_NAME
                        + "Alice " + PREFIX_MODULE + "CS2103T",
                expectedFindCommand);

        // Test with multiple whitespaces between the name keywords and chained keyword
        assertParseSuccess(parser, " " + FindCommand.CHAINED
                        + " \n " + PREFIX_NAME + "Alice \n \t " + PREFIX_MODULE + "CS2103T  \t",
                expectedFindCommand);
    }

    @Test
    public void parse_validTag_returnsFindCommand() {
        FindCommand expectedFindCommand =
            new FindCommand(List.of(new TagContainsKeywordsPredicate(List.of("school"))),
                true);
        assertParseSuccess(parser, " " + PREFIX_TAG + "school", expectedFindCommand);
    }

    @Test
    public void parse_validTags_returnsFindCommand() {
        FindCommand expectedFindCommand =
            new FindCommand(List.of(new TagContainsKeywordsPredicate(List.of("school", "office", "finance"))),
                true);
        assertParseSuccess(parser, " " + PREFIX_TAG + "school "
            + PREFIX_TAG + "office " + PREFIX_TAG + "finance", expectedFindCommand);
    }

    @Test
    public void parse_validNameAndTags_returnsFindCommand() {
        FindCommand expectedFindCommand =
            new FindCommand(List.of(
                new NameContainsKeywordsPredicate(List.of("Alice")),
                new TagContainsKeywordsPredicate(List.of("school", "office", "finance"))),
                false);
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice " + PREFIX_TAG + "school "
            + PREFIX_TAG + "office " + PREFIX_TAG + "finance", expectedFindCommand);
    }

    @Test
    public void parse_validModuleRoleArgsAndTags_returnsFindCommand() throws ParseException {
        FindCommand expectedFindCommand =
            new FindCommand(List.of(
                new TagContainsKeywordsPredicate(List.of("school", "office", "finance")),
                new ModuleRoleContainsKeywordsPredicate(ParserUtil.parseModuleRolePairs(
                    List.of("CS2103T")))
            ));
        assertParseSuccess(parser, " " + PREFIX_MODULE + "CS2103T "
            + PREFIX_TAG + "school " + PREFIX_TAG + "office " + PREFIX_TAG + "finance",
            expectedFindCommand);
    }

    @Test
    public void parse_validArgs_returnsFindCommand() throws ParseException {
        FindCommand expectedFindCommand =
            new FindCommand(List.of(
                new NameContainsKeywordsPredicate(List.of("Alice")),
                new TagContainsKeywordsPredicate(List.of("school", "office", "finance")),
                new ModuleRoleContainsKeywordsPredicate(ParserUtil.parseModuleRolePairs(
                    List.of("CS2103T")))
            ));
        assertParseSuccess(parser, " " + PREFIX_NAME + "Alice "
                + PREFIX_MODULE + "CS2103T "
                + PREFIX_TAG + "school " + PREFIX_TAG + "office " + PREFIX_TAG + "finance",
            expectedFindCommand);
    }
}

