package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.FindNricCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Nric;
import seedu.address.model.person.NricMatchesPredicate;

/**
 * Parses input arguments and creates a new findNricCommand object
 */
public class FindNricCommandParser implements Parser<FindNricCommand> {
    private static final Logger logger = LogsCenter.getLogger(FindNricCommand.class);
    /**
     * Parses the given {@code String} of arguments in the context of the FindNricCommand
     * and returns a FindNricCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindNricCommand parse(String args) throws ParseException {
        try {
            Nric patientNric = ParserUtil.parseNric(args);
            logger.info("Successfully parsed the NRIC for FindNricCommand: " + patientNric);
            return new FindNricCommand(new NricMatchesPredicate(patientNric));
        } catch (ParseException pe) {
            logger.warning("Unable to parse the NRIC for FindNricCommand: " + args);
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindNricCommand.MESSAGE_USAGE), pe);
        }
    }
}
