package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Arrays;
import java.util.function.Predicate;

import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.TagContainsKeywordsPredicate;

/**
 * Parses input arguments and creates a new FindCommand object
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the FindCommand
     * and returns a SearchCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public SearchCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_TAG);

        if (!argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_TAG);

        String nameArgs = argMultimap.getValue(PREFIX_NAME).orElse("");
        String tagArgs = argMultimap.getValue(PREFIX_TAG).orElse("");

        // Check for empty inputs after the prefixes
        checkForEmptyInput(argMultimap, PREFIX_NAME, nameArgs);
        checkForEmptyInput(argMultimap, PREFIX_TAG, tagArgs);

        Predicate<Person> combinedPredicate = null;

        if (!nameArgs.isEmpty()) {
            NameContainsKeywordsPredicate namePredicate =
                    new NameContainsKeywordsPredicate(Arrays.asList(nameArgs.split("\\s+")));
            combinedPredicate = combinePredicate(combinedPredicate, namePredicate);
        }

        if (!tagArgs.isEmpty()) {
            TagContainsKeywordsPredicate tagPredicate =
                    new TagContainsKeywordsPredicate(Arrays.asList(tagArgs.split("\\s+")));
            combinedPredicate = combinePredicate(combinedPredicate, tagPredicate);
        }

        if (combinedPredicate == null) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        return new SearchCommand(combinedPredicate);
    }


    /**
     * Checks if the value for a given prefix is empty in the {@code ArgumentMultimap}.
     * If the prefix is present but its associated value is empty, a {@code ParseException} is thrown.
     *
     * @param argMultimap The {@code ArgumentMultimap} containing the user's input and associated prefixes.
     * @param prefix The {@code Prefix} to check for empty input.
     * @param value The value associated with the {@code prefix} to check.
     * @throws ParseException If the prefix is present but its value is empty.
     */
    private void checkForEmptyInput(ArgumentMultimap argMultimap, Prefix prefix, String value)
            throws ParseException {
        if (argMultimap.getValue(prefix).isPresent() && value.trim().isEmpty()) {
            throw new ParseException("The prefix cannot be empty. Please input a keyword for the prefix.");
        }
    }

    /**
     * Combines a new predicate with the existing combined predicate using a logical AND.
     * If the combined predicate is null, it initializes it with the new predicate.
     *
     * @param combinedPredicate The existing combined predicate.
     * @param newPredicate The new predicate to add.
     * @return The updated combined predicate.
     */
    private Predicate<Person> combinePredicate(Predicate<Person> combinedPredicate, Predicate<Person> newPredicate) {
        return combinedPredicate == null ? newPredicate : combinedPredicate.and(newPredicate);
    }

}
