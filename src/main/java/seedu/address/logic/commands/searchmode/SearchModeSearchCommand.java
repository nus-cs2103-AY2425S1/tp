package seedu.address.logic.commands.searchmode;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.model.event.EventManager;
import seedu.address.model.person.Person;


/**
 * Searches for all persons in the address book whose names contain all the specified keywords.
 */
public class SearchModeSearchCommand extends Command {
    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Searches for all contacts whose fields contain the specified keywords (case-insensitive) "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: [FLAG] [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " n/Amy Bob Charlie";

    public static final String MESSAGE_SUCCESS = "Added %1$s Contacts who fit search parameter.";
    public static final String MESSAGE_NO_PERSONS_FOUND = "No Contacts found with the search parameter.";

    //maintains a set of predicates, reducing them to get the final predicate in execute
    private final Set<Predicate<Person>> predicates = new HashSet<>();

    public SearchModeSearchCommand(Predicate<Person> predicate) {
        this.predicates.add(predicate);
    }

    public SearchModeSearchCommand(Set<Predicate<Person>> predicates) {
        this.predicates.addAll(predicates);
    }

    @Override
    public CommandResult execute(Model model, EventManager eventManager) {
        // combine the new predicates with the old ones
        Predicate<Person> currPredicate = model.getLastPredicate();
        int originalSize = model.getFilteredPersonList().size();
        Predicate<Person> predicate = predicates.stream().reduce(Predicate::and).orElse(x -> true);
        Predicate<Person> newPredicate = currPredicate.or(predicate);



        model.updateFilteredListWithExclusions(newPredicate);
        int updatedSize = model.getFilteredPersonList().size();
        // or predicate, so if the size is the same, no new persons were added
        if (updatedSize == originalSize) {
            return new CommandResult(MESSAGE_NO_PERSONS_FOUND);
        }
        return new CommandResult(String.format(MESSAGE_SUCCESS, updatedSize - originalSize));

    }

    public Set<Predicate<Person>> getPredicates() {
        return predicates;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchModeSearchCommand // instanceof handles nulls
                && predicates.equals(((SearchModeSearchCommand) other).predicates));
    }
}
