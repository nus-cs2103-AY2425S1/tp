package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DAY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_MONTH;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_TIME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_YEAR;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_APPLE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_BREAD;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.Assert.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.UpcomingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.DateTime;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryIsUpcomingAfterPredicate;
import seedu.address.model.delivery.DeliveryIsUpcomingBeforePredicate;
import seedu.address.model.delivery.Status;

public class UpcomingCommandParserTest {
    private UpcomingCommandParser parser = new UpcomingCommandParser();

    @Test
    public void parse_missingCompulsoryFields_throwsParseException() {
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpcomingCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_validAfterDate_success() {
        DateTime expectedAfterDateTime = new DateTime(VALID_DATE_APPLE);
        Status expectedStatus = Status.PENDING;
        String userInput = " " + PREFIX_START_DATE + VALID_DATE_APPLE;
        Predicate<Delivery> predicateAfter = new DeliveryIsUpcomingAfterPredicate(expectedAfterDateTime,
                expectedStatus);
        List<Predicate<Delivery>> predicates = new ArrayList<>();
        predicates.add(predicateAfter);
        assertParseSuccess(parser, userInput, new UpcomingCommand(predicates));
    }

    @Test
    public void parse_validBeforeDate_success() {
        DateTime expectedBeforeDateTime = new DateTime(VALID_DATE_BREAD);
        Status expectedStatus = Status.PENDING;
        String userInput = " " + PREFIX_END_DATE + VALID_DATE_BREAD;
        Predicate<Delivery> predicateBefore = new DeliveryIsUpcomingBeforePredicate(expectedBeforeDateTime,
                expectedStatus);
        List<Predicate<Delivery>> predicates = new ArrayList<>();
        predicates.add(predicateBefore);
        assertParseSuccess(parser, userInput, new UpcomingCommand(predicates));
    }

    @Test
    public void parse_validBeforeAndAfterDate_success() {
        DateTime expectedBeforeDateTime = new DateTime(VALID_DATE_BREAD);
        DateTime expectedAfterDateTime = new DateTime(VALID_DATE_APPLE);
        Status expectedStatus = Status.PENDING;
        String userInput = " " + PREFIX_START_DATE + VALID_DATE_APPLE + " " + PREFIX_END_DATE + VALID_DATE_BREAD;
        Predicate<Delivery> predicateBefore = new DeliveryIsUpcomingBeforePredicate(expectedBeforeDateTime,
                expectedStatus);
        Predicate<Delivery> predicateAfter = new DeliveryIsUpcomingAfterPredicate(expectedAfterDateTime,
                expectedStatus);
        List<Predicate<Delivery>> predicates = new ArrayList<>();
        predicates.add(predicateAfter);
        predicates.add(predicateBefore);
        assertParseSuccess(parser, userInput, new UpcomingCommand(predicates));
    }

    @Test
    public void parse_invalidBeforeTimeValue_failure() {
        // invalid time
        assertParseFailure(parser, " " + PREFIX_END_DATE + INVALID_DATE_TIME,
                DateTime.MESSAGE_CONSTRAINTS);

        // invalid year
        assertParseFailure(parser, " " + PREFIX_END_DATE + INVALID_DATE_YEAR,
                DateTime.MESSAGE_CONSTRAINTS);

        // invalid month
        assertParseFailure(parser, " " + PREFIX_END_DATE + INVALID_DATE_MONTH,
                DateTime.MESSAGE_CONSTRAINTS);

        // invalid day
        assertParseFailure(parser, " " + PREFIX_END_DATE + INVALID_DATE_DAY,
                DateTime.MESSAGE_CONSTRAINTS);
    }
    @Test
    public void parse_invalidAfterTimeValue_failure() {
        // invalid time
        assertParseFailure(parser, " " + PREFIX_START_DATE + INVALID_DATE_TIME,
                DateTime.MESSAGE_CONSTRAINTS);

        // invalid year
        assertParseFailure(parser, " " + PREFIX_START_DATE + INVALID_DATE_YEAR,
                DateTime.MESSAGE_CONSTRAINTS);

        // invalid month
        assertParseFailure(parser, " " + PREFIX_START_DATE + INVALID_DATE_MONTH,
                DateTime.MESSAGE_CONSTRAINTS);

        // invalid day
        assertParseFailure(parser, " " + PREFIX_START_DATE + INVALID_DATE_DAY,
                DateTime.MESSAGE_CONSTRAINTS);
    }

    @Test
    public void parse_duplicatePrefixes_throwsParseException() {
        String input = " " + PREFIX_START_DATE + VALID_DATE_APPLE + PREFIX_START_DATE + VALID_DATE_BREAD;
        assertThrows(ParseException.class, () -> parser.parse(input));
    }

}
