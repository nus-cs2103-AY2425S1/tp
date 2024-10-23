package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.function.Predicate;

import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all persons";

    private Predicate<Person> previousPredicate;

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        previousPredicate = model.getCurrentPredicate();
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.addCommandToLog(this);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void undo(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(previousPredicate);
    }
}
