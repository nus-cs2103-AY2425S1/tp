package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewwCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.wedding.NameMatchesWeddingPredicate;

/**
 * Parses input arguments and creates a new ViewwCommand object
 */
public class ViewwCommandParser implements Parser<ViewwCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ViewwCommand
     * and returns a ViewwCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewwCommand parse(String args) throws ParseException {
        try {
            String trimmedArgs = args.trim();

            if (trimmedArgs.isEmpty()) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewwCommand.MESSAGE_USAGE));
            }

            if (isNumeric(trimmedArgs)) {
                Index index = ParserUtil.parseIndex(trimmedArgs);
                return new ViewwCommand(index, null);
            } else {
                String[] nameKeywords = trimmedArgs.split("\\s+");
                NameMatchesWeddingPredicate predicate = new NameMatchesWeddingPredicate(
                        Arrays.asList(nameKeywords));

                return new ViewwCommand(null, predicate);
            }
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewwCommand.MESSAGE_USAGE), pe);
        }
    }

    private boolean isNumeric(String str) {
        return str != null && str.matches("-?\\d+");
    }
}
