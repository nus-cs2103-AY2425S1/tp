package seedu.address.logic.parser;

import static seedu.address.logic.parser.CliSyntax.PREFIX_CLOSINGHOURS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OPENINGHOURS;
import static seedu.address.logic.parser.ParserUtil.parseTime;

import seedu.address.logic.commands.UpdateOperatingHoursCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.OperatingHours;

import java.time.LocalTime;

/**
 * Parses input arguments and creates a new UpdateOperatingHoursCommand object.
 */
public class UpdateOperatingHoursCommandParser implements Parser<UpdateOperatingHoursCommand> {

    @Override
    public UpdateOperatingHoursCommand parse(String args) throws ParseException {

        // empty arguments will reset the time
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_OPENINGHOURS, PREFIX_CLOSINGHOURS);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_OPENINGHOURS, PREFIX_CLOSINGHOURS);

        LocalTime openingHours = null;
        LocalTime closingHours = null;

        if (!argMultimap.getValue(PREFIX_OPENINGHOURS).isEmpty()) {
            openingHours = parseTime(argMultimap.getValue(PREFIX_OPENINGHOURS).get());
        }

        if (!argMultimap.getValue(PREFIX_CLOSINGHOURS).isEmpty()) {
            closingHours = parseTime(argMultimap.getValue(PREFIX_CLOSINGHOURS).get());
        }

        return new UpdateOperatingHoursCommand(new OperatingHours(openingHours, closingHours));
    }
}
