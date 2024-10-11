package hallpointer.address.logic.parser;

import static hallpointer.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import hallpointer.address.logic.commands.FindMemberCommand;
import hallpointer.address.logic.parser.exceptions.ParseException;
import hallpointer.address.model.member.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindMemberCommand object
 */
public class FindMemberCommandParser implements Parser<FindMemberCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindMemberCommand
     * and returns a FindMemberCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public FindMemberCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindMemberCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");

        return new FindMemberCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
    }

}
