package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortCommand.MESSAGE_NOT_SORTED;
import static seedu.address.logic.commands.SortCommand.MESSAGE_USAGE;
import static seedu.address.logic.commands.SortCommand.MESSAGE_WRONG_NUM_OF_FIELDS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENT_ID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SortCommand;

public class SortCommandParserTest {
    private SortCommandParser parser = new SortCommandParser();

    private String generateSortNameInput(int order) {
        return String.format("%d %s", order, PREFIX_NAME);
    }

    private String generateSortIdInput(int order) {
        return String.format("%d %s", order, PREFIX_STUDENT_ID);
    }

    @Test
    public void parse_validSortByNameAscending_success() {
        String userInput = generateSortNameInput(SortCommand.ASCENDING);
        SortCommand expectedCommand = SortCommand.sortByName(SortCommand.ASCENDING);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validSortByNameDescending_success() {
        String userInput = generateSortNameInput(SortCommand.DESCENDING);
        SortCommand expectedCommand = SortCommand.sortByName(SortCommand.DESCENDING);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validSortByStudentIdAscending_success() {
        String userInput = generateSortIdInput(SortCommand.ASCENDING);
        SortCommand expectedCommand = SortCommand.sortByStudentId(SortCommand.ASCENDING);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validSortByStudentIdDescending_success() {
        String userInput = generateSortIdInput(SortCommand.DESCENDING);
        SortCommand expectedCommand = SortCommand.sortByStudentId(SortCommand.DESCENDING);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noPrefixProvided_failure() {
        assertParseFailure(parser, "1", MESSAGE_NOT_SORTED);
    }

    @Test
    public void parse_multiplePrefixesProvided_failure() {
        String userInput = generateSortNameInput(SortCommand.ASCENDING) + " " + PREFIX_STUDENT_ID;
        assertParseFailure(parser, userInput, MESSAGE_WRONG_NUM_OF_FIELDS);
    }

    @Test
    public void parse_emptyInput_failure() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    @Test
    public void parse_whitespaceInput_failure() {
        assertParseFailure(parser, "   ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }
}
