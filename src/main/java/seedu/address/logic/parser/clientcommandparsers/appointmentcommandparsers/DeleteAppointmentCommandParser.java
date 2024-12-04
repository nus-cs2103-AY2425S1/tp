package seedu.address.logic.parser.clientcommandparsers.appointmentcommandparsers;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.clientcommands.appointmentcommands.DeleteAppointmentCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeleteAppointmentCommand} object.
 */
public class DeleteAppointmentCommandParser implements Parser<DeleteAppointmentCommand> {

    /**
     * Parses the given {@code String} of arguments and returns a {@code DeleteAppointmentCommand} object for execution.
     *
     * @param args User input containing the client's name prefixed with "n/".
     * @return A {@code DeleteAppointmentCommand} object.
     * @throws ParseException If the user input does not conform to the expected format or the name is missing.
     */
    public DeleteAppointmentCommand parse(String args) throws ParseException {
        ArgumentMultimap argumentMultimap = ArgumentTokenizer.tokenize(args);

        Index index = ParserUtil.parseIndexWithInvalidCommandFormatMessage(argumentMultimap.getPreamble(),
                DeleteAppointmentCommand.MESSAGE_USAGE);
        return new DeleteAppointmentCommand(index);
    }
}
