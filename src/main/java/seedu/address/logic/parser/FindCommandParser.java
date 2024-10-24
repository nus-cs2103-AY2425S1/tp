package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_VENDOR;

import java.util.Arrays;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindEventCommand;
import seedu.address.logic.commands.FindVendorCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.vendor.NameContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<FindCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public FindCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_VENDOR, PREFIX_EVENT);

        if (!(argMultimap.exactlyOnePrefixPresent(PREFIX_VENDOR, PREFIX_EVENT))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_VENDOR, PREFIX_EVENT);

        final boolean isEventFind = argMultimap.getValue(PREFIX_EVENT).isPresent();


        if (isEventFind) {
            String argsWithoutPrefix = argMultimap.getValue(PREFIX_EVENT).get();
            String[] nameKeywords = processKeywords(argsWithoutPrefix);
            return new FindEventCommand(new EventNameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        } else {
            String argsWithoutPrefix = argMultimap.getValue(PREFIX_VENDOR).get();
            String[] nameKeywords = processKeywords(argsWithoutPrefix);
            return new FindVendorCommand(new NameContainsKeywordsPredicate(Arrays.asList(nameKeywords)));
        }
    }

    /**
     * Processes the keywords from the arguments
     * @param args the arguments
     * @return the keywords
     */
    private String[] processKeywords(String args) throws ParseException {
        String trimmedArgs = args.trim();

        if (trimmedArgs.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        String[] nameKeywords = trimmedArgs.split("\\s+");
        return nameKeywords;
    }
}
