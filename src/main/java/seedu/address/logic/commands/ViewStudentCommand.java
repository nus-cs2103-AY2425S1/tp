package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.List;

/**
 * Expands the details of a person identified using it's displayed index from the address book.
 */
public class ViewStudentCommand extends Command {

    public static final String COMMAND_WORD = "view_student";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Displays details of the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_PERSON_SUCCESS = "Displayed Person: %1$s";

    private final Index targetIndex;

    /**
     * @param index index of the person to display the details of
     */
    public ViewStudentCommand(Index index) {
        requireAllNonNull(index);

        this.targetIndex = index;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToDisplay = lastShownList.get(targetIndex.getZeroBased());

        return new CommandResult(String.format(MESSAGE_VIEW_PERSON_SUCCESS, targetIndex.getZeroBased() + 1),
                false, false, personToDisplay, targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ViewStudentCommand)) {
            return false;
        }

        ViewStudentCommand e = (ViewStudentCommand) other;
        return targetIndex.equals(e.targetIndex);
    }
}
