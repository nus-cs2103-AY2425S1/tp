package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATETIME;

import seedu.address.logic.commands.FindDeliveryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.delivery.DateTime;
import seedu.address.model.delivery.DeliveryDatePredicate;


/**
 * Parses input arguments and creates a new FindDeliveryCommand object
 */

public class FindDeliveryCommandParser implements Parser<FindDeliveryCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindDeliveryCommand
     * and returns a FindDeliveryCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindDeliveryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_DATETIME);

        // Check if the prefix for the date is present and no preamble is provided
        if (!argMultimap.getPreamble().isEmpty() || !argMultimap.getValue(PREFIX_DATETIME).isPresent()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindDeliveryCommand.MESSAGE_USAGE));
        }

        // Extract the date and parse it using the existing ParserUtil
        String dateString = argMultimap.getValue(PREFIX_DATETIME).get();
        DateTime dateTime;
        try {
            dateTime = ParserUtil.parseDateTime(dateString);
        } catch (IllegalArgumentException e) {
            throw new ParseException(DateTime.MESSAGE_CONSTRAINTS);
        }

        return new FindDeliveryCommand(new DeliveryDatePredicate(dateTime));
    }
}
