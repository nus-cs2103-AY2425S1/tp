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
 * Parses input arguments and creates a new FindCommand object
 */
public class FindCommandParser implements Parser<SuperFindCommand> {
    @Override
    public SuperFindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = tokenizeArguments(args);

        List<Predicate<Person>> predicates = buildPredicates(argMultimap);

        if (predicates.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuperFindCommand.MESSAGE_USAGE));
        }

        ContainsKeywordsPredicate combinedPredicate = new CombinedContainsKeywordsPredicate(predicates);
        return new SuperFindCommand(combinedPredicate);
    }

    private ArgumentMultimap tokenizeArguments(String args) {
        return ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG);
    }

    private List<Predicate<Person>> buildPredicates(ArgumentMultimap argMultimap) {
        List<Predicate<Person>> predicates = new ArrayList<>();
        buildNamePredicates(argMultimap, predicates);
        buildPhonePredicates(argMultimap, predicates);
        buildEmailPredicates(argMultimap, predicates);
        buildTagPredicates(argMultimap, predicates);
        return predicates;
    }

    private void buildNamePredicates(ArgumentMultimap argMultimap, List<Predicate<Person>> predicates) {
        argMultimap.getValue(PREFIX_NAME).ifPresent(values ->
                predicates.add(new NameContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_NAME))));
    }

    private void buildPhonePredicates(ArgumentMultimap argMultimap, List<Predicate<Person>> predicates) {
        argMultimap.getValue(PREFIX_PHONE).ifPresent(values ->
                predicates.add(new PhoneContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_PHONE))));
    }

    private void buildEmailPredicates(ArgumentMultimap argMultimap, List<Predicate<Person>> predicates) {
        argMultimap.getValue(PREFIX_EMAIL).ifPresent(values ->
                predicates.add(new EmailContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_EMAIL))));
    }

    private void buildTagPredicates(ArgumentMultimap argMultimap, List<Predicate<Person>> predicates) {
        argMultimap.getValue(PREFIX_TAG).ifPresent(values ->
                predicates.add(new TagContainsKeywordsPredicate(argMultimap.getAllValues(PREFIX_TAG))));
    }
}
