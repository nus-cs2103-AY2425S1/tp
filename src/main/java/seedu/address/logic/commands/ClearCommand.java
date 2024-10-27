package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.model.EduContacts;
import seedu.address.model.Model;

/**
 * Clears EduContacts.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "EduContacts has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setEduContacts(new EduContacts());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
