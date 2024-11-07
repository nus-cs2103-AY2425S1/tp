package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ORDER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DESC_ASC;
import static seedu.address.logic.commands.CommandTestUtil.ORDER_DESC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.address.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FIELD_GITHUB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FIELD_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FIELD_TELEGRAM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SORT_RESET;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SORTORDER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.SortCommand;
import seedu.address.model.person.comparator.ComparatorManager;
import seedu.address.model.person.comparator.SortField;
import seedu.address.model.person.comparator.SortOrder;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();
    private ComparatorManager comparatorManager = new ComparatorManager();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValues_throwsParseException() {
        // invalid field
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + ORDER_DESC_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // invalid order
        assertParseFailure(parser, VALID_FIELD_NAME + INVALID_ORDER_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingValues_throwsParseException() {
        // missing field
        assertParseFailure(parser, PREAMBLE_WHITESPACE + ORDER_DESC_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));

        // missing order
        assertParseFailure(parser, VALID_FIELD_NAME,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_additionalValues_throwsParseException() {
        // reset + order field
        assertParseFailure(parser, VALID_SORT_RESET + ORDER_DESC_ASC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicateValues_throwsParseException() {
        // duplicate order
        assertParseFailure(parser, VALID_FIELD_NAME + ORDER_DESC_DESC + ORDER_DESC_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_SORTORDER));
    }

    @Test
    public void parse_validValues_success() {
        // success for name + asc
        SortCommand expectedFirstCommand =
                new SortCommand(comparatorManager.getComparator(SortField.NAME, SortOrder.ASC));
        assertParseSuccess(parser, VALID_FIELD_NAME + ORDER_DESC_ASC, expectedFirstCommand);

        // success for github + desc
        SortCommand expectedSecondCommand =
                new SortCommand(comparatorManager.getComparator(SortField.GITHUB, SortOrder.DESC));
        assertParseSuccess(parser, VALID_FIELD_GITHUB + ORDER_DESC_DESC, expectedSecondCommand);

        // success for telegram + asc
        SortCommand expectedThirdCommand =
                new SortCommand(comparatorManager.getComparator(SortField.TELEGRAM, SortOrder.ASC));
        assertParseSuccess(parser, VALID_FIELD_TELEGRAM + ORDER_DESC_ASC, expectedThirdCommand);

        SortCommand expectedResetCommand = new SortCommand(null);
        assertParseSuccess(parser, VALID_SORT_RESET , expectedResetCommand);
    }
}
