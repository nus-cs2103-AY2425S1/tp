package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.function.Predicate;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all persons in address book that match the specified criteria.
 */
public class FindCommand extends Command {

    public static final String COMMAND_WORD = "find-client";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons that match the specified criteria "
            + "and displays them as a list with index numbers.\n"
            + "Parameters: [n/NAME] [p/PHONE] [e/EMAIL] [a/ADDRESS] [pt/POLICY_TYPE]\n"
            + "Example: " + COMMAND_WORD + " n/John Doe p/99998888";

    private final Predicate<Person> predicate;

    public FindCommand(Predicate<Person> predicate) {
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCommand // instanceof handles nulls
                && predicate.equals(((FindCommand) other).predicate)); // state check
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("predicate", predicate)
                .toString();
    }

    public Predicate<Person> getPredicate() {
        return predicate;
    }
}
