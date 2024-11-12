package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_JOBS;

import seedu.address.model.Model;

/**
 * Lists all jobs in the address book to the user.
 */
public class ListJobCommand extends ListCommand {

    public static final String ENTITY_WORD = "job";

    public static final String MESSAGE_SUCCESS = "Listed all jobs";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
