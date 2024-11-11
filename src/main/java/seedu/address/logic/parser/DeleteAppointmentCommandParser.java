package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteAppointmentCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteAppointmentCommand object.
 */
public class DeleteAppointmentCommandParser implements Parser<DeleteAppointmentCommand> {

    private static final Logger logger = LogsCenter.getLogger(DeleteAppointmentCommandParser.class);

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteAppointmentCommand parse(String args) throws ParseException {
        logger.info("Parsing Delete Appointment Command Inputs!");
        try {
            Index index = ParserUtil.parseIndex(args);
            logger.info("Successfully parsed Delete Appointment Command Inputs!");
            return new DeleteAppointmentCommand(index);
        } catch (ParseException pe) {
            logger.warning("Error Parsing Delete Appointment Command Inputs!");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteAppointmentCommand.MESSAGE_USAGE), pe);
        }
    }
}
