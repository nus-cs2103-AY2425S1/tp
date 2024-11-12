package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_DATE;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.UpcomingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.DateTime;
import seedu.address.model.delivery.Delivery;
import seedu.address.model.delivery.DeliveryIsUpcomingAfterPredicate;
import seedu.address.model.delivery.DeliveryIsUpcomingBeforePredicate;
import seedu.address.model.delivery.Status;

/**
 * Parses input {@code String} arguments and creates a new UpcomingCommand object.
 */
public class UpcomingCommandParser implements Parser<UpcomingCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the UpcomingCommand
     * and returns an UpcomingCommand object for execution.
     *
     * @param args Input String parameters provided by user.
     * @return UpcomingCommand object.
     * @throws ParseException If the user input does not conform to the expected format.
     */
    @Override
    public UpcomingCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_START_DATE, PREFIX_END_DATE);

        if (!argMultimap.getValue(PREFIX_START_DATE).isPresent()
                && !argMultimap.getValue(PREFIX_END_DATE).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpcomingCommand.MESSAGE_USAGE));
        }
        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_START_DATE, PREFIX_END_DATE);
        List<Predicate<Delivery>> predicates = new ArrayList<>();
        if (argMultimap.getValue(PREFIX_START_DATE).isPresent()) {
            addUpcomingAfterPredicate(argMultimap, predicates);
        }
        if (argMultimap.getValue(PREFIX_END_DATE).isPresent()) {
            addUpcomingBeforePredicate(argMultimap, predicates);
        }
        assert !predicates.isEmpty();
        return new UpcomingCommand(predicates);
    }

    /**
     * Adds an UpcomingAfterPredicate to the list of Predicates provided.
     *
     * @param argMultimap Map that stores the mapping of prefixes to their respective arguments.
     * @param predicates List of Predicates used to filter the Delivery list.
     * @throws ParseException If the DateTime input provided by the user is invalid.
     */
    public void addUpcomingAfterPredicate(ArgumentMultimap argMultimap, List<Predicate<Delivery>> predicates)
            throws ParseException {
        DateTime deliveryStartDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_START_DATE).get());
        DeliveryIsUpcomingAfterPredicate upcomingAfterPredicate = new DeliveryIsUpcomingAfterPredicate(
                deliveryStartDateTime, Status.PENDING);
        predicates.add(upcomingAfterPredicate);
    }
    /**
     * Adds an UpcomingBeforePredicate to the list of Predicates provided.
     *
     * @param argMultimap Map that stores the mapping of prefixes to their respective arguments.
     * @param predicates List of Predicates used to filter the Delivery list.
     * @throws ParseException If the DateTime input provided by the user is invalid.
     */
    public void addUpcomingBeforePredicate(ArgumentMultimap argMultimap, List<Predicate<Delivery>> predicates)
            throws ParseException {
        DateTime deliveryEndDateTime = ParserUtil.parseDateTime(argMultimap.getValue(PREFIX_END_DATE).get());
        DeliveryIsUpcomingBeforePredicate upcomingBeforePredicate = new
                DeliveryIsUpcomingBeforePredicate(deliveryEndDateTime, Status.PENDING);
        predicates.add(upcomingBeforePredicate);
    }
}
