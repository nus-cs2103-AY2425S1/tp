package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_JOBS;

import seedu.address.logic.Mode;
import seedu.address.model.Model;

/**
 * Lists all persons in the address book to the user.
 */
public class ListJobCommand extends Command {

    // TODO: Temporary solution, could consider "list job"
    public static final String COMMAND_WORD = "list_job";

    public static final String MESSAGE_SUCCESS = "Listed all jobs";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
        return new CommandResult(MESSAGE_SUCCESS, false, false, Mode.JOB);
    }
}
