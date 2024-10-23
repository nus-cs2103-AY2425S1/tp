package seedu.address.logic.parser;

import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DAY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_MONTH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_APPLE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpcomingCommand;
import seedu.address.model.delivery.DateTime;
import seedu.address.model.delivery.DeliveryIsUpcomingPredicate;
import seedu.address.model.delivery.Status;

public class UpcomingCommandParserTest {
    private UpcomingCommandParser parser = new UpcomingCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        DateTime expectedDateTime = new DateTime(VALID_DATE_APPLE);
        Status expectedStatus = Status.PENDING;
        assertParseSuccess(parser, VALID_DATE_APPLE, new UpcomingCommand(
                new DeliveryIsUpcomingPredicate(expectedDateTime, expectedStatus)));


        // white space between command and date
        assertParseSuccess(parser, "   " + VALID_DATE_APPLE, new UpcomingCommand(
                        new DeliveryIsUpcomingPredicate(expectedDateTime, expectedStatus)));

        // white space at end of date
        assertParseSuccess(parser, VALID_DATE_APPLE + "   ", new UpcomingCommand(
                new DeliveryIsUpcomingPredicate(expectedDateTime, expectedStatus)));
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid time
        assertParseFailure(parser, INVALID_DATE_TIME,
                DateTime.MESSAGE_CONSTRAINTS);

        // invalid year
        assertParseFailure(parser, INVALID_DATE_YEAR,
                DateTime.MESSAGE_CONSTRAINTS);

        // invalid month
        assertParseFailure(parser, INVALID_DATE_MONTH,
                DateTime.MESSAGE_CONSTRAINTS);

        // invalid day
        assertParseFailure(parser, INVALID_DATE_DAY,
                DateTime.MESSAGE_CONSTRAINTS);
    }
}
