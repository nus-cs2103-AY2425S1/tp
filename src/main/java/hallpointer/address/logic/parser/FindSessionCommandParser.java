package hallpointer.address.logic.parser;

import static hallpointer.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import hallpointer.address.logic.commands.FindSessionCommand;
import hallpointer.address.logic.parser.exceptions.ParseException;
import hallpointer.address.model.member.SessionContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindSessionCommand object.
 */
public class FindSessionCommandParser implements Parser<FindSessionCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindSessionCommand
     * and returns a FindSessionCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindSessionCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindSessionCommand.MESSAGE_USAGE));
        }

        List<String> keywordList = Arrays.stream(trimmedArgs.split("\\s+"))
                .collect(Collectors.toList());

        return new FindSessionCommand(new SessionContainsKeywordsPredicate(keywordList));
    }
}
