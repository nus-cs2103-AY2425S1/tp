package seedu.address.logic.parser;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.eventcommands.ClearEventCommand;
import seedu.address.logic.commands.personcommands.ClearPersonCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ClearCommand object
 */
public class ClearCommandParser implements Parser<ClearCommand> {

    private static ModelType modelType;

    /**
     * Parses the given {@code String} of arguments in the context of the Command
     * and returns a Command object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ClearCommand parse(ModelType modelType, String args) throws ParseException {
        ClearCommandParser.modelType = modelType;
        if (modelType == ModelType.PERSON) {
            return new ClearPersonCommand();
        } else if (modelType == ModelType.EVENT) {
            return new ClearEventCommand();
        } else {
            return new ClearCommand();
        }
    }

    /**
     * Parses and returns a ClearCommand with a confirmed status for immediate execution.
     * The returned command will bypass the confirmation prompt, executing the clear action directly.
     *
     * @return A confirmed ClearCommand based on the current model type.
     */
    public ClearCommand parseClear() {
        if (modelType == ModelType.PERSON) {
            ClearPersonCommand clearPersonCommand = new ClearPersonCommand();
            clearPersonCommand.setConfirmed(true);
            return clearPersonCommand;
        } else if (modelType == ModelType.EVENT) {
            ClearEventCommand clearEventCommand = new ClearEventCommand();
            clearEventCommand.setConfirmed(true);
            return clearEventCommand;
        } else {
            ClearCommand clearCommand = new ClearCommand();
            clearCommand.setConfirmed(true);
            return clearCommand;
        }
    }

    /**
     * Parses and returns a ClearCommand with a non-confirmed status, indicating an aborted command.
     * The returned command will not perform any clearing actions, as the confirmation was not received.
     *
     * @return An aborted ClearCommand based on the current model type.
     */
    public ClearCommand parseAbort() {
        if (modelType == ModelType.PERSON) {
            ClearPersonCommand clearPersonCommand = new ClearPersonCommand();
            clearPersonCommand.setConfirmed(false);
            return clearPersonCommand;
        } else if (modelType == ModelType.EVENT) {
            ClearEventCommand clearEventCommand = new ClearEventCommand();
            clearEventCommand.setConfirmed(false);
            return clearEventCommand;
        } else {
            ClearCommand clearCommand = new ClearCommand();
            clearCommand.setConfirmed(false);
            return clearCommand;
        }
    }
}
