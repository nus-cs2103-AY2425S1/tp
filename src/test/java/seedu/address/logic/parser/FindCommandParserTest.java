package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_FIND_KEYWORD;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ModuleRoleContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;

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
        // 1
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        // 2
        assertParseFailure(parser, "Alice " + PREFIX_MODULE + "CS2103T",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        // 3
        assertParseFailure(parser, "CS2103T " + PREFIX_NAME + "Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        // 4
        assertParseFailure(parser, "random string " + PREFIX_NAME + "Alice",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        // 5
        assertParseFailure(parser, " " + PREFIX_NAME + " ",
                MESSAGE_EMPTY_FIND_KEYWORD);
        // 6
        assertParseFailure(parser, " " + PREFIX_MODULE + " ",
                MESSAGE_EMPTY_FIND_KEYWORD);
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

    @Test void parse_chained_returnsFindCommand() throws ParseException {
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

}

