package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.AddLogCommand.MESSAGE_INVALID_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUMBER;

import seedu.address.logic.commands.AddLogEntryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.log.AppointmentDate;
import seedu.address.model.person.IdentityNumber;

/**
 * Parses input arguments and creates a new AddLogEntryCommand object
 */
public class AddLogEntryCommandParser implements Parser<AddLogEntryCommand> {
    public static final String MESSAGE_INVALID_DATE = "Invalid date format! Please use 'dd MMM yyyy'.";

    /**
     * Parses the given {@code String} of arguments in the context of the AddLogEntryCommand
     * and returns an AddLogEntryCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddLogEntryCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_IDENTITY_NUMBER, PREFIX_DATE);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_IDENTITY_NUMBER, PREFIX_DATE);

        // Check if all fields' prefix are present
        if (argMultimap.getValue(PREFIX_IDENTITY_NUMBER).isEmpty() || argMultimap.getValue(PREFIX_DATE).isEmpty()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLogEntryCommand.MESSAGE_USAGE));
        }

        IdentityNumber identityNumber;

        // Check if identity number exists
        try {
            // Parse identity number
            identityNumber = ParserUtil.parseIdentityNumber(
                    argMultimap.getValue(PREFIX_IDENTITY_NUMBER).get());
        } catch (ParseException pe) {
            throw new ParseException(MESSAGE_INVALID_ID);
        }

        AppointmentDate appointmentDate = ParserUtil.parseAppointmentDate(argMultimap.getValue(PREFIX_DATE).get());

        // Create and return AddLog without entry
        return new AddLogEntryCommand(identityNumber, appointmentDate);
    }
}
