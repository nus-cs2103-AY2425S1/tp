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
import seedu.address.model.exceptions.VolunteerDeleteMissingDateException;
import seedu.address.model.exceptions.VolunteerNotAvailableOnAnyDayException;
import seedu.address.model.volunteer.Volunteer;

/**
 * Removes a list of dates from a volunteer's available dates
 */
public class VolunteerRemoveDateCommand extends Command {

    public static final String COMMAND_WORD = "unfree";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes dates from the list of available dates of volunteer identified by the index number "
            + "used in the displayed volunteer list.\n"
            + "Parameters: Dates (Must be in yyyy-MM-dd and cannot repeat. Multiple dates must be separated by a comma,"
            + " although spacing is optional.)\n"
            + "Example: " + VOLUNTEER_COMMAND_INDICATOR + " " + COMMAND_WORD + " i/1 d/2202-01-12, 2022-02-11";
    private static final String MESSAGE_REMOVE_DATE_VOLUNTEER_SUCCESS =
            "Removed dates from %s's list of available dates.";

    private static final String MESSAGE_REMOVE_DATE_FAIL_NO_REMAINING_DATES = "Volunteers must be free on"
            + " at least 1 day!\n This remove command will leave them with no available days."
            + " \nPlease add at least 1 more day to proceed.";

    private final Index targetIndex;
    private final String dateList;

    /**
     * Constructor. Takes in a non-zero, non-negative index and a list of dates formatted as a single string.
     * @param targetIndex
     * @param dateList
     */
    public VolunteerRemoveDateCommand(Index targetIndex, String dateList) {
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
            Volunteer volunteerToRemoveDate = lastShownList.get(targetIndex.getZeroBased());
            model.removeDatesFromVolunteer(volunteerToRemoveDate, dateList);
            return new CommandResult(String.format(MESSAGE_REMOVE_DATE_VOLUNTEER_SUCCESS,
                    volunteerToRemoveDate.getName().toString()));
        } catch (VolunteerDeleteMissingDateException e) {
            throw new CommandException(e.getMessage());
        } catch (VolunteerNotAvailableOnAnyDayException e) {
            throw new CommandException(MESSAGE_REMOVE_DATE_FAIL_NO_REMAINING_DATES);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof VolunteerRemoveDateCommand)) {
            return false;
        }

        VolunteerRemoveDateCommand otherRemoveDateCommand = (VolunteerRemoveDateCommand) other;
        return targetIndex.equals(otherRemoveDateCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
