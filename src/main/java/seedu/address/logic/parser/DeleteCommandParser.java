package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {
    private static final String WHITESPACE_SPLIT = "\\s+";
    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        args = args.trim();
        ArrayList<String> stringIndices = new ArrayList<>(new HashSet<String>((Arrays
                .asList(args.split(WHITESPACE_SPLIT))))); //Hashset to remove duplicates
        ArrayList<Index> indices = new ArrayList<>();
        ArrayList<String> invalidArgs = new ArrayList<>();
        for (String s: stringIndices) {
            try {
                indices.add(ParserUtil.parseIndex(s));
            } catch (ParseException pe) {
                invalidArgs.add(s);
            }
        }
        if (indices.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        return new DeleteCommand(indices, !invalidArgs.isEmpty());
    }

}
