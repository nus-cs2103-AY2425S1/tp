package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Comparator;

import seedu.address.model.Model;

/**
 * Sorts all contacts by name in the contact list.
 */
public class SortCommand extends Command {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SUCCESS = "Sorted all persons by name";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Call the model's sortPersonList method with a comparator that sorts by name
        model.sortPersonList(Comparator.comparing(person -> person.getName().toString()));

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
