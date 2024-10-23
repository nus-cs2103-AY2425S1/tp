package seedu.address.logic.parser;

import seedu.address.logic.commands.UpcomingCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.DateTime;
import seedu.address.model.delivery.DeliveryIsUpcomingPredicate;
import seedu.address.model.delivery.Status;

/**
 * Parses input {@code String} arguments and creates a new UpcomingCommand object
 *
 *  @throws ParseException if the user input does not conform the expected format
 */
public class UpcomingCommandParser implements Parser<UpcomingCommand> {
    @Override
    public UpcomingCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        DateTime completionDateTime = ParserUtil.parseDateTime(trimmedArgs);
        DeliveryIsUpcomingPredicate predicate = new DeliveryIsUpcomingPredicate(completionDateTime, Status.PENDING);
        return new UpcomingCommand(predicate);
    }
}
