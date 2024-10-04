package seedu.address.logic.parser;

import seedu.address.logic.commands.PriorityCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new PriorityCommand object
 */
public class PriorityCommandParser implements Parser<PriorityCommand> {

    @Override
    public PriorityCommand parse(String args) throws ParseException {
        try {
            String[] splitArgs = args.trim().split("\\s+");
            int id = Integer.parseInt(splitArgs[1]);
            int level = Integer.parseInt(splitArgs[3]);
            return new PriorityCommand(id, level);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            throw new ParseException("Invalid command format! \n" + PriorityCommand.MESSAGE_USAGE);
        }
    }
}
