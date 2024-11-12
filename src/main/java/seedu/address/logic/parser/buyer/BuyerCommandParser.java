package seedu.address.logic.parser.buyer;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.buyer.AddCommand;
import seedu.address.logic.commands.buyer.ClearCommand;
import seedu.address.logic.commands.buyer.DeleteCommand;
import seedu.address.logic.commands.buyer.EditCommand;
import seedu.address.logic.commands.buyer.FindCommand;
import seedu.address.logic.commands.buyer.ViewCommand;
import seedu.address.logic.parser.CommandParser;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses commands and arguments for Buyer-type commands.
 */
public class BuyerCommandParser extends CommandParser {

    private static final Logger logger = LogsCenter.getLogger(BuyerCommandParser.class);

    @Override
    public Command parseCommand(String commandWord, String arguments) throws ParseException {

        // Note to developers: Change the log level in config.json to enable lower level (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            return new DeleteCommandParser().parse(arguments);

        case FindCommand.COMMAND_WORD:
            return new FindCommandParser().parse(arguments);

        case ViewCommand.COMMAND_WORD:
            return new ViewCommand();

        case ClearCommand.COMMAND_WORD:
            return new ClearCommand();

        default:
            return super.parseCommand(commandWord, arguments);
        }
    }

}
