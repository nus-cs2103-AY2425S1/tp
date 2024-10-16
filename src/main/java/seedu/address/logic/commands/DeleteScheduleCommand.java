package seedu.address.logic.commands;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteScheduleCommand extends Command {

    public static final String COMMAND_WORD = "delete-schedule";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the event identified by the index number used in the displayed schedule list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_SCHEDULE_SUCCESS = "Deleted Event: %1$s";

    private final Index targetIndex;

    public DeleteScheduleCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        //Replace with schedule model
        requireNonNull(model);
        //Replace to List<Schedule>
        List<Person> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
        }

        //Change to schedule
        //Person personToDelete = lastShownList.get(targetIndex.getZeroBased());

        //Change to schedule model - Delete specified event at the given schedule
        //model.deletePerson(personToDelete);

        //Need to add format for schedule
        //Replace constant with Messages.format(Schedule) once done
        return new CommandResult(String.format(MESSAGE_DELETE_SCHEDULE_SUCCESS, "TEMP STRING REPRESENTING EVENT"));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteScheduleCommand)) {
            return false;
        }

        DeleteScheduleCommand otherDeleteScheduleCommand = (DeleteScheduleCommand) other;
        return targetIndex.equals(otherDeleteScheduleCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
