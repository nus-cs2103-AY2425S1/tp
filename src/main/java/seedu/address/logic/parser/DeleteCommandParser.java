package seedu.address.logic.parser;

import java.util.Arrays;
import java.util.ArrayList;
import java.util.HashSet;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.core.index.IndexComparator;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        try {
            ArrayList<String> stringIndices = new ArrayList<>(new HashSet<String>((Arrays.asList(args.split("\\s*")))));
            ArrayList<Index> indices = new ArrayList<>();
            for (String s: stringIndices) {
                indices.add(ParserUtil.parseIndex(s));
            }
            indices.sort(new IndexComparator()); //sort in descending order for smooth deletion
            return new DeleteCommand(indices);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
