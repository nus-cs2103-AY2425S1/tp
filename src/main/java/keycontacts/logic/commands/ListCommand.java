package keycontacts.logic.commands;

import static java.util.Objects.requireNonNull;
import static keycontacts.model.Model.PREDICATE_SHOW_ALL_STUDENTS;

import keycontacts.model.Model;

/**
 * Lists all students in the student directory to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String MESSAGE_SUCCESS = "Listed all students";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateStudentList(PREDICATE_SHOW_ALL_STUDENTS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
