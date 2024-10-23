package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Finds and lists all tasks in the task list that are related to a specified person.
 */
public class FindTaskCommand extends Command {

    public static final String COMMAND_WORD = "findtask";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all tasks related to the person identified by "
            + "the index number used in the displayed person list and displays them as a list with index numbers.\n"
            + "Parameters: PERSON_INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    private final Index targetIndex;

    /**
     * Constructs a FindTaskCommand with the specified target index.
     *
     * @param targetIndex The index of the person whose tasks are to be found.
     */
    public FindTaskCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownPersonList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownPersonList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToFindTasksFor = lastShownPersonList.get(targetIndex.getZeroBased());

        // Filter tasks that are related to the selected person
        model.updateFilteredTaskList(task -> task.getPatient().equals(personToFindTasksFor));

        return new CommandResult(
                String.format(Messages.MESSAGE_TASKS_LISTED_OVERVIEW, model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof FindTaskCommand)) {
            return false;
        }

        FindTaskCommand otherFindTaskCommand = (FindTaskCommand) other;
        return targetIndex.equals(otherFindTaskCommand.targetIndex);
    }

    @Override
    public String toString() {
        return "FindTaskCommand{"
                + "targetIndex="
                + targetIndex
                + '}';
    }
}
