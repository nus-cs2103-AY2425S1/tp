package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteEmergencyContactCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new {@code DeleteEmergencyContactCommand} object
 */
public class DeleteEmergencyContactCommandParser implements Parser<DeleteEmergencyContactCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeleteEmergencyContactCommand}
     * and returns a {@code DeleteEmergencyContactCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteEmergencyContactCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);
        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    DeleteEmergencyContactCommand.MESSAGE_USAGE), ive);
        }
        return new DeleteEmergencyContactCommand(index);
    }
}
