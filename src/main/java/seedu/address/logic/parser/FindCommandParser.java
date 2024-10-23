package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COMPANY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RSVP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Person;
import seedu.address.model.person.predicates.AddressContainsKeywordsPredicate;
import seedu.address.model.person.predicates.CompanyContainsKeywordsPredicate;
import seedu.address.model.person.predicates.EmailContainsKeywordsPredicate;
import seedu.address.model.person.predicates.NameContainsKeywordsPredicate;
import seedu.address.model.person.predicates.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.predicates.RsvpContainsKeywordsPredicate;
import seedu.address.model.person.predicates.TagContainsKeywordsPredicate;

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
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                        PREFIX_TAG, PREFIX_RSVP, PREFIX_COMPANY);

        if (!isOnlyOneDistinctPrefixPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS,
                PREFIX_TAG, PREFIX_RSVP, PREFIX_COMPANY) || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_ADDRESS, PREFIX_RSVP,
                PREFIX_COMPANY);

        return new FindCommand(getPredicate(argMultimap));
    }

    /**
     * Constructs a {@code Predicate} based on the provided {@code ArgumentMultimap}.
     *
     * @param argMultimap the {@code ArgumentMultimap} containing the arguments to be parsed
     * @return a {@code Predicate<Person>} that filters {@code Person} objects based on the specified arguments
     * @throws ParseException if none of the expected prefixes are present
     */
    private static Predicate<Person> getPredicate(ArgumentMultimap argMultimap) throws ParseException {
        Optional<String> name = argMultimap.getValue(PREFIX_NAME);
        Optional<String> phone = argMultimap.getValue(PREFIX_PHONE);
        Optional<String> email = argMultimap.getValue(PREFIX_EMAIL);
        Optional<String> address = argMultimap.getValue(PREFIX_ADDRESS);
        Optional<String> rsvp = argMultimap.getValue(PREFIX_RSVP);
        Optional<String> company = argMultimap.getValue(PREFIX_COMPANY);
        List<String> tags = argMultimap.getAllValues(PREFIX_TAG);

        if (name.isPresent()) {
            return new NameContainsKeywordsPredicate(parseKeywords(name.get()));
        } else if (phone.isPresent()) {
            return new PhoneContainsKeywordsPredicate(parseKeywords(phone.get()));
        } else if (email.isPresent()) {
            return new EmailContainsKeywordsPredicate(parseKeywords(email.get()));
        } else if (address.isPresent()) {
            return new AddressContainsKeywordsPredicate(parseKeywords(address.get()));
        } else if (rsvp.isPresent()) {
            return new RsvpContainsKeywordsPredicate(parseKeywords(rsvp.get()));
        } else if (company.isPresent()) {
            return new CompanyContainsKeywordsPredicate(parseKeywords(company.get()));
        } else if (!tags.isEmpty()) {
            return new TagContainsKeywordsPredicate(parseTags(tags));
        } else {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
        }
    }

    /**
     * Checks if exactly one distinct prefix is present in the given {@code ArgumentMultimap}.
     *
     * @param argumentMultimap The {@code ArgumentMultimap} containing the argument prefixes and values.
     * @param prefixes Varargs of {@code Prefix} to check for presence in the {@code argumentMultimap}.
     * @return true if exactly one distinct prefix is present, false otherwise.
     */
    private static boolean isOnlyOneDistinctPrefixPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        long numberOfDistinctPrefixes = Stream.of(prefixes)
                .filter(prefix -> argumentMultimap.getValue(prefix).isPresent())
                .distinct()
                .count();
        return numberOfDistinctPrefixes == 1;
    }

    /**
     * Parses a given string into a list of trimmed keywords.
     *
     * @param inputString The input string to be parsed into keywords.
     * @return A {@code List<String>} containing the trimmed and non-empty keywords.
     */
    private static List<String> parseKeywords(String inputString) {
        return Arrays.stream(inputString.split(" "))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * Parses a list of tags, splitting each tag by spaces and returning a flattened list of individual tags.
     *
     * @param tags the list of tags to be parsed, where each tag may contain multiple words separated by spaces
     * @return a list of individual tags, with no empty strings and no leading or trailing whitespace
     */
    private static List<String> parseTags(List<String> tags) {
        return tags.stream()
                .flatMap(tag -> Arrays.stream(tag.split(" ")))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
