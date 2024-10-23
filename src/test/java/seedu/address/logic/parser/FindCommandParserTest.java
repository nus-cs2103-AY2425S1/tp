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

    @Test
    public void parse_invalidArg_throwsParseException() {
        // Test with completely empty input
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        // Test with no prefix, should throw a ParseException
        assertParseFailure(parser, "Alice CS2103T",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        // Test with a blank name keyword, should throw a ParseException
        assertParseFailure(parser, " " + PREFIX_NAME + " ",
                MESSAGE_EMPTY_FIND_KEYWORD);
        // Test with a blank module keyword, should throw a ParseException
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

}

