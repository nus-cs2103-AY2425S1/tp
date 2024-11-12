package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_COMPANIES;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_JOBS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.model.Model;

/**
 * Lists all contacts, companies, and jobs in the address book to the user.
 */
public class ListAllCommand extends ListCommand {

    public static final String ENTITY_WORD = "all";
    public static final String MESSAGE_SUCCESS = "Listed all contacts, companies, and jobs";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);

        // Update each list individually to show all items
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        model.updateFilteredCompanyList(PREDICATE_SHOW_ALL_COMPANIES);
        model.updateFilteredJobList(PREDICATE_SHOW_ALL_JOBS);

        // Return a combined message indicating the operation's success
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
