package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEETINGS;

import seedu.address.model.Model;

/**
 * Lists all Meetings in the address book to the user.
 */
public class SeeAllScheduleCommand extends Command {

    public static final String COMMAND_WORD = "list-schedule";

    public static final String MESSAGE_SUCCESS = "List all meetings";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.changeWeeklySchedule(PREDICATE_SHOW_ALL_MEETINGS);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || other instanceof SeeAllScheduleCommand; // instanceof handles
    }
}
