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

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Searches for all persons whose names contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " alice bob charlie";

    public static final String MESSAGE_SUCCESS = "Added all Persons who fit search parameter";

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
        Predicate<Person> predicate = predicates.stream().reduce(Predicate::and).orElse(x -> true);
        Predicate<Person> newPredicate = currPredicate.or(predicate);
        model.updateFilteredPersonList(newPredicate);
        return new CommandResult(MESSAGE_SUCCESS);
    }


    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SearchModeSearchCommand // instanceof handles nulls
                && predicates.equals(((SearchModeSearchCommand) other).predicates));
    }
}
