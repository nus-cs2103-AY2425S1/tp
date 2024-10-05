package seedu.researchroster.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.researchroster.model.ResearchRoster;
import seedu.researchroster.model.Model;

/**
 * Clears the researchroster book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setAddressBook(new ResearchRoster());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
