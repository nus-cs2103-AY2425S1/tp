package seedu.academyassist.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.academyassist.model.AcademyAssist;
import seedu.academyassist.model.Model;

/**
 * Clears the admin system.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "AcademyAssist has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAcademyAssist(new AcademyAssist());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
