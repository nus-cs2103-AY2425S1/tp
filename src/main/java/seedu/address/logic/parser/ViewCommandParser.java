package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TELEGRAM;

import java.util.stream.Stream;

import seedu.address.logic.commands.ViewCommand;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class ViewCommandParser implements Parser<ViewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ViewCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultiMap = ArgumentTokenizer.tokenize(args, PREFIX_TELEGRAM);
        if (!arePrefixesPresent(argMultiMap, PREFIX_TELEGRAM)) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ViewCommand.MESSAGE_USAGE));
        }

        String tele = argMultiMap.getValue(PREFIX_TELEGRAM).get();
        // ERROR! 'tele' here gives an empty string ""...
        return new ViewCommand(tele);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

}
