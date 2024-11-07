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
import seedu.address.model.exceptions.VolunteerDuplicateDateException;
import seedu.address.model.volunteer.Volunteer;

/**
 * Adds a list of dates to a volunteer's available dates
 */
public class VolunteerAddDateCommand extends Command {


    public static final String COMMAND_WORD = "free";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds dates to the list of available dates of volunteer identified by the index number "
            + "used in the displayed volunteer list.\n"
            + "Parameters: Dates (Must be in yyyy-MM-dd and cannot repeat. Multiple dates must be separated by a comma,"
            + " although spacing is optional.)\n"
            + "Example: " + VOLUNTEER_COMMAND_INDICATOR + " " + COMMAND_WORD + " i/1 d/2202-01-12, 2022-02-11";
    private static final String MESSAGE_ADD_DATE_VOLUNTEER_SUCCESS =
            "Added dates to %s's list of available dates.";

    private final Index targetIndex;
    private final String dateList;

    /**
     * Constructor. Takes in a non-zero, non-negative index and a list of dates formatted as a single string.
     * @param targetIndex
     * @param dateList
     */
    public VolunteerAddDateCommand(Index targetIndex, String dateList) {
        this.targetIndex = targetIndex;
        this.dateList = dateList;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Volunteer> lastShownList = model.getFilteredVolunteerList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_VOLUNTEER_DISPLAYED_INDEX);
        }

        try {
            Volunteer volunteerToAddDate = lastShownList.get(targetIndex.getZeroBased());
            model.addDatesToVolunteer(volunteerToAddDate, dateList);
            return new CommandResult(String.format(MESSAGE_ADD_DATE_VOLUNTEER_SUCCESS,
                    volunteerToAddDate.getName().toString()));
        } catch (VolunteerDuplicateDateException e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VolunteerAddDateCommand)) {
            return false;
        }

        VolunteerAddDateCommand otherAddDateCommand = (VolunteerAddDateCommand) other;
        return targetIndex.equals(otherAddDateCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
