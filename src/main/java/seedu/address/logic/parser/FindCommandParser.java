package seedu.address.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import seedu.address.logic.commands.SuperFindCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.CombinedContainsKeywordsPredicate;
import seedu.address.model.person.ContainsKeywordsPredicate;
import seedu.address.model.person.EmailContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PhoneContainsKeywordsPredicate;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Parses input arguments for the find command and constructs a new {@code SuperFindCommand} object.
 * <p>
 * This class processes user input to extract keywords associated with different attributes of a person,
 * such as names, phone numbers, emails, and tags. It tokenizes the input arguments based on predefined
 * prefixes and builds a list of predicates used to filter persons in the address book.
 * </p>
 *
 * <p>
 * The parser creates a {@code CombinedContainsKeywordsPredicate} that combines all individual predicates, allowing
 * for complex search queries. If no valid predicates are constructed from the input, a {@code ParseException}
 * is thrown to indicate an invalid command format.
 * </p>
 *
 * <p>
 * This class follows the parser pattern used in the logic layer of the application and is responsible for ensuring
 * that input is validated and correctly interpreted before creating command objects.
 * </p>
 */
public class FindCommandParser implements Parser<SuperFindCommand> {
    /**
     * Parses the given arguments for find command and returns a {@code SuperFindCommand}.
     *
     * @param args The input arguments for the command.
     * @throws ParseException if the arguments format is invalid.
     */
    @Override
    public SuperFindCommand parse(String args) throws ParseException {
        requireNonNull(args, "Arguments cannot be null.");

        ArgumentMultimap argMultimap = tokenizeArguments(args);
        List<Predicate<Person>> predicates = buildPredicates(argMultimap);

        if (predicates.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuperFindCommand.MESSAGE_USAGE));
        }

        ContainsKeywordsPredicate combinedPredicate = new CombinedContainsKeywordsPredicate(predicates);
        return new SuperFindCommand(combinedPredicate);
    }

    /**
     * Tokenizes the input arguments into an {@code ArgumentMultimap}.
     *
     * @param args The input arguments.
     * @return The tokenized arguments.
     */
    private ArgumentMultimap tokenizeArguments(String args) {
        return ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG);
    }

    /**
     * Builds a list of predicates based on the tokenized input arguments.
     *
     * @param argMultimap The tokenized input arguments.
     * @return A list of predicates for filtering persons.
     */
    private List<Predicate<Person>> buildPredicates(ArgumentMultimap argMultimap) {
        List<Predicate<Person>> predicates = new ArrayList<>();
        buildNamePredicates(argMultimap, predicates);
        buildPhonePredicates(argMultimap, predicates);
        buildEmailPredicates(argMultimap, predicates);
        buildTagPredicates(argMultimap, predicates);
        return predicates;
    }

    private void buildNamePredicates(ArgumentMultimap argMultimap, List<Predicate<Person>> predicates) {
        argMultimap.getValue(PREFIX_NAME)
                .ifPresent(values -> predicates.add(
                        new NameContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_NAME))));
    }

    private void buildPhonePredicates(ArgumentMultimap argMultimap, List<Predicate<Person>> predicates) {
        argMultimap.getValue(PREFIX_PHONE)
                .ifPresent(values -> predicates.add(
                        new PhoneContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_PHONE))));
    }

    private void buildEmailPredicates(ArgumentMultimap argMultimap, List<Predicate<Person>> predicates) {
        argMultimap.getValue(PREFIX_EMAIL)
                .ifPresent(values -> predicates.add(
                        new EmailContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_EMAIL))));
    }

    private void buildTagPredicates(ArgumentMultimap argMultimap, List<Predicate<Person>> predicates) {
        argMultimap.getValue(PREFIX_TAG)
                .ifPresent(values -> predicates.add(
                        new TagContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_TAG))));
    }
}
