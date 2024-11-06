package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.APPOINTMENT_ENTITY_STRING;
import static seedu.address.logic.parser.ParserUtil.PERSON_ENTITY_STRING;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.DeletePersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {
    private static final int MINIMUM_ARGS_LENGTH = 2;
    private static final int ENTITY_TYPE_INDEX = 0;
    private static final int TARGET_INDEX_POSITION = 1;
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {

        String[] splitArgs = validateAndSplitArgs(args);
        String entityType = splitArgs[ENTITY_TYPE_INDEX];

        switch (entityType) {
        case PERSON_ENTITY_STRING:
            return parsePersonDelete(splitArgs);
        case APPOINTMENT_ENTITY_STRING:
            return parseAppointmentDelete(splitArgs);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
    }

    private DeleteAppointmentCommand parseAppointmentDelete(String[] splitArgs) throws ParseException {
        if (splitArgs.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE));
        }
        String apptIndexString = splitArgs[TARGET_INDEX_POSITION];

        Index index = ParserUtil.parseIndex(apptIndexString);
        return new DeleteAppointmentCommand(index);
    }

    private DeletePersonCommand parsePersonDelete(String[] splitArgs) throws ParseException {
        if (splitArgs.length < 2) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePersonCommand.MESSAGE_USAGE));
        }
        String personIndexString = splitArgs[TARGET_INDEX_POSITION];

        Index index = ParserUtil.parseIndex(personIndexString);
        return new DeletePersonCommand(index);
    }

    private String[] validateAndSplitArgs(String args) throws ParseException {
        String[] splitArgs = args.trim().split("\\s+");

        if (splitArgs.length < 1) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        return splitArgs;
    }

}
