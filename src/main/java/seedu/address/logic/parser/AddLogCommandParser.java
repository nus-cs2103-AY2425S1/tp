package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AddLogCommand.MESSAGE_INVALID_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOG;

import seedu.address.logic.commands.AddLogCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.log.AppointmentDate;
import seedu.address.model.log.Log;
import seedu.address.model.log.LogEntry;
import seedu.address.model.person.IdentityNumber;

/**
 * Parses input arguments and creates a new AddLogCommand object
 */
public class AddLogCommandParser implements Parser<AddLogCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddLogCommand
     * and returns an AddLogCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLogCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_IDENTITY_NUMBER, PREFIX_LOG, PREFIX_DATE);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_IDENTITY_NUMBER, PREFIX_LOG, PREFIX_DATE);

        // Check if all fields' prefix are present
        if (argMultimap.getValue(PREFIX_IDENTITY_NUMBER).isEmpty() || argMultimap.getValue(PREFIX_LOG).isEmpty()
                || argMultimap.getValue(PREFIX_DATE).isEmpty() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLogCommand.MESSAGE_USAGE));
        }

        // Check if identity number exists
        IdentityNumber identityNumber;
        try {
            // Parse identity number
            identityNumber = ParserUtil.parseIdentityNumber(
                    argMultimap.getValue(PREFIX_IDENTITY_NUMBER).get());
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_ID);
        }

        // Parse date
        AppointmentDate appointmentDate = ParserUtil.parseAppointmentDate(argMultimap.getValue(PREFIX_DATE).get());

        // Parse log
        LogEntry logEntry;
        try {
            String entry = argMultimap.getValue(PREFIX_LOG).get();
            logEntry = new LogEntry(entry);
        } catch (IllegalArgumentException e) {
            throw new ParseException(LogEntry.MESSAGE_CONSTRAINTS);
        }

        // Create log object
        Log log = new Log(appointmentDate, logEntry);

        // Create and return AddLogCommand with parsed values
        return new AddLogCommand(identityNumber, log);
    }
}
