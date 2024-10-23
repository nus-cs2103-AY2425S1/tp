package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.Nric;

/**
 * Parses input arguments and creates a new DeleteAppointmentCommand object
 */
public class DeleteAppointmentCommandParser implements Parser<DeleteAppointmentCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAppointmentCommand parse(String args) throws ParseException {
        String[] splitArgs = args.trim().split(" ");

        if (!(splitArgs.length == 3)) {
            throw new ParseException(DeleteAppointmentCommand.MESSAGE_USAGE);
        }

        // Check if the first argument is NRIC or index
        try {
            Nric nric = ParserUtil.parseNric(splitArgs[0]);
            Appointment appointment = ParserUtil.parseAppointment(splitArgs[1] + " " + splitArgs[2]);
            return new DeleteAppointmentCommand(nric, appointment);
        } catch (ParseException pe) {
            try {
                Index index = ParserUtil.parseIndex(splitArgs[0].trim());
                Appointment appointment = ParserUtil.parseAppointment(splitArgs[1] + " " + splitArgs[2]);
                return new DeleteAppointmentCommand(index, appointment);
            } catch (ParseException indexParseException) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE),
                        indexParseException);
            }
        }

    }

}
