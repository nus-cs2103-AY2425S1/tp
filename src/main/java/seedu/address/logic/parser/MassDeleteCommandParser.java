package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.MassDeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MassDeleteCommand object.
 */
public class MassDeleteCommandParser implements Parser<MassDeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MassDeleteCommand
     * and returns a MassDeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public MassDeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassDeleteCommand.MESSAGE_USAGE));
        }

        String[] indexStrings = trimmedArgs.split("\\s+");
        List<Index> indices = new ArrayList<>();
        List<String> invalidInputs = new ArrayList<>();

        for (String indexString : indexStrings) {
            try {
                int index = Integer.parseInt(indexString);
                if (index > 0) {
                    indices.add(Index.fromOneBased(index));
                } else {
                    invalidInputs.add(indexString);
                }
            } catch (NumberFormatException e) {
                invalidInputs.add(indexString);
            }
        }

        if (indices.isEmpty() && invalidInputs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassDeleteCommand.MESSAGE_USAGE));
        }

        return new MassDeleteCommand(indices, invalidInputs);
    }
}
