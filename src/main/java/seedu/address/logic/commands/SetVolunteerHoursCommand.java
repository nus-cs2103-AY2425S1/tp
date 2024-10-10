package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOURS;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.Model;

/**
 * Sets the hours of an existing volunteer in the address book
 */
public class SetVolunteerHoursCommand extends Command {
    public static final String COMMAND_WORD = "sethours";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the hours of the volunteer identified "
            + "by the index number used in the displayed person list. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_HOURS + "NAME] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_HOURS + "20 ";

    public static final String MESSAGE_SET_VOLUNTEER_HOURS_SUCCESS = "New hours for: %1$s";
    public static final String MESSAGE_NOT_EDITED = "The new number of hours must be provided.";
    public static final String MESSAGE_NOT_IMPLEMENTED = "The execute function has not been implemented.";
    private final Index index;
    private final Integer newHours;

    /**
     * @param index of the volunteer in the filtered person list to change hours for
     * @param newHours the new hours to input for the person
     */
    public SetVolunteerHoursCommand(Index index, int newHours) {
        requireNonNull(index);

        this.index = index;
        this.newHours = newHours;
    }
    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        return new CommandResult(
                String.format(MESSAGE_NOT_IMPLEMENTED));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetVolunteerHoursCommand)) {
            return false;
        }

        SetVolunteerHoursCommand otherSetVolunteerHoursCommand =
                (SetVolunteerHoursCommand) other;
        return index.equals(otherSetVolunteerHoursCommand.index)
                && newHours.equals(otherSetVolunteerHoursCommand.newHours);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("index", index)
                .add("newHours", newHours)
                .toString();
    }
}
