package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import java.util.Arrays;

import seedu.address.logic.commands.GetCommand;
import seedu.address.logic.parser.exceptions.ParseException;


/**
 * Parses input arguments and creates a new GetCommand object
 */
public class GetCommandParser implements Parser<GetCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the GetCommand
     * and returns a GetCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public GetCommand parse(String args) throws ParseException {

        String[] parameters = args.trim().split("\\s+");
        int size = parameters.length;
        for (int i = 0; i < size; i++) {
            String trimmedParam = parameters[i].trim();
            if (!trimmedParam.matches("^[a-zA-Z]+$")) {
                throw new ParseException(
                        String.format(MESSAGE_INVALID_COMMAND_FORMAT, GetCommand.MESSAGE_USAGE));
            }
        }
        String[] uniqueParams = Arrays.stream(parameters)
                .distinct()
                .toArray(String[]::new);
        return new GetCommand(uniqueParams);

    }
}

