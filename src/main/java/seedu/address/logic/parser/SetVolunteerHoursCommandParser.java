package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetVolunteerHoursCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Hours;

/**
 * Parses input arguments and creates a new SetVolunteerHoursCommand object
 */
public class SetVolunteerHoursCommandParser implements Parser<SetVolunteerHoursCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the SetVolunteerHoursCommand
     * and returns a SetVolunteerHoursCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SetVolunteerHoursCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_HOURS);

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FORMAT);
        }

        if (argMultimap.getValue(PREFIX_HOURS).isPresent()) {
            String hoursAsString = argMultimap.getValue(PREFIX_HOURS).get();
            if (!Hours.isValidHours(hoursAsString)) {
                throw new ParseException(Hours.MESSAGE_CONSTRAINTS);
            }
            return new SetVolunteerHoursCommand(index, hoursAsString);
        }

        throw new ParseException(SetVolunteerHoursCommand.MESSAGE_NOT_EDITED);
    }
}
