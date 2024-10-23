package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.function.Predicate;

import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;


/**
 * Filters the address book by a given prefix and displays the filtered list to the user
 * Users can only filter by one field at a time
 */
public class FilterCommand extends Command {
    public static final String COMMAND_WORD = "filter";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Filters the address book with the given criteria\n"
            + "Parameters: [prefix]/[filter critera]\n" + "Example: " + COMMAND_WORD + " t/friends";

    private final Set<Predicate<Person>> predicateSet;
    private Predicate<Person> predicate = new Predicate<Person>() {
        @Override
        public boolean test(Person person) {
            return true;
        }
    };

    public FilterCommand(Set<Predicate<Person>> predicateSet) {
        this.predicateSet = predicateSet;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        for (Predicate<Person> predicateToAdd: predicateSet) {
            predicate = predicate.and(predicateToAdd);
        }
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }
}
