package keycontacts.logic.commands;

import static java.util.Objects.requireNonNull;

import keycontacts.model.Model;
import keycontacts.model.StudentDirectory;

/**
 * Clears the student directory.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Student directory has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setStudentDirectory(new StudentDirectory());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
