package seedu.address.logic.commands.volunteercommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.AddressBookParser.VOLUNTEER_COMMAND_INDICATOR;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.volunteer.Volunteer;

/**
 * Displays events the selected volunteer is participating in
 */
public class VolunteerViewCommand extends Command {

    public static final String COMMAND_WORD = "view";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Views the event details of the volunteer identified by the index number used in the displayed "
            + "volunteer list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + VOLUNTEER_COMMAND_INDICATOR + " " + COMMAND_WORD + " 1";

    public static final String MESSAGE_VIEW_VOLUNTEER_SUCCESS = "Viewing details of events that %s is involved in.";

    private final Index targetIndex;

    public VolunteerViewCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Volunteer> lastShownList = model.getFilteredVolunteerList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
        }

        Volunteer volunteerToView = lastShownList.get(targetIndex.getZeroBased());
        model.viewVolunteer(volunteerToView);
        return new CommandResult(String.format(MESSAGE_VIEW_VOLUNTEER_SUCCESS, volunteerToView.getName().toString()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VolunteerViewCommand)) {
            return false;
        }

        VolunteerViewCommand otherViewCommand = (VolunteerViewCommand) other;
        return targetIndex.equals(otherViewCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
