package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ShowOrderHistoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

/**
 * Parser for ShowOrderHistoryCommand
 */

public class ShowOrderHistoryCommandParser implements Parser<ShowOrderHistoryCommand> {

    /**
     * Parse args for ShowOrderHistoryCommand
     * @param args to parse
     * @return a new ShowOrderHistoryCommand
     * @throws ParseException if the name of the customer is missing
     */
    public ShowOrderHistoryCommand parse(String args) throws ParseException {
        args = args.trim();
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        if (argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ShowOrderHistoryCommand.MESSAGE_USAGE));
        }

        Name name = new Name(argMultimap.getPreamble().trim());
        return new ShowOrderHistoryCommand(name);
    }
}
