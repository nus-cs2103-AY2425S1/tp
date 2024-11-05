package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.InFilteredListPredicate;
import seedu.address.model.person.ModuleRoleContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;


/**
 * Finds and lists all persons in address book whose name or module contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String CHAINED = "chained";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds persons by their names, module-role pairs"
            + "(or both), matching any (combination) of the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "To search from the previously displayed results, use 'find " + CHAINED + "'. \n"
            + "Parameters: "
            + "[" + CHAINED + "] "
            + "(" + PREFIX_NAME + "KEYWORD | " + PREFIX_MODULE + "KEYWORD)+\n"
            + "Example: " + COMMAND_WORD + " "
            + CHAINED + " "
            + PREFIX_NAME + "alice "
            + PREFIX_NAME + "bob "
            + PREFIX_MODULE + "CS1101S "
            + PREFIX_MODULE + "CS2030S";

    private final List<Predicate<Person>> predicates;

    private final boolean isChained;

    /**
     * Creates a FindCommand to find persons that match the specified predicates.
     *
     * @param predicates A list of predicates that specify the conditions to match.
     */
    public FindCommand(List<Predicate<Person>> predicates) {
        this(predicates, false); // constructor delegation
    }

    /**
     * Creates a FindCommand to find persons that match the specified predicates.
     *
     * @param predicates A list of predicates that specify the conditions to match.
     * @param isChained If true, this command will further filter the results of the current filtered list.
     *                  If false, this command will search on the full list of persons.
     */
    public FindCommand(List<Predicate<Person>> predicates, boolean isChained) {
        // For now we only support 1 or 2 predicates
        assert predicates.size() == 1 || predicates.size() == 2;
        this.predicates = predicates;
        this.isChained = isChained;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        Predicate<Person> combinedPredicate = predicates.stream()
                .reduce(Predicate::and)
                .orElse(t -> false);

        // If we are chaining, on top of the searching conditions, we need to perform an additional check
        // that the person is in the previously displayed results.
        if (isChained) {
            // We HAVE to copy the list here, or it will have been emptied by the time Predicate.test() is called
            Predicate<Person> inFilteredListPredicate = new InFilteredListPredicate(
                    new ArrayList<>(model.getFilteredPersonList()));
            combinedPredicate = combinedPredicate.and(inFilteredListPredicate);
        }

        if (isChained) {
            combinedPredicate = predicates.stream()
                    .reduce(Predicate::and)
                    .orElse(t -> false);
            Predicate<Person> inFilteredListPredicate = new InFilteredListPredicate(
                    new ArrayList<>(model.getFilteredPersonList()));
            combinedPredicate = combinedPredicate.and(inFilteredListPredicate);
        } else {
            combinedPredicate = predicates.stream()
                    .reduce(Predicate::and)
                    .orElse(t -> false);
        }

        model.updateFilteredPersonList(combinedPredicate);
        String conditions = getAllKeywordsFromPredicates(predicates);
        if (isChained) {
            conditions = Messages.MESSAGE_CHAINED_FIND_PREFIX + conditions;
        }

        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW,
                        model.getFilteredPersonList().size(),
                        conditions
                )
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindCommand)) {
            return false;
        }

        FindCommand otherFindCommand = (FindCommand) other;
        return predicates.equals(otherFindCommand.predicates);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicates)
                .toString();
    }

    /**
     * Retrieves all keywords from the provided list of predicates and formats them as a comma-separated string.
     * Each keyword is surrounded by "".
     *
     * @param predicates A list of predicates that may include name or module-based predicates.
     * @return A formatted string containing all keywords from the predicates.
     */
    private String getAllKeywordsFromPredicates(List<Predicate<Person>> predicates) {
        return predicates.stream()
                .map(this::extractKeywords)
                .map(kws -> kws.stream().collect(Collectors.joining(" OR ")))
                .map(kws -> "(" + kws + ")")
                .collect(Collectors.joining(" AND "));
    }

    /**
     * Extracts keywords from a given predicate, depending on the type of predicate.
     * If the predicate is of an unrecognized type, an empty list is returned.
     *
     * @param predicate The predicate from which to extract keywords.
     * @return A list of keywords from the predicate, or an empty list if the predicate type is not recognized.
     */
    private List<String> extractKeywords(Predicate<Person> predicate) {
        if (predicate instanceof NameContainsKeywordsPredicate) {
            return ((NameContainsKeywordsPredicate) predicate).getKeywords();
        } else if (predicate instanceof ModuleRoleContainsKeywordsPredicate) {
            return ((ModuleRoleContainsKeywordsPredicate) predicate).getModuleRolePairs();
        }
        return Collections.emptyList();
    }
}
