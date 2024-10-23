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
    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a FindCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    @Override
    public SuperFindCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer
                .tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_EMAIL, PREFIX_TAG);

        List<Predicate<Person>> predicates = new ArrayList<>();

        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            List<String> nameKeywords = argMultimap.getAllValues(PREFIX_NAME);
            predicates.add(new NameContainsKeywordsPredicate(nameKeywords));
        }

        if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
            List<String> phoneKeywords = argMultimap.getAllValues(PREFIX_PHONE);
            predicates.add(new PhoneContainsKeywordsPredicate(phoneKeywords));
        }

        if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            List<String> emailKeywords = argMultimap.getAllValues(PREFIX_EMAIL);
            predicates.add(new EmailContainsKeywordsPredicate(emailKeywords));
        }

        if (argMultimap.getValue(PREFIX_TAG).isPresent()) {
            List<String> tagKeywords = argMultimap.getAllValues(PREFIX_TAG);
            predicates.add(new TagContainsKeywordsPredicate(tagKeywords));
        }

        if (predicates.isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SuperFindCommand.MESSAGE_USAGE));
        }

        // Combine them into a single predicate
        ContainsKeywordsPredicate combinedPredicate = new CombinedContainsKeywordsPredicate(predicates);
        return new SuperFindCommand(combinedPredicate);
    }
}
