package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_EMPTY_INDEX;
import static seedu.address.logic.Messages.MESSAGE_EMPTY_MONTH_PAID;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MONTHPAID;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.Set;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UnmarkPaidCommand;
import seedu.address.model.person.MonthPaid;

public class UnmarkPaidCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnmarkPaidCommand.MESSAGE_USAGE);

    private static final String VALID_MONTHPAID1 = "2024-01";
    private static final String VALID_MONTHPAID2 = "2024-02";
    private static final String INVALID_MONTHPAID_YY = "24-01";
    private static final String INVALID_MONTHPAID_MM_RANGE_UPPER = "2024-13";
    private static final String INVALID_MONTHPAID_MM_RANGE_LOWER = "2024-00";
    private static final String INVALID_MONTHPAID_YYYY_RANGE_UPPER = "2201-12";
    private static final String INVALID_MONTHPAID_YYYY_RANGE_LOWER = "1899-12";

    private UnmarkPaidCommandParser parser = new UnmarkPaidCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_EMPTY_INDEX);
    }
    @Test
    public void parse_missingField_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = String.valueOf(targetIndex.getOneBased());
        String expectedMessage = String.format(MESSAGE_EMPTY_MONTH_PAID, UnmarkPaidCommand.MESSAGE_USAGE);

        // Since no prefixes are provided, we expect a parse failure
        assertParseFailure(parser, userInput, expectedMessage);
    }
    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 " + withPrefix(VALID_MONTHPAID1), MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // zero index
        assertParseFailure(parser, "0 " + withPrefix(VALID_MONTHPAID2), MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_EMPTY_MONTH_PAID);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_EMPTY_MONTH_PAID);
    }
    @Test
    public void parse_oneField_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = String.valueOf(targetIndex.getOneBased()) + " " + withPrefix(VALID_MONTHPAID1);
        // empty field
        UnmarkPaidCommand expectedCommand = new UnmarkPaidCommand(
                UnmarkPaidCommand.UnmarkPaidTarget.fromIndex(targetIndex),
                Set.of(new MonthPaid(VALID_MONTHPAID1)));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_twoFields_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = String.valueOf(targetIndex.getOneBased()) + " " + withPrefix(VALID_MONTHPAID1)
                + " " + withPrefix(VALID_MONTHPAID2);
        // empty field
        UnmarkPaidCommand expectedCommand = new UnmarkPaidCommand(
                UnmarkPaidCommand.UnmarkPaidTarget.fromIndex(targetIndex), Set.of(new MonthPaid(VALID_MONTHPAID1),
                    new MonthPaid(VALID_MONTHPAID2)));

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_oneInvalidField_failure() {
        // 1 invalid field of type YY-MM
        assertParseFailure(parser, "1 " + withPrefix(INVALID_MONTHPAID_YY), MonthPaid.MESSAGE_CONSTRAINTS);

        // MM is more than 12 and mm is 0
        assertParseFailure(parser, "1 "
                + withPrefix(INVALID_MONTHPAID_MM_RANGE_UPPER), MonthPaid.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1 "
                + withPrefix(INVALID_MONTHPAID_MM_RANGE_LOWER), MonthPaid.MESSAGE_CONSTRAINTS);

        // YYYY-MM where YYYY is less than 1900 and more than 2100
        assertParseFailure(parser, "1 "
                + withPrefix(INVALID_MONTHPAID_YYYY_RANGE_UPPER), MonthPaid.MESSAGE_CONSTRAINTS);
        assertParseFailure(parser, "1 "
                + withPrefix(INVALID_MONTHPAID_YYYY_RANGE_LOWER), MonthPaid.MESSAGE_CONSTRAINTS);

        // 1 invalid field, 1 valid field
        assertParseFailure(parser, "1 "
                + withPrefix(INVALID_MONTHPAID_MM_RANGE_LOWER)
                + " " + withPrefix(VALID_MONTHPAID2), MonthPaid.MESSAGE_CONSTRAINTS);
    }

    private String withPrefix(String monthPaidString) {
        return PREFIX_MONTHPAID + monthPaidString;
    }

}
