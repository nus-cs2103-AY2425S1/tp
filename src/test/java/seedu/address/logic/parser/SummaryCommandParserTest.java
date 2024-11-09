package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_INVALID_DATE_RANGE;
import static seedu.address.logic.Messages.MESSAGE_INVALID_MONTH_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.time.YearMonth;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.SummaryCommand;
import seedu.address.model.client.TransactionDatePredicate;

public class SummaryCommandParserTest {
    private SummaryCommandParser parser = new SummaryCommandParser();
    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingParts_throwsParseException() {
        //no start month
        String userInputWithoutStartMonth = "       e/2020-11        ";
        assertParseFailure(parser, userInputWithoutStartMonth,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE));
        //no end month
        String userInputWithoutEndMonth = "        s/2022-05       ";
        assertParseFailure(parser, userInputWithoutEndMonth,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SummaryCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidMonths_throwsParseException() {
        // invalid format
        String invalidMonthFormat1 = "        s/2022-5       e/2022-05       ";
        String invalidMonthFormat2 = "        s/2022-05       e/5/100       ";
        assertParseFailure(parser, invalidMonthFormat1, MESSAGE_INVALID_MONTH_FORMAT);
        assertParseFailure(parser, invalidMonthFormat2, MESSAGE_INVALID_MONTH_FORMAT);

        // start month after end month
        String startAfterEnd = "        s/2022-05       e/2022-04       ";
        assertParseFailure(parser, startAfterEnd, MESSAGE_INVALID_DATE_RANGE);
    }

    @Test
    public void parse_validArgs_returnsSummaryCommand() {
        String validArgs = "        s/2022-05       e/2022-06       ";
        SummaryCommand expectedSummaryCommand = new SummaryCommand(
                new TransactionDatePredicate(YearMonth.parse("2022-05"), YearMonth.parse("2022-06")));
        assertParseSuccess(parser, validArgs, expectedSummaryCommand);
    }
}
