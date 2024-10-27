package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.APPOINTMENT_ENTITY_STRING;
import static seedu.address.logic.parser.ParserUtil.PERSON_ENTITY_STRING;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand<?>> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     *
     * @param  args user input
     * @return findCommand object for execution
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String entityType = getEntity(trimmedArgs);

        switch (entityType) {
        case PERSON_ENTITY_STRING:
            return new FindPersonCommandParser().parse(trimmedArgs);
        case APPOINTMENT_ENTITY_STRING:
            return new FindAppointmentCommandParser().parse(trimmedArgs);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    private String getEntity(String args) {
        String[] nameKeywords = args.split("\\s+");
        return nameKeywords[0];
    }

}
