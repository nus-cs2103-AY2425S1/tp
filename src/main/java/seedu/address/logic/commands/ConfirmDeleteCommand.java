package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.ui.CommandDetailChange;
import seedu.address.ui.CommandTabChange;

/**
 * Requests confirmation before delete person operation.
 */
public class ConfirmDeleteCommand extends Command {
    public static final String DELETE_CONFIRMATION_MESSAGE = "Are you sure you want to delete this person? "
            + "All events tied to this person will be deleted "
            + "and this person will no longer show in any event's contact list. (Y/N)";

    private Index deleteIndex;

    public ConfirmDeleteCommand(Index deleteIndex) {
        this.deleteIndex = deleteIndex;
    }

    /**
     * Checks if the index is valid.
     *
     * @param model {@code Model} which the command should operate on.
     * @return true if the index is valid, false otherwise.
     */
    public boolean checkValidIndex(Model model) {
        List<Person> lastShownList = model.getFilteredPersonList();
        return deleteIndex.getZeroBased() < lastShownList.size();
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        if (!checkValidIndex(model)) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return new CommandResult(DELETE_CONFIRMATION_MESSAGE, false, false,
                CommandTabChange.NONE, CommandDetailChange.NONE);
    }
}
