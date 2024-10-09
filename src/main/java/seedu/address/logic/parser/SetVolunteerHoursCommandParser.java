package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetVolunteerHoursCommand;
import seedu.address.logic.parser.exceptions.ParseException;

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
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT));
        }

        if (argMultimap.getValue(PREFIX_HOURS).isPresent()) {
            String hoursAsString = argMultimap.getValue(PREFIX_HOURS).get();
            int hoursAsInt;
            try {
                hoursAsInt = Integer.parseInt(hoursAsString);
            } catch (NumberFormatException nfe) {
                throw new ParseException(SetVolunteerHoursCommand.MESSAGE_NOT_EDITED);
            }
            return new SetVolunteerHoursCommand(index, hoursAsInt);
        }

        throw new ParseException(SetVolunteerHoursCommand.MESSAGE_NOT_EDITED);
    }
}
