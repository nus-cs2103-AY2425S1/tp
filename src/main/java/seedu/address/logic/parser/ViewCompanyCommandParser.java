package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.Messages.MESSAGE_MISSING_INDEX;
import static seedu.address.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ViewCompanyCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new ViewCompanyCommand object.
 */
public class ViewCompanyCommandParser implements Parser<ViewCompanyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of a ViewCompanyCommand
     * and returns a ViewCompanyCommand object for execution.
     *
     * @throws ParseException if the user input does not conform to the expected format.
     */
    public ViewCompanyCommand parse(String args) throws ParseException {

        String[] splitArgs = args.trim().split("\\s+");

        if (args.isEmpty()) {
            throw new ParseException(ViewCompanyCommand.MESSAGE_USAGE);
        } else if (splitArgs.length < 2) {
            throw new ParseException(MESSAGE_MISSING_INDEX);
        }

        // TODO: Implement an entity for view command
        if (!splitArgs[0].equals("company")) {
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }

        String indexString = splitArgs[1];

        Index index;
        try {
            index = ParserUtil.parseIndex(indexString);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            ViewCompanyCommand.MESSAGE_USAGE), pe);
        }

        return new ViewCompanyCommand(index);

    }
}
