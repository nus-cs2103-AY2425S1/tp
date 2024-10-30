package seedu.ddd.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.ddd.logic.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.ddd.logic.parser.CliFlags.FLAG_CLIENT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_EVENT;
import static seedu.ddd.logic.parser.CliFlags.FLAG_VENDOR;
import static seedu.ddd.logic.parser.ParserUtil.parseFlags;

import seedu.ddd.logic.commands.ListCommand;
import seedu.ddd.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and decides which parser to use.
 */
public class ListCommandParser implements Parser<ListCommand> {
    /**
     * Parses the given {@code String} of arguments in the context of the ListCommand
     * and returns a ListCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ListCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap flagMultimap = ArgumentTokenizer.tokenize(args, FLAG_CLIENT, FLAG_VENDOR, FLAG_EVENT);
        CommandFlag commandFlag = parseFlags(flagMultimap);
        if (commandFlag == null) {
            return new ListContactCommandParser().parse(args);
        }
        switch(commandFlag) {

        case CLIENT:
            return new ListClientCommandParser().parse(args);

        case VENDOR:
            return new ListVendorCommandParser().parse(args);

        case EVENT:
            return new ListEventCommandParser().parse(args);
        // Will never reach this case as my commandFlag can only be of those three above.
        default:
            assert false : "Unexpected commandFlag: " + commandFlag;
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
