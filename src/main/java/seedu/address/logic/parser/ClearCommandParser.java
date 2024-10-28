package seedu.address.logic.parser;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.eventcommands.ClearEventCommand;
import seedu.address.logic.commands.personcommands.ClearPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser implements Parser<ClearCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the Command
     * and returns a Command object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearCommand parse(ModelType modelType, String args) throws ParseException {
        if (modelType == ModelType.PERSON) {
            return new ClearPersonCommand();
        } else if (modelType == ModelType.EVENT) {
            return new ClearEventCommand();
        } else {
            return new ClearCommand();
        }
    }
}
