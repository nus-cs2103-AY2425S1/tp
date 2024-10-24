package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Lists all users.\n"
            + "There should be no parameters!\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Listed all persons\n"
            + "Total number of students: ";
    public static final String MESSAGE_NO_RESULT = "There is.. no one?";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        int numberOfStudents = model.getFilteredPersonList().size();
        if (model.getFilteredPersonList().isEmpty()) {
            return new CommandResult(MESSAGE_NO_RESULT);
        }
        return new CommandResult(MESSAGE_SUCCESS + numberOfStudents);
    }
}
