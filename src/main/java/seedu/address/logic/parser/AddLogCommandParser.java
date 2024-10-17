package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IDENTITY_NUMBER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOG;

import seedu.address.logic.commands.AddLogCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.log.Log;
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
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_IDENTITY_NUMBER, PREFIX_LOG);

        // Manually checking if required prefixes are present
        if (argMultimap.getValue(PREFIX_IDENTITY_NUMBER).isEmpty() || argMultimap.getValue(PREFIX_LOG).isEmpty()) {
            System.out.println("failhere");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLogCommand.MESSAGE_USAGE));
        }

        try {
            // Parse identity number
            String identityNumber = argMultimap.getValue(PREFIX_IDENTITY_NUMBER).get();
            IdentityNumber identityNumber1 = ParserUtil.parseIdentityNumber(identityNumber);

            // Extract and assign log details (date and entry)
            String logEntry = argMultimap.getValue(PREFIX_LOG).get();

            // Split the log entry into date and entry components
            String[] logParts = logEntry.split("\\|");
            if (logParts.length != 2) {
                throw new ParseException(Log.MESSAGE_CONSTRAINTS);
            }
            String date = logParts[0].trim();
            String entry = logParts[1].trim();

            // Create and return AddLogCommand with parsed values
            return new AddLogCommand(identityNumber1, date, entry);

        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddLogCommand.MESSAGE_USAGE), pe);
        }
    }
}


