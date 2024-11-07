package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.Messages;
import seedu.address.model.Model;

/**
 * Displays the current number of guests and vendors.
 *
 */
public class StatsCommand extends Command {

    public static final String COMMAND_WORD = "stats";
    public static final String MESSAGE_STATS_SUCCESS = "Here are the current statistics: \n";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        int[] guestCounts = model.getFilteredGuestListCount();
        int vendorCount = model.getFilteredVendorListCount();
        return new CommandResult(Messages.getSuccessMessageWithStats(MESSAGE_STATS_SUCCESS, guestCounts, vendorCount));
    }
}
