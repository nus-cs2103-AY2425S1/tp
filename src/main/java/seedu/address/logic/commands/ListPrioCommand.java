package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.PriorityMatchesPredicate;


/**
 * Lists all patients in the address book whose Priority matches the given Priority level.
 */
public class ListPrioCommand extends Command {

    public static final String COMMAND_WORD = "listPrio";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Lists all patients whose Priority "
            + "matches the given Priority level.\n"
            + "Parameter: !/Priority\n"
            + "Example: " + COMMAND_WORD + " !/HIGH\n";

    private final PriorityMatchesPredicate predicate;

    /**
     * Constructs a ListPrioCommand to filter and list patients by their Priority level.
     *
     * @param predicate The predicate to filter patients by their Priority level.
     */
    public ListPrioCommand(PriorityMatchesPredicate predicate) {
        this.predicate = predicate;
    }

    /**
     * Executes the command to list all patients whose Priority matches the given predicate.
     *
     * @param model The model that contains the list of patients.
     * @return A CommandResult containing the number of patients listed.
     */
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }


    /**
     * Compares this ListPrioCommand with another object.
     *
     * @param other The other object to compare to.
     * @return true if the other object is the same instance or has the same predicate, false otherwise.
     */
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ListPrioCommand)) {
            return false;
        }
        ListPrioCommand otherListPrioCommand = (ListPrioCommand) other;
        return predicate.equals(otherListPrioCommand.predicate);
    }

    /**
     * Returns the string representation of this ListPrioCommand.
     *
     * @return A string representation of the command, including its predicate.
     */
    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }
}
