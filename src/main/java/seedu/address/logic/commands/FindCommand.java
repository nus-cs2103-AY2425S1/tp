package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;

import seedu.address.commons.util.StringUtil;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book whose name contains any of the argument keywords.
 * Keyword matching is case insensitive.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    private final NameContainsKeywordsPredicate predicate;
    private final Comparator<Person> comparator;
    /**
     * Constructs a {@code FindCommand} with the specified predicate and comparator.
     *
     * @param predicate The predicate used to filter the list of persons.
     * @param comparator The comparator used to sort the filtered list of persons.
     */
    public FindCommand(NameContainsKeywordsPredicate predicate, String searchTerm) {
        this.predicate = predicate;
        this.comparator = Comparator.comparing((Person person) ->
                StringUtil.getLevenshteinDistanceSubstring(person.getName().fullName, searchTerm));
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        int size = model.getFilteredPersonList().size();
        if (size > 0) {
            return new CommandResult(
                    String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, size));
        } else {
            // Essentially reset list to show all persons
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            // Sort by provided comparator (levenshtein distance for now)
            model.sortFilteredPersonList(comparator);
            return new CommandResult(Messages.MESSAGE_NO_PERSONS_LISTED);
        }
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
        return predicate.equals(otherFindCommand.predicate);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
