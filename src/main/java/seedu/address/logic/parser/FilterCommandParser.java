package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RSVP_STATUS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.logic.commands.FilterCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.RsvpStatus;
import seedu.address.model.tag.Tag;

/**
 * Parses the user's input and return a FilterCommand object for execution
 * @throws ParseException if the user input does not conform the expected format
 */
public class FilterCommandParser implements Parser<FilterCommand> {

    public static final String MESSAGE_SUPPORT = "Filter does not support Name, Email or Phone\n"
            + "Filter only supports Tag and RSVP Status";
    private final Set<Predicate<Person>> predicateSet = new HashSet<>();

    /**
     * Parses the user's input based on the prefix provided and returns the matching FilterCommand object
     * @throws ParseException if the user enters an invalid prefix
     */
    public FilterCommand parse(String args) throws ParseException {
        String lowercaseArgs = args.toLowerCase();
        String trimmedArgs = args.trim();

        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(lowercaseArgs, PREFIX_TAG, PREFIX_RSVP_STATUS,
                PREFIX_EMAIL, PREFIX_NAME, PREFIX_PHONE);
        if (trimmedArgs.isEmpty() || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
        }
        checkInputs(argMultimap);

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_RSVP_STATUS);
        Set<Tag> tagSet = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));
        Set<RsvpStatus> statusSet = ParserUtil.parseStatuses(argMultimap.getAllValues(PREFIX_RSVP_STATUS));

        return new FilterCommand(tagSet, statusSet);
    }

    public Set<Predicate<Person>> getPredicateSet() {
        return this.predicateSet;
    }

    /**
     * Checks if the user input includes Name, Phone or Email
     */
    public void checkInputs(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.contains(PREFIX_EMAIL) || argMultimap.contains(PREFIX_NAME)
                || argMultimap.contains(PREFIX_PHONE)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    MESSAGE_SUPPORT));
        }
    }
}
