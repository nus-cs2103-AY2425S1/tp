package seedu.internbuddy.logic.parser;

import static seedu.internbuddy.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_APP_INDEX;
import static seedu.internbuddy.logic.parser.CliSyntax.PREFIX_COMPANY_INDEX;
import static seedu.internbuddy.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.internbuddy.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.internbuddy.testutil.TypicalIndexes.INDEX_FIRST_APPLICATION;
import static seedu.internbuddy.testutil.TypicalIndexes.INDEX_FIRST_COMPANY;

import org.junit.jupiter.api.Test;

import seedu.internbuddy.logic.Messages;
import seedu.internbuddy.logic.commands.Command;
import seedu.internbuddy.logic.commands.WithdrawCommand;

public class WithdrawCommandParserTest {
    private static final String COMPANY_INDEX_DESC =
            " " + PREFIX_COMPANY_INDEX + INDEX_FIRST_COMPANY.getOneBased();
    private static final String APPLICATION_INDEX_DESC =
            " " + PREFIX_APP_INDEX + INDEX_FIRST_APPLICATION.getOneBased();
    private static final String INVALID_COMPANY_INDEX_DESC_1 =
            " " + PREFIX_COMPANY_INDEX + "-1";
    private static final String INVALID_COMPANY_INDEX_DESC_2 =
            " " + PREFIX_COMPANY_INDEX + "one";
    private static final String INVALID_APPLICATION_INDEX_DESC_1 =
            " " + PREFIX_APP_INDEX + "-1";
    private static final String INVALID_APPLICATION_INDEX_DESC_2 =
            " " + PREFIX_APP_INDEX + "one";

    private WithdrawCommandParser parser = new WithdrawCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Command expectedCommand = new WithdrawCommand(INDEX_FIRST_COMPANY, INDEX_FIRST_APPLICATION);

        assertParseSuccess(parser, COMPANY_INDEX_DESC + APPLICATION_INDEX_DESC, expectedCommand);
    }

    @Test
    public void parse_repeatedValue_failure() {
        // multiple company indexes
        assertParseFailure(parser, COMPANY_INDEX_DESC + COMPANY_INDEX_DESC + APPLICATION_INDEX_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COMPANY_INDEX));

        // multiple application indexes
        assertParseFailure(parser, COMPANY_INDEX_DESC + APPLICATION_INDEX_DESC + APPLICATION_INDEX_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_APP_INDEX));

        // multiple fields repeated
        assertParseFailure(parser, COMPANY_INDEX_DESC + APPLICATION_INDEX_DESC
                        + APPLICATION_INDEX_DESC + COMPANY_INDEX_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_COMPANY_INDEX, PREFIX_APP_INDEX));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, WithdrawCommand.MESSAGE_USAGE);

        // company index missing
        assertParseFailure(parser, INDEX_FIRST_COMPANY + APPLICATION_INDEX_DESC, expectedMessage);

        // application index missing
        assertParseFailure(parser, COMPANY_INDEX_DESC + INDEX_FIRST_APPLICATION, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser,
                String.format("%d %d", INDEX_FIRST_COMPANY.getOneBased(), INDEX_FIRST_APPLICATION.getOneBased()),
                expectedMessage);
    }

    @Test
    public void parse_invalidValues_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, WithdrawCommand.MESSAGE_USAGE);

        // invalid application index
        assertParseFailure(parser, INVALID_COMPANY_INDEX_DESC_1 + APPLICATION_INDEX_DESC, expectedMessage);
        assertParseFailure(parser, INVALID_COMPANY_INDEX_DESC_2 + APPLICATION_INDEX_DESC, expectedMessage);

        // invalid application index
        assertParseFailure(parser, COMPANY_INDEX_DESC + INVALID_APPLICATION_INDEX_DESC_1, expectedMessage);
        assertParseFailure(parser, COMPANY_INDEX_DESC + INVALID_APPLICATION_INDEX_DESC_2, expectedMessage);
    }
}
