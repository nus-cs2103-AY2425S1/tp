package seedu.eventtory.logic.parser;

import static seedu.eventtory.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.eventtory.logic.parser.CliSyntax.PREFIX_VENDOR;

import java.util.Arrays;

import seedu.eventtory.logic.commands.FindCommand;
import seedu.eventtory.logic.commands.FindEventCommand;
import seedu.eventtory.logic.commands.FindVendorCommand;
import seedu.eventtory.logic.parser.exceptions.ParseException;
import seedu.eventtory.model.event.EventContainsKeywordsPredicate;
import seedu.eventtory.model.vendor.VendorContainsKeywordsPredicate;

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
            String[] keywords = processKeywords(argsWithoutPrefix);
            return new FindEventCommand(new EventContainsKeywordsPredicate(Arrays.asList(keywords)));
        } else {
            String argsWithoutPrefix = argMultimap.getValue(PREFIX_VENDOR).get();
            String[] keywords = processKeywords(argsWithoutPrefix);
            return new FindVendorCommand(new VendorContainsKeywordsPredicate(Arrays.asList(keywords)));
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

        String[] keywords = trimmedArgs.split("\\s+");
        return keywords;
    }
}
