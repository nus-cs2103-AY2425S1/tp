package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
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
            List<Index> indices = Arrays.stream(args.trim().split("\\s+"))
                    .map(arg -> {
                        try {
                            return ParserUtil.parseIndex(arg);
                        } catch (ParseException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    // remove duplicates
                    .distinct()
                    .collect(Collectors.toList());
            return new DeleteCommand(indices);
        } catch (RuntimeException e) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), e);
        }
    }
}
