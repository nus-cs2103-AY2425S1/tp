package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.ParserUtil.isNumeric;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeletewCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.NameMatchesWeddingPredicate;

/**
 * Parses input arguments and creates a new {@code DeletewCommand} object
 */
public class DeletewCommandParser implements Parser<DeletewCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the {@code DeletewCommand}
     * and returns a {@code DeletewCommand} object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeletewCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();

            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletewCommand.MESSAGE_USAGE));
            }

            if (isNumeric(trimmedArgs)) {
                Index index = ParserUtil.parseIndex(trimmedArgs);
                return new DeletewCommand(index, null);
            } else {
                String[] nameKeywords = trimmedArgs.split("\\s+");
                NameMatchesWeddingPredicate predicate = new NameMatchesWeddingPredicate(
                        Arrays.asList(nameKeywords));

                return new DeletewCommand(null, predicate);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletewCommand.MESSAGE_USAGE), pe);
        }
    }
}
