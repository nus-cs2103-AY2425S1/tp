package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;


/**
 * Parses input arguments and creates a new DeleteCommand object.
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {
    private static final Logger logger = LogsCenter.getLogger(DeleteCommandParser.class);
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format.
     */
    public DeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            logger.warning("Received empty NRIC for DeleteCommand");
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        try {
            Nric patientNric = ParserUtil.parseNric(args);
            logger.info("Successfully parsed the NRIC for DeleteCommand: " + patientNric);
            return new DeleteCommand(new NricMatchesPredicate(patientNric));
        } catch (ParseException pe) {
            logger.warning("Unable to parse the NRIC for DeleteCommand: " + args);
            throw new ParseException(pe.getMessage());
        }
    }

}
