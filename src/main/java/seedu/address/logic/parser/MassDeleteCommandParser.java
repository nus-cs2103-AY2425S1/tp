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

        try {
            for (String indexString : indexStrings) {
                Index index = ParserUtil.parseIndex(indexString);
                indices.add(index);
            }
            return new MassDeleteCommand(indices);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, MassDeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}

