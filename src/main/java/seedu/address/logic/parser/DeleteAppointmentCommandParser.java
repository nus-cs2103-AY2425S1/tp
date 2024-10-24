package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.appointment.Appointment;

/**
 * Parses input arguments and creates a new DeleteAppointmentCommand object.
 */
public class DeleteAppointmentCommandParser implements Parser<DeleteAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteAppointmentCommand
     * and returns a DeleteAppointmentCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    @Override
    public DeleteAppointmentCommand parse(String args) throws ParseException {
        Index index;
        try {
            index = ParserUtil.parseIndex(args.trim());
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE), pe);
        }

        Integer id = index.getOneBased();
        Appointment appointment = Appointment.getAppointmentById(id);

        if (appointment == null) {
            // ID format is correct but no appointment with this ID exists
            throw new ParseException(DeleteAppointmentCommand.MESSAGE_INVALID_APPOINTMENT_ID);
        }
        return new DeleteAppointmentCommand(appointment);
    }
}
