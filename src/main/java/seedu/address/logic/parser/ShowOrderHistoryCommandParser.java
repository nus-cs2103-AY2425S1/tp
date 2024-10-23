package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.address.logic.commands.ShowOrderHistoryCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Name;

public class ShowOrderHistoryCommandParser implements Parser<ShowOrderHistoryCommand> {

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
